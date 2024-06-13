package Functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.auth.UserNameStore;

public class UsernameStore extends UserNameStore {

	String logDirectory;
	File logFile;
	List<String> listLogs;
	String[] arrayLogs;

	//J2SE_1.6
	BufferedWriter out;

	//J2SE_1.7
	//FileWriter fw;

	public UsernameStore(String directoryName) {

		// Create logDirectory based on OS
		logDirectory = defaultDirectory(directoryName);


		// Initialize logFile with only the directory
		logFile = new File(logDirectory);


		// Check the directory for jrecords log file
		logFile.mkdir();


		// Declare logFile as new with filename of log file
		logFile = new File(logDirectory + "/user.logs");


		// Check the file for jrecords log file
		try {
			logFile.createNewFile();
		} catch (IOException e1) { }


		// Get the usernames logged in log file and save into listLogs
		listLogs = listOfUserLogs2(logFile);


		// Initialize  the FileWriter fw and set to appdendable
		try {
			//J2SE_1.6
			out = new BufferedWriter(new FileWriter(logFile.getPath(), true));

			//J2SE_1.7
			//fw = new FileWriter(logFile.getPath(), true);
		} catch (IOException e) { }


		// Convert List<String> listLogs into String[] arrayLogs and save to arrayLogs
		arrayLogs = new String[listLogs.size()];
		for(int x=0; x < listLogs.size(); x++){
			arrayLogs[x] = listLogs.get(x);
		}


		// Set the UserNames using arrayLogs
		setUserNames(arrayLogs);
	}

	@Override
	public void addUserName(String username) {
		if(!containsUserName(username)){
			try {
				//J2SE_1.6
				out.write(username + "\n");
				out.close();

				//J2SE_1.7
				/*fw.append(arg0 + "\n");
				fw.flush();
				fw.close();*/
				
				listLogs.add(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean containsUserName(String username) {
		return listLogs.contains(username);
	}

	@Override
	public String[] getUserNames() {
		return this.arrayLogs;
	}

	@Override
	public void loadUserNames() {

	}

	@Override
	public void removeUserName(String username) {

	}

	@Override
	public void saveUserNames() {

	}

	@Override
	public void setUserNames(String[] arrayLogs) {
		this.arrayLogs = arrayLogs;
	}

	public void clearLogs() {
		try {
			//J2SE_1.6
			out = new BufferedWriter(new FileWriter(logFile.getPath()));
			out.write("");
			out.close();

			//J2SE_1.7
			/*fw = new FileWriter(logDirectory);
			fw.write("");
			fw.flush();
			fw.close();*/
		} catch (IOException e) { }
	}

	private String defaultDirectory(String directoryName) {
		String OS = System.getProperty("os.name").toUpperCase();
		System.out.println("Operating System: " + OS + "\n\n");

		if (OS.contains("WIN")){
			directoryName = directoryName.replace("_", " ");
			return System.getenv("APPDATA") + "/" + directoryName;
		}
		else if (OS.contains("LINUX")){
			return System.getProperty("user.home") + "/." + directoryName;
		}
		else if (OS.contains("MAC")){
			return System.getProperty("user.home") + "/Library/Application "  + "Support";
		}

		return System.getProperty("user.dir");
	}

	// Reading user logs using J2SE_1.6
	public List<String> listOfUserLogs2(File logFile) {
		List<String> list = new ArrayList<String>();
		BufferedReader textReader = null;

		try {
			textReader = new BufferedReader(new FileReader(logFile));
			for (String line = textReader.readLine(); line != null; line = textReader.readLine()) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (textReader != null) {
				try {
					textReader.close();
				} catch (IOException e) { }
			}
		}

		return list;
	}

	// Reading user logs using J2SE_1.7
	/*public List<String> listOfUserLogs1(File logFile) {
		List<String> list = null;
		try {
			list = Files.readAllLines(logFile.toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}*/

}

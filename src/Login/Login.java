package Login;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.PasswordStore;
import org.jdesktop.swingx.auth.UserNameStore;
import org.jdesktop.swingx.plaf.basic.CapsLockSupport;

import Functions.FncGlobal;

import components._JXLoginPane;

public class Login extends _JXLoginPane {

	private static final long serialVersionUID = 1L;

	private Functions.UsernameStore usernames = new Functions.UsernameStore("JSystem");

	private JComboBox cmbUsername;
	private JPasswordField txtPassword = null;
	private Object [] oneLoginDetails;

	public Login(boolean isAdmin) {
		initGUI(isAdmin);
	}

	public Login(LoginService service, boolean isAdmin) {
		super(service);
		initGUI(isAdmin);
	}

	public Login(LoginService service, PasswordStore passwordStore, UserNameStore userStore, boolean isAdmin) {
		super(service, passwordStore, userStore);
		initGUI(isAdmin);
	}

	public Login(LoginService service, PasswordStore passwordStore, UserNameStore userStore, List<String> servers, boolean isAdmin) {
		super(service, passwordStore, userStore, servers);
		initGUI(isAdmin);
	}

	private void initGUI(boolean isAdmin) {
		setBannerText(FncGlobal.ORIGINAL_TITLE.replace("(Testing)", "")); //FncGlobal.ORIGINAL_TITLE

		setSaveMode(SaveMode.BOTH);

		// Set the usernames logs in computer
		setUserNameStore(usernames);

		String[] servers = new String[FncGlobal.mapURL.size()];
		for(int x=0; x < servers.length; x++){
			servers[x] = FncGlobal.mapURL.keySet().toArray()[x].toString();
		}
		setServers(Arrays.asList(servers));

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//System.out.println(evt.getPropertyName() + " (" + evt.getOldValue() + ", " + evt.getNewValue() + ")");
			}
		});

		addPropertyChangeListener("status", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				/*if(getStatus() == Status.FAILED){
					setErrorMessage("Please check network connection.");
				}*/
				if(getStatus() == Status.SUCCEEDED){
					FncGlobal.usernameStore = getUserNameStore();
					FncGlobal.isRememberPassword = isRememberPassword();
				}
			}
		});

		CapsLockSupport.getInstance().addPropertyChangeListener("capsLockEnabled", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println(evt.getPropertyName() + " - " + evt.getNewValue());
			}
		});

		looperPanel(getComponents());

		try { 
			String ip_address = InetAddress.getLocalHost().getHostAddress();
			System.out.printf("LocalHost: %s%n", InetAddress.getLocalHost());
			System.out.printf("IPV4: %s%n", Inet4Address.getLocalHost().getHostAddress());
		
		
			//System.out.printf("Display value of date: %s%n",FncGlobal.getDateSQL());
		
		
			/*oneLoginDetails = FncGlobal.isOneLogin(FncGlobal.getDateSQL(), ip_address);
		
			if(oneLoginDetails != null){
		
				String username = (String) oneLoginDetails[0]; 
				String password = (String) oneLoginDetails[1];
		
				setUserName(username); setPassword(password.toCharArray());
		
				startLogin(); 
			} */
		} catch (UnknownHostException e) 
		{ e.printStackTrace(); }

		if(isAdmin){//XXX Determine if IP Address is an Admin IP --COMMENTED ON (04-29-15)
			try {
				String ip_address = InetAddress.getLocalHost().getHostAddress();
				System.out.printf("IP: %s%n", ip_address);

				String username = "";
				String password = "";
				
				//ADD ANOTHER IP ADDRESS HERE
				setUserName(username);
				setPassword(password.toCharArray());
				startLogin();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}/*else{
			String username = "jffatallo";
			String password = "900876";

		}*/
	}

	private void looperPanel(Component[] comps) {
		for(Component comp : comps){
			//System.out.println(comp.getClass());

			//Detect all JComboBox
			if(comp instanceof JComboBox){
				cmbUsername = ((JComboBox)comp);

				if(cmbUsername.getClass().getSimpleName().equals("ComboNamePanel")){
					//System.out.println("Combo: " + cmbUsername.getClass().getSimpleName());

					cmbUsername.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//JComboBox combo = (JComboBox) e.getSource();
							//System.out.println(e.getActionCommand() + ": " + combo.getEditor().getItem());

							if(e.getActionCommand().equals("comboBoxEdited")){
								txtPassword.requestFocus();
							}
						}
					});
				}
			}

			//Detect all JPasswordField
			if(comp instanceof JPasswordField){
				txtPassword = (JPasswordField) comp;
			}

			//Detect all JCheckBox
			if(comp instanceof JCheckBox){
				((JCheckBox)comp).setVisible(false);
			}

			if(comp instanceof JPanel){
				((JPanel)comp).setOpaque(false);
				looperPanel(((JPanel)comp).getComponents());
			}else if(comp instanceof JXPanel){
				((JXPanel)comp).setOpaque(false);
				looperPanel(((JXPanel)comp).getComponents());
			}else if(comp instanceof JXLoginPane){
				((JXLoginPane)comp).setOpaque(false);
				looperPanel(((JXLoginPane)comp).getComponents());
			}else{
				//comp.setEnabled(false);
			}
		}
	}

}

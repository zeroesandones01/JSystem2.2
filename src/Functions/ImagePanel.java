package Functions;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Database.pgSelect;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	Image IMAGE_BG;


	String file = "Images/5.jpg";
	String test_system_bg = "Images/test_bg.jpg";

	public ImagePanel() throws IOException {



		IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/5.jpg"));
	}
	public ImagePanel(String file) throws IOException {

		System.out.println("**********IMAGE: "+ file);


		byte bt[]=null;
		String strSQL = "SELECT system_background FROM rf_system_background WHERE emp_code = '"+UserInfo.EmployeeCode+"'";
		//String strSQL = "SELECT system_background FROM rf_entity_image WHERE entity_id = '6917707123'";

		Functions.FncSelectRecords.selectV2(strSQL, false);
		pgSelect db = new pgSelect();
		String SQL = "SELECT current_database()::Varchar, inet_server_addr()::VARCHAR";
		db.select(SQL);
		String database = "";
		String ip = "";

		if(db.isNotNull()) {
			database = (String) db.getResult()[0][0];
			ip = (String) db.getResult()[0][1];
		}

		System.out.printf("Value of database: %s%n", database);
		System.out.printf("Value of ip: %s%n", ip);


		try {
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			while (Functions.FncSelectRecords.rs.next()) {
				bt = Functions.FncSelectRecords.rs.getBytes(1);
			}

			if(database.equals("terraverde_beta_testing") && ip.equals("192.168.10.15/32")) {	
				if(UserInfo.EmployeeCode.equals("900796")) {
					IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/5.png"));
				} else {
					IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/test_bg.jpg"));
				}
			}else {

				if (bt == null) {
					IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/5.jpg"));
					//IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/christmas.jpg"));
				}else{
					IMAGE_BG = Toolkit.getDefaultToolkit().createImage(bt);	
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();

		/*if (file == null) {
    		IMAGE_BG = Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("Images/5.jpg"));
		}else{
			IMAGE_BG = Toolkit.getDefaultToolkit().createImage(file);	
		}*/

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(IMAGE_BG, 150, 0, this);

		g.drawImage(IMAGE_BG, 0, 0, getWidth(), getHeight(), this);
	}

}


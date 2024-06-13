package File;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import components._JInternalFrame;

public class ChangePassword extends _JInternalFrame {
	/**
	 * @author Jessa Herrera (2016-07-13)
	 */
	private static final long serialVersionUID = 6710862414317279692L;
	private JPasswordField txtCurrentPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmPassword;
	private JLabel lblConfirmPassword;
	private JLabel lblNewPassword;
	private JButton btnCancel;
	private JButton btnSave;
	private JLabel lblCurrentPassword;
	
	private Font plainFont = new Font("DIALOG", Font.PLAIN, 12);
	private Font italicFont = new Font("DIALOG", Font.ITALIC, 12);
	private Color redFont= Color.RED;
	private Color blackFont = Color.BLACK;
	private JLabel tagNewPassword;
	private JPanel pnlPasword;
	private JLabel tagConfirmPassword;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ChangePassword inst = new ChangePassword();
		JDesktopPane jdp = new JDesktopPane();
		jdp.add(inst);
		jdp.setPreferredSize(inst.getPreferredSize());
		frame.setContentPane(jdp);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ChangePassword(){
		super();
		initGUI();
/*		txtCurrentPassword.setDocument( new FncJTextFieldLimit(16, "ALL", false) );
		txtNewPassword.setDocument( new FncJTextFieldLimit(16, "ALL", false) );
		txtConfirmPassword.setDocument( new FncJTextFieldLimit(16, "ALL", false) );*/
	}

	private void initGUI(){
		this.setPreferredSize(new java.awt.Dimension(382, 211));
		this.setBounds(0, 0, 382, 211);
		this.setVisible(true);
		this.setTitle("Change Password");
		this.setClosable(true);
		this.getContentPane().setLayout(null);
		this.setIconifiable(true);
		{
			pnlPasword = new JPanel();
			getContentPane().add(pnlPasword);
			pnlPasword.setLayout(null);
			pnlPasword.setBounds(4, 4, 368, 138);
			pnlPasword.setBorder(new LineBorder(new java.awt.Color(0,0,0),1,false));
			{
				tagConfirmPassword = new JLabel();
				pnlPasword.add(tagConfirmPassword);
				tagConfirmPassword.setBounds(157, 110, 205, 15);
				tagConfirmPassword.setName("tagConfirmPassword");
			}
			{
				tagNewPassword = new JLabel("");
				pnlPasword.add(tagNewPassword);
				tagNewPassword.setBounds(157, 64, 205, 15);
				tagNewPassword.setName("tagNewPassword");
			}
			{
				lblConfirmPassword = new JLabel("Confirm New Pword:");
				pnlPasword.add(lblConfirmPassword);
				lblConfirmPassword.setHorizontalAlignment(JLabel.RIGHT);
				lblConfirmPassword.setFont(italicFont);
				lblConfirmPassword.setForeground(redFont);
				lblConfirmPassword.setBounds(4, 85, 141, 25);
				lblConfirmPassword.setName("lblConfirmPassword");
			}
			{
				lblNewPassword = new JLabel("New Password:");
				pnlPasword.add(lblNewPassword);
				lblNewPassword.setHorizontalAlignment(JLabel.RIGHT);
				lblNewPassword.setFont(italicFont);
				lblNewPassword.setForeground(redFont);
				lblNewPassword.setBounds(5, 39, 141, 25);
				lblNewPassword.setName("lblNewPassword");
			}
			{
				lblCurrentPassword = new JLabel("Current Password:");
				pnlPasword.add(lblCurrentPassword);
				lblCurrentPassword.setHorizontalAlignment(JLabel.RIGHT);
				lblCurrentPassword.setFont(italicFont);
				lblCurrentPassword.setForeground(redFont);
				lblCurrentPassword.setBounds(5, 8, 141, 25);
				lblCurrentPassword.setName("lblCurrentPassword");
			}
			{
				txtConfirmPassword = new JPasswordField(16);
				pnlPasword.add(txtConfirmPassword);
				txtConfirmPassword.setBounds(157, 85, 205, 25);
				txtConfirmPassword.setName("txtConfirmPassword");
				txtConfirmPassword.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent arg0) {
						String confirmPassword = "";
						for(int x=0; x<txtConfirmPassword.getPassword().length; x++){
							confirmPassword = confirmPassword + txtConfirmPassword.getPassword()[x];
						}
						
						if(confirmPassword.equals("")){
							lblConfirmPassword.setFont(italicFont);
							lblConfirmPassword.setForeground(redFont);
						}else{
							lblConfirmPassword.setFont(plainFont);
							lblConfirmPassword.setForeground(blackFont);
						}
						
						if(isMatch()){
							tagConfirmPassword.setText("Password match.");
							tagConfirmPassword.setFont(new Font("DIALOG", Font.BOLD, 11));
							tagConfirmPassword.setForeground(new Color(0, 0, 205));
						}else{
							tagConfirmPassword.setText("Password did not match.");
							tagConfirmPassword.setFont(new Font("DIALOG", Font.BOLD, 11));
							tagConfirmPassword.setForeground( new Color(255, 0, 0));
						}
					}
				});
				txtConfirmPassword.addFocusListener(new FocusAdapter(){
					public void focusLost(FocusEvent arg0) {
						String confirmPassword = "";
						for(int x=0; x<txtConfirmPassword.getPassword().length; x++){
							confirmPassword = confirmPassword + txtConfirmPassword.getPassword()[x];
						}
						
						if(confirmPassword.equals("")){
							lblConfirmPassword.setFont(italicFont);
							lblConfirmPassword.setForeground(redFont);
						}else{
							lblConfirmPassword.setFont(plainFont);
							lblConfirmPassword.setForeground(blackFont);
						}
					}
				});
			}
			{
				txtNewPassword = new JPasswordField(16);
				pnlPasword.add(txtNewPassword);
				txtNewPassword.setBounds(157, 39, 205, 25);
				txtNewPassword.setName("txtNewPassword");
				txtNewPassword.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent arg0) {
						String newPassword = "";
						for(int x=0; x<txtNewPassword.getPassword().length; x++){
							newPassword = newPassword + txtNewPassword.getPassword()[x];
						}
						
						if(newPassword.equals("")){
							lblNewPassword.setFont(italicFont);
							lblNewPassword.setForeground(redFont);
						}else{
							lblNewPassword.setFont(plainFont);
							lblNewPassword.setForeground(blackFont);
						}
					}
				});
				txtNewPassword.addFocusListener(new FocusAdapter(){
					public void focusLost(FocusEvent arg0) {
						String newPassword = "";
						for(int x=0; x<txtNewPassword.getPassword().length; x++){
							newPassword = newPassword + txtNewPassword.getPassword()[x];
						}
						
						if(newPassword.equals("")){
							lblNewPassword.setFont(italicFont);
							lblNewPassword.setForeground(redFont);
						}else{
							lblNewPassword.setFont(plainFont);
							lblNewPassword.setForeground(blackFont);
						}
						
						if(!newPassword.trim().equals("")){
							tagNewPassword.setText("");
							tagNewPassword.setFont(new Font("DIALOG", Font.BOLD, 11));
							tagNewPassword.setForeground(new Color(0, 0, 205));
						}else{
							tagNewPassword.setText("You cannot use a blank password.");
							tagNewPassword.setFont(new Font("DIALOG", Font.BOLD, 11));
							tagNewPassword.setForeground( new Color(255, 0, 0));
						}
					}
				});
			}
			{
				txtCurrentPassword = new JPasswordField(16);
				pnlPasword.add(txtCurrentPassword);
				txtCurrentPassword.setBounds(157, 8, 205, 25);
				txtCurrentPassword.setName("txtCurrentPassword");
				txtCurrentPassword.addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent arg0) {
						String currentPassword = "";
						for(int x=0; x<txtCurrentPassword.getPassword().length; x++){
							currentPassword = currentPassword + txtCurrentPassword.getPassword()[x];
						}
						
						if(currentPassword.equals("")){
							lblCurrentPassword.setFont(italicFont);
							lblCurrentPassword.setForeground(redFont);
						}else{
							lblCurrentPassword.setFont(plainFont);
							lblCurrentPassword.setForeground(blackFont);
						}
					}
				});
				txtCurrentPassword.addFocusListener(new FocusAdapter(){
					public void focusLost(FocusEvent arg0) {
						String currentPassword = "";
						for(int x=0; x<txtCurrentPassword.getPassword().length; x++){
							currentPassword = currentPassword + txtCurrentPassword.getPassword()[x];
						}
						if(currentPassword.equals("")){
							lblCurrentPassword.setFont(italicFont);
							lblCurrentPassword.setForeground(redFont);
						}else{
							lblCurrentPassword.setFont(plainFont);
							lblCurrentPassword.setForeground(blackFont);
						}
					}
				});
			}
		}
		{
			btnSave = new JButton("Save");
			getContentPane().add(btnSave);
			btnSave.setBounds(89, 148, 100, 30);
			btnSave.setName("btnSave");
			btnSave.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String login_id = UserInfo.Alias;
					String currentPassword = "";
					for(int x=0; x<txtCurrentPassword.getPassword().length; x++){
						currentPassword = currentPassword + txtCurrentPassword.getPassword()[x];
					}
					
					String newPassword = "";
					for(int x=0; x<txtNewPassword.getPassword().length; x++){
						newPassword = newPassword + txtNewPassword.getPassword()[x];
					}
					
					String confirmPassword = "";
					for(int x=0; x<txtConfirmPassword.getPassword().length; x++){
						confirmPassword = confirmPassword + txtConfirmPassword.getPassword()[x];
					}
					//System.out.println("Current Password: "+currentPassword);
					//System.out.println("New Password: "+newPassword);
					//System.out.println("Confirm Password: "+confirmPassword);
					
					if(toSave()){
						String pword = FncGlobal.connectionPassword;
						//if(JOptionPane.showConfirmDialog(this, "New password will also be ", pword, optionType))
						if(JOptionPane.showConfirmDialog(ChangePassword.this, String.format("Changing password will also affect your password \nin Behavioral and 360 Evaluation\nAre you sure you want to continue?"), "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							if(currentPassword.equals(pword)){
								if(isMatch()){
									updatePassword(login_id, confirmPassword);
									System.exit(0);
									
								}else{
									//JOptionPane.showMessageDialog(getContentPane(), "Your new password entries did not match.", "Save", JOptionPane.WARNING_MESSAGE);
								}
							}else{
								JOptionPane.showMessageDialog(getContentPane(), "Invalid current password", "Save", JOptionPane.WARNING_MESSAGE);
							}
						}
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "Please fill-up required fields.", "Save", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		{
			btnCancel = new JButton("Cancel");
			getContentPane().add(btnCancel);
			btnCancel.setBounds(194, 148, 100, 30);
			btnCancel.setName("btnCancel");
			btnCancel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		
		//Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
	}
	
	private void updatePassword(String login_id, String password){
		//String SQL = "update rf_system_user set emp_code='"+password+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() where login_id='"+login_id+"'";
		String SQL2 = "INSERT INTO mf_audit_trail(\n" + 
				"              system_id, activity, user_code, date_upd, entity_id, \n" + 
				"              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
				"      VALUES ('UT', 'Change Password', '"+UserInfo.EmployeeCode+"', NOW(), NULL, \n" + 
				"              NULL, NULL, NULL, NULL, NULL, NULL);";
		
		pgUpdate db2 = new pgUpdate();
		db2.executeUpdate(SQL2, false);
		db2.commit();
		
		String SQL = "ALTER ROLE "+login_id+" WITH ENCRYPTED PASSWORD '"+password+"'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, false);
		db.commit();
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver") ;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection("jdbc:mysql://177.177.180.24:3306/hr", "root", "");
			Statement stmt = conn.createStatement() ;
			String strSQL = "UPDATE rf_system_user SET password = md5('"+password+"') WHERE emp_code = '"+UserInfo.EmployeeCode+"'" ;
			stmt.executeUpdate(strSQL);
			conn.close();
			stmt.close();
			System.out.println("Dumaan dito sa MYSQL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(this.getContentPane(), "Password successfully updated.", "Save", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private Boolean isMatch(){
		String newPassword = "";
		for(int x=0; x<txtNewPassword.getPassword().length; x++){
			newPassword = newPassword + txtNewPassword.getPassword()[x];
		}
		
		String confirmPassword = "";
		for(int x=0; x<txtConfirmPassword.getPassword().length; x++){
			confirmPassword = confirmPassword + txtConfirmPassword.getPassword()[x];
		}
		
		return newPassword.equals(confirmPassword) ? true:false;
	}
	
	/*private Boolean isCurrentPasswordOK(String login_id){
		String currentPassword = "";
		
		//System.out.println("**************" + txtCurrentPassword.getPassword());
		for(int x=0; x<txtCurrentPassword.getPassword().length; x++){
			currentPassword = currentPassword + txtCurrentPassword.getPassword()[x];
		}
		String SQL = "select rolpassword from pg_authid where rolname = '"+login_id+"' and rolpassword = (select concat('md5',(SELECT md5('"+currentPassword+""+login_id+"'))))";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display", SQL);
		
		//return currentPassword.equals(db.Result[0][0]) ? true:false;
		if(db.isNotNull()){
			return currentPassword.equals(db.Result[0][0]);
		}else{
			return null;
		}
		
	}
	
	private Boolean isPasswordValid(String password){
		return password.trim().equals("") ? false:true;
	}*/
	
	private void resetState(){
		txtCurrentPassword.setText("");
		txtNewPassword.setText("");
		txtConfirmPassword.setText("");
		
		lblCurrentPassword.setFont(italicFont);
		lblCurrentPassword.setForeground(redFont);
		lblNewPassword.setFont(italicFont);
		lblNewPassword.setForeground(redFont);
		lblConfirmPassword.setFont(italicFont);
		lblConfirmPassword.setForeground(redFont);
		
		tagNewPassword.setText("");
		tagConfirmPassword.setText("");
	}
	
	private Boolean toSave(){
		String currentPassword = "";
		for(int x=0; x<txtCurrentPassword.getPassword().length; x++){
			currentPassword = currentPassword + txtCurrentPassword.getPassword()[x];
		}
		
		String newPassword = "";
		for(int x=0; x<txtNewPassword.getPassword().length; x++){
			newPassword = newPassword + txtNewPassword.getPassword()[x];
		}
		
		String confirmPassword = "";
		for(int x=0; x<txtConfirmPassword.getPassword().length; x++){
			confirmPassword = confirmPassword + txtConfirmPassword.getPassword()[x];
		}
		
		if(currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")){
			return false;
		}else{
			return true;
		}
	}
}

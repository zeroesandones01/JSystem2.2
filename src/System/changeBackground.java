package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.UserInfo;
import Home.Home_JSystem;
import components._JButton;
import components._JInternalFrame;
import interfaces._GUI;


public class changeBackground extends _JInternalFrame implements _GUI{
	static String TITLE = "Change Background";
	static Dimension FRAME_SIZE = new Dimension(800, 400);
	static Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JXLabel lblImage;
	private JXPanel pnlCenter;
	private _JButton btnFile;
	private _JButton btnChangeBack;
	private String selectedFile;
	private JFileChooser fileChooser;
	
	public changeBackground() {
		super(TITLE, true, true, true, true);
		initGUI();
	}

	public changeBackground(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public changeBackground(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(TITLE);
		this.setSize(FRAME_SIZE);
		this.setPreferredSize(new Dimension(FRAME_SIZE));
		this.setLayout(new BorderLayout());
		{

			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{

				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(800, 300));
					pnlNorth.setBorder(components._JTBorderFactory.createTitleBorder("Image Viewer"));
					{
						lblImage = new JXLabel();
						pnlNorth.add(lblImage,BorderLayout.CENTER);

					}
				}	
				
				{

					pnlCenter = new JXPanel();
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					{
						JXPanel pnlNorth = new JXPanel(new GridLayout(1, 4, 3, 3));
						pnlCenter.add(pnlNorth,BorderLayout.NORTH);
						{
							{
								btnFile = new _JButton("File Open");
								pnlNorth.add(btnFile);
								btnFile.setActionCommand("Preview");
								btnFile.setPreferredSize(new Dimension(150, 30));
								btnFile.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										

										new Thread(new Runnable() {
											
											

											public void run() {

												if(fileChooser==null){
													fileChooser = new JFileChooser(FileUtils.getUserDirectoryPath());
													fileChooser.addChoosableFileFilter(new MyFilter());
													fileChooser.setAcceptAllFileFilterUsed(true);
												}

												fileChooser.setSelectedFile(new File(""));
												int status = fileChooser.showOpenDialog(getContentPane());
												if (status == JFileChooser.APPROVE_OPTION) {
													btnFile.requestFocus();

													if(fileChooser.getSelectedFile().equals(null)){
														JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Selected Document");

													}else{
														selectedFile = fileChooser.getSelectedFile().toString();
														//txtEfiles.setText(selectedFile);
														//file = new  File(selectedFile);
														try {
															Image bi = ImageIO.read(fileChooser.getSelectedFile());
														
															lblImage.setIcon(new ImageIcon(bi.getScaledInstance(800, 300, 300)));
														} catch (IOException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														System.out.println("***************************"+selectedFile);
														

														
													}
												}else if (status == JFileChooser.CANCEL_OPTION) ;
											}
										}).start();
									
									}
								});
							}
							{
								btnChangeBack = new _JButton("Apply");
								pnlNorth.add(btnChangeBack);
								btnChangeBack.setActionCommand("Apply");
								btnChangeBack.setPreferredSize(new Dimension(150, 30));
								btnChangeBack.addActionListener(new ActionListener() {
									

									private FileInputStream fis;

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										
										File file = new  File(selectedFile);
										
										
										 try {
											fis = new FileInputStream(file);
											
											
											byte[] bytes = null;
												
												ByteArrayOutputStream bos = new ByteArrayOutputStream();
												byte[] buf = new byte[1024];
												for (int readNum; (readNum = fis.read(buf)) != -1;) {
													bos.write(buf, 0, readNum);      
												}
												bytes = bos.toByteArray();
												
												saveImage(bytes);
										} catch (FileNotFoundException e1) {
											// TODO Auto-generated catch block
											//e1.printStackTrace();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											//e1.printStackTrace();
										}
										 
										FncGlobal.homeMDI.setVisible(false);
										FncGlobal.homeMDI.dispose();
										Home_JSystem hs = new Home_JSystem(FncGlobal.ORIGINAL_TITLE,selectedFile);
										
										 hs.setVisible(false);
										//pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
										//pnlMain.setBackgroundPainter(FncPanelPainter.paint(FncLookAndFeel.grayColor));
										 hs.invalidate();
										 hs.validate();
										 hs.repaint();
										hs.setVisible(true);
										
									}
										
								});
							}
						}
					}
				}

			}	
			


		}
	}

	protected class MyFilter extends FileFilter {
		public boolean accept(File file) {
			String filename = file.getName();
			return file.isDirectory() || filename.endsWith(".jpg");
		}

		public String getDescription() {
			return "*.jpg";
		}
	}
	
	private void saveImage(byte[] img){
		pgUpdate db = new pgUpdate();
		String SQL = "DELETE FROM rf_system_background where emp_code = '"+UserInfo.EmployeeCode+"'";
		db.executeUpdate(SQL, false);
		
		String SQL2 = "INSERT INTO rf_system_background(rec_id, emp_code)\n" + 
					  "VALUES ((SELECT COALESCE(max(rec_id), 0) + 1 from rf_system_background), '"+UserInfo.EmployeeCode+"');\n";
		db.executeUpdate(SQL2, false);
		db.commit();
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE rf_system_background SET system_background = ? WHERE emp_code = '"+UserInfo.EmployeeCode+"'");
			ps.setBytes(1, img);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}


}

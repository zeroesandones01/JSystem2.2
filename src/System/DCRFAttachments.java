package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_dcrf_attachment_viewer;
import Functions.FncFileChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.UserInfo;
import interfaces._GUI;

public class DCRFAttachments extends JPanel implements _GUI {

	private static final long serialVersionUID = -6758643438663384291L;
	private JButton btnAdd;
	private JButton btnView; 
	
	private JPanel panCenter; 
	
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private JCheckBox[] chkAttachments; 
	
	private String strDCRF;
	
	public DCRFAttachments(String dcrf) {
		this.strDCRF = dcrf; 
		initGUI(); 
	}

	public DCRFAttachments(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public DCRFAttachments(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public DCRFAttachments(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(350, 300));
		
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(2, 2, 2, 2));
		panMain.setOpaque(true);
		{
			{
				panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				FillCenter(); 
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnAdd = new JButton("Add"); 
						panEnd.add(btnAdd); 
						btnAdd.setFont(font11);
						btnAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								addAttachment(); 
							}
						});
					}
					{
						panEnd.add(new JLabel("")); 
					}
					{
						btnView = new JButton("View"); 
						panEnd.add(btnView); 
						btnView.setFont(font11);
						btnView.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								dlg_dcrf_attachment_viewer dialog = new dlg_dcrf_attachment_viewer(FncGlobal.homeMDI, "Attachments", false, strDCRF);
								
								final Toolkit toolkit = Toolkit.getDefaultToolkit();
								final Dimension screenSize = toolkit.getScreenSize();
								
								if(UserInfo.EmployeeCode.equals("900876")) {
									dialog.setSize(screenSize.width-100, screenSize.height-50);
								}else {
									dialog.setSize(screenSize.width-100, screenSize.height-50);
								}
								
								dialog.setResizable(false);
								dialog.setVisible(true);
							}
						});
					}
				}
			}
		}
	}

	private void addAttachment() {
		String strMessage = ""; 
        File[] listOfFiles = FncFileChooser.ImageFileChooser(); 
		
        if (listOfFiles.length>1) {
        	strMessage = listOfFiles.length+" attachment(s) will be added. Proceed?";
        } else {
        	strMessage = listOfFiles.length+" attachment will be added. Proceed?";
        }
        
		if (JOptionPane.showConfirmDialog(null, strMessage, 
				"Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			for (File file : listOfFiles) {
				String path = file.getPath().replace('\\', '/');
	        	saveAttachments(file, GetFileName(path)); 
	        }
			
			JOptionPane.showMessageDialog(null, "Attachments successfully added!");
		}
		
		//DataChangeRequest.dialog.dispose();
		FillCenter();
	}
	
	private void saveAttachments(File file, String strFilename) {
		FileInputStream fis = null;
		PreparedStatement ps;
		
		Integer intAtt = FncGlobal.GetInteger("select coalesce(max(attachment_no), 0)+1 \n" +
				"from rf_dcrf_attachments \n" +
				"where dcrf_no = '"+strDCRF+"' and status_id = 'A'"); 
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			ps = FncGlobal.connection.prepareStatement("insert into rf_dcrf_attachments (dcrf_no, attachment_no, attachment_desc, attachment_img, created_by, date_created, status_id) \n" +
					"values ('"+strDCRF+"'::int, '"+intAtt+"'::int, '"+strFilename+"', ?, '"+UserInfo.EmployeeCode+"', now(), 'A')");
			ps.setBinaryStream(1, fis, (int) file.length());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String GetFileName(String strPath) {
		char strChar = '/'; 
		Integer intNum = 0; 
		
		for(int x=0; x<strPath.length(); x++) {
			if (strPath.charAt(x)==strChar) {
				intNum++; 
			}
		}
		
		String[] strSplit = strPath.split("/"); 
		return strSplit[intNum]; 
	}
	
	private void FillCenter() {
		pgSelect dbExec = new pgSelect();
		dbExec.select("select * from rf_dcrf_attachments where dcrf_no = '"+strDCRF+"' and status_id = 'A'");
		
		Integer intCount = dbExec.getRowCount();
		
		panCenter.removeAll();
		panCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panList = new JPanel(new GridLayout(((intCount<10)?10:intCount), 1, 0, 0)); 
		panList.setMinimumSize(new Dimension(400, 200));
		{
			chkAttachments = new JCheckBox[intCount]; 
			for (int x=0; x<dbExec.getRowCount(); x++) {
				chkAttachments[x] = new JCheckBox(dbExec.getResult()[x][2].toString()); 
				panList.add(chkAttachments[x]); 
				chkAttachments[x].setHorizontalAlignment(JCheckBox.LEADING);
				chkAttachments[x].setSelected(true);
				chkAttachments[x].setFocusable(false);
				chkAttachments[x].addItemListener(itemListener);
			}
		}
		
		JScrollPane scr = new JScrollPane(panList); 
		panCenter.add(scr, BorderLayout.CENTER); 
		
		panCenter.repaint();
		panCenter.revalidate();
	}
	
	private ItemListener itemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			pgUpdate dbExec = new pgUpdate();
			
			if (!((JCheckBox) e.getSource()).isSelected()) {
				if (JOptionPane.showConfirmDialog(null, "Deselected attachment will be removed. Proceed?", 
						"Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					dbExec.executeUpdate("update rf_dcrf_attachments \n" +
						"set status_id = 'I' \n" +
						"where dcrf_no = '"+strDCRF+"' \n" +
						"and attachment_desc = '"+((JCheckBox) e.getSource()).getText()+"'", true);
					dbExec.commit();

					pgSelect dbSel = new pgSelect();
					dbSel.select("select sp_update_attachment_no('"+strDCRF+"')");
					
					JOptionPane.showMessageDialog(null, "Attachment removed");
				}
			}
			
			FillCenter();
		}
	};
}

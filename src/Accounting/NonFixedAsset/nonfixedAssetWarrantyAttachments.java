package Accounting.NonFixedAsset;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import Accounting.FixedAssets.panelAssetInformation;
import Database.pgSelect;
import Functions.FncFileChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_warrantyAttachment;

public class nonfixedAssetWarrantyAttachments extends JPanel implements _GUI {

	private static final long serialVersionUID = 1L;

	private JButton btnAdd;
	private JButton btnView; 
	
	private JPanel panCenter; 
	
	
	
	
	
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private JCheckBox[] chkAttachments; 
	
	public static String strassetwar;
	public static JButton btndownload;
	private static JFileChooser chooser1;
	private static File file;
	private String temp_folder;
	private String file_name;
	private File uploaded_file = null;
	private FileOutputStream fos;
	public String attachmentdesc;
	private Integer intAttCount;
	private JCheckBox[] chkAttachments_no;
	private JScrollPane scrollAttachment;
	private String output_stream;
	public static JList rowheaderAttachment;
	public static _JTableMain tblAttachment;
	public static model_warrantyAttachment modelAttachment;
	private  static Integer intCount; 
	//public static BufferedImage image =  dlg_assetwarranty_attachment_viewer.image;
	
	
	public nonfixedAssetWarrantyAttachments(String assetno) {
		this.strassetwar = assetno; 
		
		initGUI(); 
		if(NonFixedAsset.btnsave.isEnabled()) {
			btnAdd.setEnabled(true);
			btndownload.setEnabled(false);
		}else {
			btnAdd.setEnabled(false);
			btndownload.setEnabled(true);
		}
	}

	public nonfixedAssetWarrantyAttachments(LayoutManager layout) {
		super(layout);
		
		initGUI(); 
	}

	public nonfixedAssetWarrantyAttachments(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public nonfixedAssetWarrantyAttachments(LayoutManager layout, boolean isDoubleBuffered) {
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
				//FillCenter(); 
				{
					scrollAttachment = new JScrollPane();
					panCenter.add(scrollAttachment, BorderLayout.CENTER);
					scrollAttachment.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelAttachment = new model_warrantyAttachment();
						tblAttachment = new _JTableMain(modelAttachment);
						scrollAttachment.setViewportView(tblAttachment);
						
						tblAttachment.getColumnModel().getColumn(0).setPreferredWidth(30);
						tblAttachment.getColumnModel().getColumn(1).setPreferredWidth(50);
						tblAttachment.getColumnModel().getColumn(2).setPreferredWidth(70);
					}
				}
				
				
				{
					rowheaderAttachment = tblAttachment.getRowHeader();
					scrollAttachment.setRowHeaderView(rowheaderAttachment);
					scrollAttachment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
				
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 2, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnAdd = new JButton("Upload"); 
						panEnd.add(btnAdd); 
						btnAdd.setFont(font11);
						btnAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								addAttachment(); 
							}
						});
					}
					{
						btndownload = new JButton("Download");
						panEnd.add(btndownload);
						btndownload.setFont(font11);
						btndownload.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
									System.out.println("hasCheckedAssets: "+hasCheckedAssets());
									if(hasCheckedAssets()) {
										try{
											openPDF();
											CenterDisplay();
											
										}catch (IOException a) {
											a.printStackTrace();
										} catch (OutOfMemoryError er){
											
											JOptionPane.showMessageDialog(getTopLevelAncestor(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
										}
									}else {
										JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select asset warranty to download!", "", JOptionPane.INFORMATION_MESSAGE);
									}
							}
						});
					}
					/*{
						btnView = new JButton("View"); 
						panEnd.add(btnView); 
						btnView.setFont(font11);
						btnView.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								dlg_assetwarranty_attachment_viewer dialog = new dlg_assetwarranty_attachment_viewer(FncGlobal.homeMDI, "Attachments", false, strassetwar);
								
								final Toolkit toolkit = Toolkit.getDefaultToolkit();
								final Dimension screenSize = toolkit.getScreenSize();
								
								dialog.setSize(screenSize.width-100, screenSize.height-50);
								dialog.setResizable(false);
								dialog.setVisible(true);
								
							}
						});
					}*/
				}
			}
		}
		CenterDisplay();
	}
	
	protected Boolean hasCheckedAssets(){
		Boolean chk =false;
		for(int x=0; x<tblAttachment.getRowCount(); x++){
			
			if(tblAttachment.getValueAt(x, 0).equals(true)) {
				chk= true;
				break;
			}else {
				chk= false;
			}
		}
		return chk;
	}
	
	private void openPDF() throws IOException{
		
		for(int x=0; x<modelAttachment.getRowCount(); x++) {
			
			Boolean selected = false; 
					
			try {
				selected = (Boolean) modelAttachment.getValueAt(x, 0);
			} catch (Exception e) {
				selected = false; 
			}
					
			System.out.println("Selected: "+selected );
			
			if(selected.equals(true)) {
				
				Integer attachmentNo = (Integer) (modelAttachment.getValueAt(x, 1));
				System.out.println("attachmentNo: "+attachmentNo);
				Connection connection = null;
				
				try {
					connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery("select attachment_img from tbl_asset_warranty_attachments where asset_no = '"+strassetwar+"' and attachment_no= '"+attachmentNo+"'  and status_id = 'A' and asset_type =  'nonfixedasset' ");
					System.out.println("rs:"+"select attachment_img from tbl_asset_warranty_attachments where asset_no = '"+strassetwar+"' and attachment_no= '"+attachmentNo+"'  and status_id = 'A' and asset_type =  'nonfixedasset' ");
					
					//get the extension for the file 
					pgSelect db = new pgSelect();
					String strsql = "select split_part(attachment_desc, '.',2),attachment_no from tbl_asset_warranty_attachments where asset_no = '"+strassetwar+"' and attachment_no= '"+attachmentNo+"' and status_id = 'A' and asset_type =  'nonfixedasset' ";
					db.select(strsql);
					if(db != null) {
						attachmentdesc = (String) db.getResult()[0][0];
					}
					
					if(UserInfo.EmployeeCode.equals("900767")) {

						temp_folder = "/home/pc-114l/Desktop/Desktop/test_dir";//Directory where the the file will be saved.
					}else {
						temp_folder = System.getProperty("user.home");//Directory where the the file will be saved.
					} 
					System.out.println("temp_folder"+ temp_folder);
					file_name = String.format("%s - %s."+attachmentdesc+"", "Warranty for asset ", strassetwar+"-"+attachmentNo);//Filename of the downloaded file
					output_stream = String.format("%s/%s", temp_folder, file_name);
					System.out.println("file_name: "+file_name);
					System.out.println("output_stream: "+output_stream);
					fos = new FileOutputStream(output_stream);
					rs.next();
					byte[] fileBytes = rs.getBytes(1);
					fos.write(fileBytes);
					rs.close();
					fos.close();
					
					uploaded_file = new File(output_stream);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
        Desktop.getDesktop().open(uploaded_file);
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
	        	
	        	System.out.println("path:"+path );
	        	System.out.println("GetFileName:"+GetFileName(path) );
	        	System.out.println("listOfFiles:"+listOfFiles );
	        }
			
			JOptionPane.showMessageDialog(null, "Attachments successfully added!");
		}
		
		//DataChangeRequest.dialog.dispose();
		//FillCenter();
		CenterDisplay();
	}
	
	private void saveAttachments(File file, String strFilename) {
		FileInputStream fis = null;
		PreparedStatement ps;
		
		Integer intAtt = FncGlobal.GetInteger("select coalesce(max(attachment_no), 0)+1 \n" +
				"from tbl_asset_warranty_attachments \n" +
				"where asset_no = '"+strassetwar+"' and status_id = 'A' and asset_type =  'nonfixedasset'"); 
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			ps = FncGlobal.connection.prepareStatement("insert into tbl_asset_warranty_attachments (asset_no, attachment_no, attachment_desc, attachment_img, created_by, date_created, status_id,asset_type) \n" +
					"values ('"+strassetwar+"', '"+intAtt+"'::int, '"+strFilename+"', ?, '"+UserInfo.EmployeeCode+"', now(), 'A', 'nonfixedasset')");
			
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
	
	public static void CenterDisplay(){
		modelAttachment.clear();
		modelAttachment.setEditable(false);
		
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderAttachment.setModel(listModel);//Setting of listModel into rowHeader.
		
		String sql="select * from tbl_asset_warranty_attachments where asset_no = '"+strassetwar+"' and status_id = 'A' and asset_type =  'nonfixedasset'";
		
		System.out.println("CenterDisplay: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){

				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelAttachment.addRow(db.getResult()[x]);

				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelAttachment.getRowCount());
			}
		}
		tblAttachment.packAll();
	}

}

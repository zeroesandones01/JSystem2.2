package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.FileChooserUI;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncODSFileFilter;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class UploadPayment extends _JInternalFrame implements _GUI {
	
	static String title = "Upload payment";
	Dimension frameSize = new Dimension(400, 250);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JPanel pnlSelect;
	private JTextField txtSelectFile;
	private JFileChooser fileHeader;
	private JComboBox cbType;
	protected JFileChooser fileDetail;
	private JTextField txtSelectFileHeader;
	private JTextField txtSelectFileDetail;

	public UploadPayment() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadPayment(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadPayment(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlFileUploading = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlFileUploading, BorderLayout.CENTER);
				pnlFileUploading.setBorder(JTBorderFactory.createTitleBorder("Upload File"));
				{
					JPanel pnlDivider = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlDivider.setPreferredSize(new Dimension(0, 180));
					pnlFileUploading.add(pnlDivider);
//					{
//						String[] petStrings = { "Header", "Detail" };
//						cbType = new JComboBox(petStrings);
//						pnlFileUploadingNorth.add(cbType, BorderLayout.NORTH);
//						cbType.setSelectedIndex(0);
//					}
					{
						JPanel pnlFileUploadingNorth1 = new JPanel(new BorderLayout(5, 5));
						pnlDivider.add(pnlFileUploadingNorth1, BorderLayout.NORTH);
						{
							JPanel pnlWest = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlWest.setPreferredSize(new Dimension(80, 20));
							pnlFileUploadingNorth1.add(pnlWest, BorderLayout.WEST);
							pnlWest.add(new JLabel("Header: "));
						}

						{
							JPanel pnlCenter = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlFileUploadingNorth1.add(pnlCenter, BorderLayout.CENTER);
							txtSelectFileHeader = new JTextField();
							pnlCenter.add(txtSelectFileHeader);
							txtSelectFileHeader.setEditable(false);
						}
						
						{
							JPanel pnlEast = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlFileUploadingNorth1.add(pnlEast, BorderLayout.EAST);
							{
								JButton btnSelectFile = new JButton("Search");
								pnlEast.add(btnSelectFile);
								btnSelectFile.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(fileHeader == null){
											fileHeader = new JFileChooser();
											fileHeader.setAcceptAllFileFilterUsed(false);
											fileHeader.addChoosableFileFilter(new FncODSFileFilter(new String[] { "xml" }, "XML (*.xml, *.dtg)"));

											Action details = fileHeader.getActionMap().get("viewTypeDetails");
											details.actionPerformed(null);
										}
										
										fileHeader.setSelectedFile(new File(""));
										int status = fileHeader.showOpenDialog(null);
										if (status == JFileChooser.APPROVE_OPTION) {
											if(fileHeader.getSelectedFile().equals(null)){
												JOptionPane.showMessageDialog(getParent(), "No Selected Document");
												return;
											}

											System.out.printf("Header File: %s%n", fileHeader.getSelectedFile().toString());
											String selectedFile = fileHeader.getSelectedFile().toString();
											txtSelectFileHeader.setText(selectedFile);
										}
									}
								});
							}
						}
					}
					
					{
						JPanel pnlFileUploadingNorth1 = new JPanel(new BorderLayout(5, 5));
						pnlDivider.add(pnlFileUploadingNorth1, BorderLayout.NORTH);
						{
							JPanel pnlWest = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlWest.setPreferredSize(new Dimension(80, 20));
							pnlFileUploadingNorth1.add(pnlWest, BorderLayout.WEST);
							pnlWest.add(new JLabel("Detail: "));
						}

						{
							JPanel pnlCenter = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlFileUploadingNorth1.add(pnlCenter, BorderLayout.CENTER);
							txtSelectFileDetail = new JTextField();
							pnlCenter.add(txtSelectFileDetail);
							txtSelectFileDetail.setEditable(false);
						}
						
						{
							JPanel pnlEast = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlFileUploadingNorth1.add(pnlEast, BorderLayout.EAST);
							{
								JButton btnSelectFile = new JButton("Search");
								pnlEast.add(btnSelectFile);
								btnSelectFile.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(fileDetail == null){
											fileDetail = new JFileChooser();
											fileDetail.setAcceptAllFileFilterUsed(false);
											fileDetail.addChoosableFileFilter(new FncODSFileFilter(new String[] { "xml" }, "XML (*.xml, *.dtg)"));

											Action details = fileDetail.getActionMap().get("viewTypeDetails");
											details.actionPerformed(null);
										}
										
										fileDetail.setSelectedFile(new File(""));
										int status = fileDetail.showOpenDialog(null);
										if (status == JFileChooser.APPROVE_OPTION) {
											if(fileDetail.getSelectedFile().equals(null)){
												JOptionPane.showMessageDialog(getParent(), "No Selected Document");
												return;
											}

											System.out.printf("Detail File: %s%n", fileDetail.getSelectedFile().toString());
											String selectedFile = fileDetail.getSelectedFile().toString();
											txtSelectFileDetail.setText(selectedFile);
										}
									}
								});
							}
						}
					}
				}
				{
					JPanel pnlFileUploadingSouth = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlFileUploading.add(pnlFileUploadingSouth, BorderLayout.SOUTH);
					{
						JButton btnProcess = new JButton("Process");
						pnlFileUploadingSouth.add(btnProcess);
						btnProcess.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String pathHeader = fileHeader.getSelectedFile().getAbsolutePath().toString();
								String pathDetail = fileDetail.getSelectedFile().getAbsolutePath().toString();
//								String type = cbType.getSelectedItem().toString();
								int response = JOptionPane.showConfirmDialog(null, "Upload payment in \nFrom file " + fileHeader.getSelectedFile().getName()+ "\n"+ fileDetail.getSelectedFile().getName(), "Upload ?", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.PLAIN_MESSAGE);
								if(response == 0) {
									if(pathHeader != null && pathDetail != null) {
											UPHeader uph = new UPHeader();
											UPDetail upd = new UPDetail();
											uph.importXML(pathHeader);
											upd.importXML(pathDetail);
											uploadData();
									}
									txtSelectFileHeader.setText("");
									txtSelectFileDetail.setText("");
								}
								
							}
						});
					}
				}
			}
		}
	}
	
	private void uploadData() {
		pgSelect pg = new pgSelect();
		String SQL = "select sp_upload_payments('"+UserInfo.EmployeeCode+"')";
		System.out.println(SQL);
		pg.select(SQL, "ERROR", true);
	}
	
}

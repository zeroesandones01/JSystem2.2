package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_dcrf_attachment_viewer;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import interfaces._GUI;

public class DCRFMonitoring_COO2 extends JPanel implements _GUI {

	private static final long serialVersionUID = 7521212833590342054L;

	private static pgSelect dbExec;  
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);
	private static Font font10bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);
	private static JXPanel panDCRF; 
	private static Color color; 
	private static Integer intCount = 0; 

	private Color colorHeader = new Color(91, 149, 249);
	private Color color1 = new Color(207, 224, 254);
	private Color color2 = Color.WHITE; 

	private Border border_highlight = BorderFactory.createLineBorder(new Color(3, 95, 254));

	static Border lineBorderRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBorderBlue = BorderFactory.createLineBorder(Color.BLUE);

	private Timer timer; 

	public DCRFMonitoring_COO2() {
		initGUI();
	}

	public DCRFMonitoring_COO2(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public DCRFMonitoring_COO2(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public DCRFMonitoring_COO2(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		dbExec = new pgSelect(); 
		final JPanel panel = new JPanel(new BorderLayout(0, 0));

		panDCRF = new JXPanel(new BorderLayout(0, 0));

		Count();  

		panel.add(panDCRF, BorderLayout.CENTER); 
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private void createGUI() {
		final JLabel[] lblDCRFNo;
		final JLabel[] lblDept;
		final JLabel[] lblError; 

		final JLabel[] lblButton1;
		final JLabel[] lblButton2;
		final JLabel[] lblButton3;
		final JLabel[] lblButton4;


		final JPanel[] panContainer;

		panDCRF.removeAll();
		panDCRF.setLayout(new BorderLayout(0, 0));

		panDCRF.repaint();
		panDCRF.revalidate();

		lblDCRFNo = new JLabel[intCount]; 
		lblDept = new JLabel[intCount];
		lblError = new JLabel[intCount];

		lblButton1 = new JLabel[intCount];
		lblButton2 = new JLabel[intCount];
		lblButton3 = new JLabel[intCount];
		lblButton4 = new JLabel[intCount];

		panContainer = new JXPanel[intCount];

		JPanel panDCRF1 = new JPanel(new GridLayout(1, 1, 0, 0));
		panDCRF.add(panDCRF1, BorderLayout.PAGE_START); 
		panDCRF1.setPreferredSize(new Dimension(0, 40));
		{
			panContainer[0] = new JXPanel(new BorderLayout(0, 0)); 
			panDCRF1.add(panContainer[0]); 
			{
				{
					JXPanel panLine = new JXPanel(new GridLayout(1, 3, 0, 0)); 
					panContainer[0].add(panLine, BorderLayout.LINE_START); 
					panLine.setPreferredSize(new Dimension(250, 0));
					{
						{
							lblDCRFNo[0] = new JLabel(dbExec.getResult()[0][0].toString()); 
							panLine.add(lblDCRFNo[0]); 
							lblDCRFNo[0].setHorizontalAlignment(JLabel.CENTER);
							lblDCRFNo[0].setOpaque(true);
							lblDCRFNo[0].setForeground(new Color(30, 37, 47));
							lblDCRFNo[0].setBackground(alternatingColor(0));
							lblDCRFNo[0].setFont(font11bold);					
						}
						{
							lblDept[0] = new JLabel(dbExec.getResult()[0][1].toString()); 
							panLine.add(lblDept[0]); 
							lblDept[0].setHorizontalAlignment(JLabel.CENTER);
							lblDept[0].setFont(font11);
							lblDept[0].setOpaque(true);
							lblDept[0].setForeground(new Color(30, 37, 47));
							lblDept[0].setBackground(alternatingColor(0));
							lblDept[0].setFont(font11bold);
						}
						{
							String str = dbExec.getResult()[0][3].toString(); 
							str = str.replace(" ", "<br>"); 
							str = "<html><center>".concat(str).concat("</center></html>"); 

							lblError[0] = new JLabel(str); 
							panLine.add(lblError[0]); 
							lblError[0].setHorizontalAlignment(JLabel.CENTER);
							lblError[0].setFont(font11);
							lblError[0].setOpaque(true);
							lblError[0].setForeground(new Color(30, 37, 47));
							lblError[0].setBackground(alternatingColor(0));
							lblError[0].setFont(font11bold);
						}
					}
				}
				{
					JXPanel panCenter = new JXPanel(new GridLayout(1, 4, 0, 0));
					panContainer[0].add(panCenter, BorderLayout.CENTER); 
					{
						{	
							lblButton1[0] = new JLabel("");
							panCenter.add(lblButton1[0]); 
							lblButton1[0].setHorizontalAlignment(JLabel.CENTER);
							lblButton1[0].setFont(font10bold);
							lblButton1[0].setName(dbExec.getResult()[0][0].toString());
							lblButton1[0].setToolTipText("Preview DCRF");
							lblButton1[0].setOpaque(true);

							lblButton1[0].setBackground(alternatingColor(0));
						}
						{
							lblButton2[0] = new JLabel("");
							panCenter.add(lblButton2[0]); 
							lblButton2[0].setHorizontalAlignment(JLabel.CENTER);
							lblButton2[0].setFont(font10bold);
							lblButton2[0].setName(dbExec.getResult()[0][0].toString());
							lblButton2[0].setToolTipText("View Attachments");
							lblButton2[0].setOpaque(true);

							lblButton2[0].setBackground(alternatingColor(0));
						}
						{

							lblButton3[0] = new JLabel("");
							panCenter.add(lblButton3[0]); 
							lblButton3[0].setHorizontalAlignment(JLabel.CENTER);
							lblButton3[0].setFont(font10bold);
							lblButton3[0].setName(dbExec.getResult()[0][0].toString());
							lblButton3[0].setOpaque(true);
							lblButton3[0].setToolTipText("Approve");
							lblButton3[0].setBackground(alternatingColor(0));
						}
						{
							lblButton4[0] = new JLabel("");
							panCenter.add(lblButton4[0]); 
							lblButton4[0].setHorizontalAlignment(JLabel.CENTER);
							lblButton4[0].setFont(font10bold);
							lblButton4[0].setName(dbExec.getResult()[0][0].toString());
							lblButton4[0].setOpaque(true);
							lblButton4[0].setToolTipText("Disapprove");
							lblButton4[0].setBackground(alternatingColor(0));
						}
					}
				}
			}
		}

		JPanel panDCRF2 = new JPanel(new GridLayout(intCount-1, 1, 0, 0)); 
		{
			for (int x=1; x<dbExec.getRowCount(); x++) {
				panContainer[x] = new JXPanel(new BorderLayout(0, 0)); 
				panDCRF2.add(panContainer[x]); 
				{
					{
						JXPanel panLine = new JXPanel(new GridLayout(1, 3, 0, 0)); 
						panContainer[x].add(panLine, BorderLayout.LINE_START); 
						panLine.setPreferredSize(new Dimension(250, 0));
						{
							{
								lblDCRFNo[x] = new JLabel(dbExec.getResult()[x][0].toString()); 
								panLine.add(lblDCRFNo[x]); 
								lblDCRFNo[x].setHorizontalAlignment(JLabel.CENTER);
								lblDCRFNo[x].setOpaque(true);

								lblDCRFNo[x].setBackground(alternatingColor(x));
								lblDCRFNo[x].setForeground(Color.DARK_GRAY);
								lblDCRFNo[x].setFont(font11bold);						
							}
							{
								lblDept[x] = new JLabel(dbExec.getResult()[x][1].toString()); 
								panLine.add(lblDept[x]); 
								lblDept[x].setHorizontalAlignment(JLabel.CENTER);
								lblDept[x].setFont(font11);
								lblDept[x].setOpaque(true);

								lblDept[x].setBackground(alternatingColor(x));
								lblDept[x].setForeground(Color.DARK_GRAY);

								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblDept[x].setFont(font11bold);
								}
							}
							{
								String str = dbExec.getResult()[x][3].toString(); 
								str = str.replace(" ", "<br>"); 
								str = "<html><center>".concat(str).concat("</center></html>"); 

								lblError[x] = new JLabel(str); 
								panLine.add(lblError[x]); 
								lblError[x].setHorizontalAlignment(JLabel.CENTER);
								lblError[x].setFont(font11);
								lblError[x].setOpaque(true);

								lblError[x].setBackground(alternatingColor(x));
								lblError[x].setForeground(Color.DARK_GRAY);

								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblError[x].setFont(font11bold);
								}
							}
						}
					}
					{
						JXPanel panCenter = new JXPanel(new GridLayout(1, 4, 0, 0));
						panContainer[x].add(panCenter, BorderLayout.CENTER); 
						{
							{	
								if (x==0) {
									lblButton1[x] = new JLabel("");
								} else {
									lblButton1[x] = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("Images/preview-doc.png")));
									lblButton1[x].addMouseListener(mouseListener);
								}

								panCenter.add(lblButton1[x]); 
								lblButton1[x].setHorizontalAlignment(JLabel.CENTER);
								lblButton1[x].setFont(font10bold);
								lblButton1[x].setName(dbExec.getResult()[x][0].toString());
								lblButton1[x].setToolTipText("Preview DCRF");
								lblButton1[x].setOpaque(true);

								lblButton1[x].setBackground(alternatingColor(x));
							}
							{
								if (x==0) {
									lblButton2[x] = new JLabel("");
								} else {
									lblButton2[x] = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("Images/attach-icon.png")));
									lblButton2[x].addMouseListener(mouseListener);
								}

								panCenter.add(lblButton2[x]); 
								lblButton2[x].setHorizontalAlignment(JLabel.CENTER);
								lblButton2[x].setFont(font10bold);
								lblButton2[x].setName(dbExec.getResult()[x][0].toString());
								lblButton2[x].setToolTipText("View Attachments");
								lblButton2[x].setOpaque(true);

								lblButton2[x].setBackground(alternatingColor(x));
							}
							{
								if (x==0) {
									lblButton3[x] = new JLabel("");
								} else {
									Boolean bol = (Boolean) dbExec.getResult()[x][10];
									if(bol) {
										lblButton3[x] = new JLabel("RECEIVE");
										lblButton3[x].setToolTipText("Receive");
										lblButton3[x].addMouseListener(mouseListener); 

									}else {
										lblButton3[x] = new JLabel("APPROVE");
										lblButton3[x].addMouseListener(mouseListener);     								}

								}
								Boolean bol = (Boolean) dbExec.getResult()[x][10];
								if(bol) {
									panCenter.add(lblButton3[x]); 
									lblButton3[x].setHorizontalAlignment(JLabel.CENTER);
									lblButton3[x].setFont(font10bold);
									lblButton3[x].setName(dbExec.getResult()[x][0].toString());
									lblButton3[x].setOpaque(true);
									lblButton3[x].setToolTipText("Receive");
									lblButton3[x].setBackground(alternatingColor(x));
								}else {
									panCenter.add(lblButton3[x]); 
									lblButton3[x].setHorizontalAlignment(JLabel.CENTER);
									lblButton3[x].setFont(font10bold);
									lblButton3[x].setName(dbExec.getResult()[x][0].toString());
									lblButton3[x].setOpaque(true);
									lblButton3[x].setToolTipText("Approve");
									lblButton3[x].setBackground(alternatingColor(x));
								}
							}
							{
								if (x==0) {
									lblButton4[x] = new JLabel("");
								} else {
									Boolean bol = (Boolean) dbExec.getResult()[x][10];
									if(bol) {
										lblButton4[x] = new JLabel("");
										lblButton4[x].addMouseListener(mouseListener); 
									}else {
										lblButton4[x] = new JLabel("DISAPPROVE");
										lblButton4[x].addMouseListener(mouseListener); 
									}
								}

								panCenter.add(lblButton4[x]); 
								lblButton4[x].setHorizontalAlignment(JLabel.CENTER);
								lblButton4[x].setFont(font10bold);
								lblButton4[x].setName(dbExec.getResult()[x][0].toString());
								lblButton4[x].setOpaque(true);
								lblButton4[x].setToolTipText("Disapprove");
								lblButton4[x].setBackground(alternatingColor(x));
							}
						}
					}
				}
				panContainer[x].setPreferredSize(new Dimension(460, 40));
			}	
		}

		JScrollPane scr = new JScrollPane(panDCRF2); 
		panDCRF.add(scr, BorderLayout.CENTER);

		panDCRF.repaint();
		panDCRF.revalidate();
	}

	public static void main (String args[]) {
		DCRFMonitoring_COO2 dcrf = new DCRFMonitoring_COO2(); 

		JFrame frame = new JFrame("Tae"); 
		frame.setContentPane(dcrf);
		frame.setSize(300, 300);
		frame.setVisible(true);

	}

	private MouseListener mouseListener = new MouseListener() {

		public void mouseReleased(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(color);
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(color);
			((JLabel) e.getSource()).setBorder(null);
		}

		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setBorder(border_highlight);
		}

		public void mouseClicked(MouseEvent e) {
			FncGlobal.startProgress("Please wait..");

			if (!(((JLabel) e.getSource()).getToolTipText().equals("Preview DCRF") || ((JLabel) e.getSource()).getToolTipText().equals("View Attachments")) && !(UserInfo.EmployeeCode.equals("900876") || UserInfo.EmployeeCode.equals("900748") || UserInfo.EmployeeCode.equals("000645") || UserInfo.EmployeeCode.equals("901172")) ) {
				JOptionPane.showMessageDialog(null, "You do not have authority to perform the selected action.");
			} else {
				execute(((JLabel) e.getSource()).getToolTipText(), ((JLabel) e.getSource()).getName());

				if (((JLabel) e.getSource()).getToolTipText().equals("Preview DCRF") || ((JLabel) e.getSource()).getToolTipText().equals("View Attachments")) {
					dbExec.select("select * from view_dcrf_monitoring_coo()");				
					intCount = ((dbExec.getRowCount()<6)?6:dbExec.getRowCount()); 
					createGUI();	
				}
			}

			FncGlobal.stopProgress();
		}
	};

	private void Count() {

		ActionListener timerListener = new ActionListener() {

			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				dbExec.select("select * from view_dcrf_monitoring_coo()");
				intCount = ((dbExec.getRowCount()<6)?6:dbExec.getRowCount()); 

				reGUI(); 
			}
		};

		timer = new Timer(5000, timerListener);
		timer.setInitialDelay(0);
		timer.start();

	}

	private void reGUI() {
		SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				createGUI();
				return null;
			}
		}; 
		sw.execute(); 
	}

	private Color alternatingColor(Integer intRow) {
		Color color = null; 
		if (intRow==0) {
			color = colorHeader; 
		} else {
			if (intRow%2==0) {
				color = color1; 
			} else {
				color = color2; 
			}
		}

		return  color; 
	}


	private void execute(String strCommand, String strDCRF) {
		String strExecute = ""; 
		pgUpdate dbExec = new pgUpdate(); 

		switch(strCommand) {
		case "View Attachments":
			dlg_dcrf_attachment_viewer dialog = new dlg_dcrf_attachment_viewer(FncGlobal.homeMDI, "Attachments", false, strDCRF);

			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();

			dialog.setSize(screenSize.width-100, screenSize.height-50);
			dialog.setResizable(false);
			dialog.setVisible(true);

			break;
		case "Approve":

			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header \n" +
						"set app_vcoo_date = now() \n" +		
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				timer.restart();
				JOptionPane.showMessageDialog(null, "Success!");
			}

			break;
		case "Disapprove":

			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header \n" +
						"set disapp_vcoo_date = now(), \n" +
						"status = 'DISAPPROVED' \n" + 	
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				createGUI();
				JOptionPane.showMessageDialog(null, "Success!");
			}

			break;
		case "Receive":

			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header set " +
						"status = 'RECEIVED',  \n" +		
						"received_by = '"+UserInfo.EmployeeCode+"' , \n" +	
						"date_received = now() \n" +		
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				timer.restart();
				JOptionPane.showMessageDialog(null, "Success!");
			}
			break;
		default:
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("dcrf_no", new Integer(strDCRF));
			FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", "DCRF", "", mapParameters);
		}
	}
}

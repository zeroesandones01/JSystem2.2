package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.decorator.HighlightPredicate.DepthHighlightPredicate;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_dcrf_attachment_viewer;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import interfaces._GUI;

public class DCRFMonitoring3 extends JPanel implements _GUI {

	private static final long serialVersionUID = 3280869172665362662L;
	private static pgSelect dbExec; 
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);
	private static Font font10bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);

	private static JPanel panDCRF;
	private static JPanel panEnd; 
	private static Color color; 

	private static Color colorHeader = new Color(91, 149, 249);
	private static Color color2 = new Color(207, 224, 254);
	private static Color colorHeaderFore = new Color(30, 37, 47);
	
	private Border border_highlight = BorderFactory.createLineBorder(new Color(3, 95, 254));
	
	private HashMap<String, String> hashLogin = new HashMap<>(); 

	public DCRFMonitoring3() {
		initGUI();
	}

	public DCRFMonitoring3(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public DCRFMonitoring3(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public DCRFMonitoring3(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		/*
		panDCRF = new JPanel(new BorderLayout(0, 0));
		createGUI();
		this.setPreferredSize(new Dimension(970, 900)); 
		this.setLayout(new BorderLayout(0, 0));
		this.add(panDCRF, BorderLayout.CENTER);
		 */

		panDCRF = new JPanel(new BorderLayout(0, 0));
		createGUI(UserInfo.EmployeeCode);
		this.setPreferredSize(new Dimension(970, 900)); 
		this.setLayout(new BorderLayout(0, 0));
		this.add(panDCRF, BorderLayout.CENTER);

		pgSelect db = new pgSelect(); 
		db.select("select * \n" +
				"from \n" +
				"( \n" +
				"select distinct upper(x1.login_id)::varchar as login_id, y.emp_code::varchar as emp_code, 1::int as sort \n" +
				"from mf_jsystem_modules x \n" +
				"inner join em_employee y on x.in_charge = y.emp_code  \n" +
				"inner join rf_entity z on y.entity_id = z.entity_id \n" +
				"inner join rf_system_user x1 on y.emp_code = x1.emp_code \n" +
				") a \n" +
				"order by a.sort, a.login_id");

		for (int x=0; x<db.getRowCount(); x++) {
			hashLogin.put(db.getResult()[x][0].toString(), db.getResult()[x][1].toString()); 
		}

		final JRadioButton[] chkJST = new JRadioButton[db.getRowCount()];

		panEnd = new JPanel(new GridLayout(1, db.getRowCount(), 5, 5)); 
		this.add(panEnd, BorderLayout.PAGE_END);
		panEnd.setPreferredSize(new Dimension(0, 40));
		panEnd.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		{
			for (int x=0; x<db.getRowCount(); x++) {
				chkJST[x] = new JRadioButton(db.getResult()[x][0].toString()); 
				panEnd.add(chkJST[x]); 
				chkJST[x].setFont(font11);
				chkJST[x].setHorizontalAlignment(JCheckBox.CENTER);
				chkJST[x].setSelected(db.getResult()[x][1].toString().equals(UserInfo.EmployeeCode));
				chkJST[x].setFocusable(false);
				chkJST[x].addItemListener(itemListener);
			}		         
		}
	}

	private static void execute(String strCommand, String strDCRF) {
		String strExecute = ""; 
		pgUpdate dbExec = new pgUpdate(); 

		switch(strCommand) {
		case "RECEIVE":
			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header \n" +
						"set status = 'RECEIVED', received_by = '"+UserInfo.EmployeeCode+"', \n" +
						"date_received = now() \n" +	
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				JOptionPane.showMessageDialog(null, "Success!");
			}

			break;
		case "DELETE":
			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header \n" +
						"set status = 'INACTIVE', date_deleted = now() \n" +	
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				JOptionPane.showMessageDialog(null, "Success!");
			}

			break;
		case "FIX":
			if (JOptionPane.showConfirmDialog(null, strCommand.concat(" DCRF#").concat(strDCRF).concat("?"), "Confirm?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				strExecute = "update rf_dcrf_header \n" +
						"set status = 'FIXED', fixed_by = '"+UserInfo.EmployeeCode+"', \n" +	
						"admin_remarks = '', date_fixed = now() \n" +	
						"where dcrf_no = '"+strDCRF+"'";

				dbExec.executeUpdate(strExecute, true);
				dbExec.commit();

				JOptionPane.showMessageDialog(null, "Success!");
			}

			break;
		case "VIEW":
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("dcrf_no", new Integer(strDCRF));
			FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", "DCRF", "", mapParameters);
			break;
		case "Attachments":
			dlg_dcrf_attachment_viewer dialog = new dlg_dcrf_attachment_viewer(FncGlobal.homeMDI, "Attachments", false, strDCRF);
			
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();
			
			dialog.setSize(screenSize.width-100, screenSize.height-50);
			dialog.setResizable(false);
			dialog.setVisible(true);

			break;
		default:

		}
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
			((JLabel) e.getSource()).setForeground(Color.BLUE);
			((JLabel) e.getSource()).setBorder(border_highlight);
		}

		public void mouseClicked(MouseEvent e) {
			FncGlobal.startProgress("Please wait..");

			if ( (((JLabel) e.getSource()).getText().equals("DELETE") || ((JLabel) e.getSource()).getText().equals("RECEIVE")) &&  Home_JSystem.deptHead(UserInfo.EmployeeCode))  {
				JOptionPane.showMessageDialog(null, "You do not have authority to perform the selected action.");
			} else {
				execute(((JLabel) e.getSource()).getText(), ((JLabel) e.getSource()).getName());
				
				if (!((JLabel) e.getSource()).getText().equals("VIEW") && !((JLabel) e.getSource()).getText().equals("Attachments") && !((JLabel) e.getSource()).getText().equals("")) {
					createGUI(UserInfo.EmployeeCode);	
				}
			}

			FncGlobal.stopProgress();
		}
	};

	private void createGUI(String strUser) {
		final JLabel[] lblDCRFNo;
		final JLabel[] lblError;
		final JLabel[] lblDept;
		final JLabel[] lblRequester;
		final JLabel[] lblInCharge; 
		final JLabel[] lblLapse;
		final JLabel[] lblStatus;
		
		final JLabel[] lblButton0;
		final JLabel[] lblButton1;
		final JLabel[] lblButton2;

		dbExec = new pgSelect(); 
		dbExec.select("select * from view_dcrf_monitoring('"+strUser+"')");
		
		System.out.println("select * from view_dcrf_monitoring('"+strUser+"')");
		
		panDCRF.removeAll();

		lblDCRFNo = new JLabel[dbExec.getRowCount()]; 
		lblError = new JLabel[dbExec.getRowCount()];
		lblDept = new JLabel[dbExec.getRowCount()];
		lblRequester = new JLabel[dbExec.getRowCount()];
		lblInCharge = new JLabel[dbExec.getRowCount()];
		lblLapse = new JLabel[dbExec.getRowCount()];
		lblStatus = new JLabel[dbExec.getRowCount()];

		lblButton0 = new JLabel[dbExec.getRowCount()];
		lblButton1 = new JLabel[dbExec.getRowCount()];
		lblButton2 = new JLabel[dbExec.getRowCount()];

		JPanel panContainer = new JPanel(new BorderLayout(0, 0));
		panDCRF.add(panContainer, BorderLayout.CENTER);

		JPanel panDiv1 = new JPanel(new BorderLayout(0, 0)); 
		panContainer.add(panDiv1, BorderLayout.PAGE_START);                   
		panDiv1.setPreferredSize(new Dimension(0, 30));
		{
			{
				JPanel panLine = new JPanel(new GridLayout(1, 3, 0, 0)); 
				panDiv1.add(panLine, BorderLayout.LINE_START); 
				panLine.setPreferredSize(new Dimension(270, 0));
				{
					{
						JLabel labelDCRF = new JLabel("DCRF#"); 
						panLine.add(labelDCRF); 
						labelDCRF.setHorizontalAlignment(JLabel.CENTER);
						labelDCRF.setOpaque(true);
						labelDCRF.setBackground(colorHeader);
						labelDCRF.setForeground(colorHeaderFore);
						labelDCRF.setFont(font11bold);
					}
					{
						JLabel labelDepartment = new JLabel("Department"); 
						panLine.add(labelDepartment); 
						labelDepartment.setHorizontalAlignment(JLabel.CENTER);
						labelDepartment.setOpaque(true);
						labelDepartment.setBackground(colorHeader);
						labelDepartment.setForeground(colorHeaderFore);
						labelDepartment.setFont(font11bold);
					}
					{
						JLabel labelLapse = new JLabel("Lapse");
						panLine.add(labelLapse); 
						labelLapse.setHorizontalAlignment(JLabel.CENTER);
						labelLapse.setFont(font11);
						labelLapse.setOpaque(true);
						labelLapse.setBackground(colorHeader);
						labelLapse.setForeground(colorHeaderFore);
						labelLapse.setFont(font11bold);
					}
				}
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(0, 0)); 
				panDiv1.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JLabel labelError = new JLabel("Error Type"); 
						panCenter.add(labelError, BorderLayout.LINE_START); 
						labelError.setHorizontalAlignment(JLabel.CENTER);
						labelError.setOpaque(true);
						labelError.setBackground(colorHeader);
						labelError.setForeground(colorHeaderFore);
						labelError.setFont(font11bold);
						labelError.setPreferredSize(new Dimension(125, 0));
					}
					{
						JPanel panDiv = new JPanel(new GridLayout(1, 2, 0, 0)); 
						panCenter.add(panDiv, BorderLayout.CENTER); 
						{
							{
								JLabel labelRequester = new JLabel("Requester"); 
								panDiv.add(labelRequester); 
								labelRequester.setHorizontalAlignment(JLabel.CENTER);
								labelRequester.setOpaque(true);
								labelRequester.setBackground(colorHeader);
								labelRequester.setForeground(colorHeaderFore);
								labelRequester.setFont(font11bold);
							}
							{
								JLabel labelInCharge = new JLabel("In-Charge"); 
								panDiv.add(labelInCharge); 
								labelInCharge.setHorizontalAlignment(JLabel.CENTER);
								labelInCharge.setOpaque(true);
								labelInCharge.setBackground(colorHeader);
								labelInCharge.setForeground(colorHeaderFore);
								labelInCharge.setFont(font11bold);
							}

						}
					}
					{
						JLabel labelStatus = new JLabel("Status"); 
						panCenter.add(labelStatus, BorderLayout.LINE_END); 
						labelStatus.setHorizontalAlignment(JLabel.CENTER);
						labelStatus.setFont(font11bold);
						labelStatus.setOpaque(true);
						labelStatus.setBackground(colorHeader);
						labelStatus.setForeground(colorHeaderFore);
						labelStatus.setPreferredSize(new Dimension(80, 0));
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(0, 0)); 
				panDiv1.add(panEnd, BorderLayout.LINE_END); 
				panEnd.setBackground(colorHeader);
				panEnd.setPreferredSize(new Dimension(300, 0));
				{

				}
			}         
		}

		if (dbExec.getRowCount()>0) {
			JPanel panDiv2 = new JPanel(new GridLayout(dbExec.getRowCount(), 1, 0, 0)); 
			{
				final JPanel[] panContent = new JPanel[dbExec.getRowCount()]; 

				for (int x=0; x<dbExec.getRowCount(); x++) {
					panContent[x] = new JPanel(new BorderLayout(0, 0)); 
					panDiv2.add(panContent[x]);
					panContent[x].setPreferredSize(new Dimension(0, 40));
					{
						{
							JPanel panLine = new JPanel(new GridLayout(1, 3, 0, 0)); 
							panContent[x].add(panLine, BorderLayout.LINE_START); 
							panLine.setPreferredSize(new Dimension(270, 0));
							{
								{
									JPanel panDCRFNo = new JPanel(new BorderLayout(0, 0)); 
									panLine.add(panDCRFNo, BorderLayout.CENTER); 
									{
										lblDCRFNo[x] = new JLabel(dbExec.getResult()[x][0].toString()); 
										panDCRFNo.add(lblDCRFNo[x]); 
										lblDCRFNo[x].setHorizontalAlignment(JLabel.CENTER);
										lblDCRFNo[x].setForeground(Color.DARK_GRAY);
										lblDCRFNo[x].setFont(font11bold);
										
										if (x%2==0) {
											lblDCRFNo[x].setOpaque(true);
											lblDCRFNo[x].setBackground(color2);
										}
									}
								}
								{
									JPanel panDept = new JPanel(new BorderLayout(0, 0)); 
									panLine.add(panDept, BorderLayout.CENTER); 
									{
										lblDept[x] = new JLabel(dbExec.getResult()[x][1].toString()); 
										panDept.add(lblDept[x]); 
										lblDept[x].setHorizontalAlignment(JLabel.CENTER);
										lblDept[x].setFont(font11);

										if (x%2==0) {
											lblDept[x].setOpaque(true);
											lblDept[x].setBackground(color2);
										}
									}
								}
								{
									JPanel panLapse = new JPanel(new BorderLayout(0, 0)); 
									panLine.add(panLapse, BorderLayout.CENTER); 
									{
										if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
											lblLapse[x] = new JLabel(dbExec.getResult()[x][6].toString());
										} else {
											lblLapse[x] = new JLabel(dbExec.getResult()[x][6].toString()+" day(s)");
										}


										panLapse.add(lblLapse[x]); 
										lblLapse[x].setHorizontalAlignment(JLabel.CENTER);
										lblLapse[x].setFont(font11);

										if (x%2==0) {
											lblLapse[x].setOpaque(true);
											lblLapse[x].setBackground(color2);
										}
									}
								}
							}
						}
						{
							JPanel panCenter = new JPanel(new BorderLayout(0, 0)); 
							panContent[x].add(panCenter, BorderLayout.CENTER); 
							{
								{
									JPanel panError = new JPanel(new BorderLayout(0, 0)); 
									panCenter.add(panError, BorderLayout.LINE_START); 
									{
										lblError[x] = new JLabel(dbExec.getResult()[x][3].toString()); 
										panError.add(lblError[x]); 
										lblError[x].setHorizontalAlignment(JLabel.CENTER);
										lblError[x].setFont(font11);

										if (x%2==0) {
											lblError[x].setOpaque(true);
											lblError[x].setBackground(color2);
										}
									}
									panError.setPreferredSize(new Dimension(125, 0));
								}
								{
									JPanel panDivider = new JPanel(new GridLayout(1, 2, 0, 0)); 
									panCenter.add(panDivider, BorderLayout.CENTER); 
									{
										{
											JPanel panRequester = new JPanel(new BorderLayout(0, 0)); 
											panDivider.add(panRequester); 
											{
												lblRequester[x] = new JLabel(dbExec.getResult()[x][2].toString()); 
												panRequester.add(lblRequester[x]); 
												lblRequester[x].setHorizontalAlignment(JLabel.LEFT);
												lblRequester[x].setFont(font11);

												if (x%2==0) {
													lblRequester[x].setOpaque(true);
													lblRequester[x].setBackground(color2);
												}
											}
										}
										{
											JPanel panInCharge = new JPanel(new BorderLayout(0, 0)); 
											panDivider.add(panInCharge); 
											{
												lblInCharge[x] = new JLabel(dbExec.getResult()[x][10].toString()==null?"":dbExec.getResult()[x][10].toString()); 
												panInCharge.add(lblInCharge[x]); 
												lblInCharge[x].setHorizontalAlignment(JLabel.LEFT);
												lblInCharge[x].setFont(font11);

												if (x%2==0) {
													lblInCharge[x].setOpaque(true);
													lblInCharge[x].setBackground(color2);
												}
											}
										}
									}
								}
								{
									JPanel panStatus = new JPanel(new BorderLayout(0, 0)); 
									panCenter.add(panStatus, BorderLayout.LINE_END);
									panStatus.setPreferredSize(new Dimension(80, 0));
									{
										lblStatus[x] = new JLabel(dbExec.getResult()[x][8].toString()); 
										panStatus.add(lblStatus[x]); 
										lblStatus[x].setHorizontalAlignment(JLabel.CENTER);
										lblStatus[x].setFont(font11);

										if (x%2==0) {
											lblStatus[x].setOpaque(true);
											lblStatus[x].setBackground(color2);
										}
									}
								}
							}
						}
						{
							JPanel panEnd = new JPanel(new GridLayout(1, 3, 0, 0)); 
							panContent[x].add(panEnd, BorderLayout.LINE_END); 
							panEnd.setPreferredSize(new Dimension(300, 0));
							{
								{
									JPanel panButton1 = new JPanel(new BorderLayout(0, 0)); 
									panEnd.add(panButton1);
									{
										lblButton1[x] = new JLabel(
												(dbExec.getResult()[x][5].toString()==null)?"":dbExec.getResult()[x][5].toString()
												); 
										panButton1.add(lblButton1[x]); 
										lblButton1[x].setHorizontalAlignment(JLabel.CENTER);
										lblButton1[x].setFont(font11bold);
										lblButton1[x].addMouseListener(mouseListener);
										lblButton1[x].setName(dbExec.getResult()[x][0].toString());
										lblButton1[x].setForeground(Color.DARK_GRAY);

										if (x%2==0) {
											lblButton1[x].setOpaque(true);
											lblButton1[x].setBackground(color2);
										}
									}
								}
								{
									JPanel panButton2 = new JPanel(new BorderLayout(0, 0)); 
									panEnd.add(panButton2); 
									{
										lblButton2[x] = new JLabel(
												(dbExec.getResult()[x][4].toString()==null)?"":dbExec.getResult()[x][4].toString()
												);  
										panButton2.add(lblButton2[x]); 
										lblButton2[x].setHorizontalAlignment(JLabel.CENTER);
										lblButton2[x].setFont(font11bold);
										lblButton2[x].addMouseListener(mouseListener);
										lblButton2[x].setName(dbExec.getResult()[x][0].toString());
										lblButton2[x].setForeground(Color.DARK_GRAY);
										
										if (x%2==0) {
											lblButton2[x].setOpaque(true);
											lblButton2[x].setBackground(color2);
										}

										color = lblButton2[x].getForeground(); 
									}
								}
								{
									JPanel panButton0 = new JPanel(new BorderLayout(0, 0)); 
									panEnd.add(panButton0);
									{
										lblButton0[x] = new JLabel("Attachments"); 
										panButton0.add(lblButton0[x]); 
										lblButton0[x].setHorizontalAlignment(JLabel.CENTER);
										lblButton0[x].setFont(font11bold);
										lblButton0[x].addMouseListener(mouseListener);
										lblButton0[x].setName(dbExec.getResult()[x][0].toString());
										lblButton0[x].setForeground(Color.DARK_GRAY);
										
										if (x%2==0) {
											lblButton0[x].setOpaque(true);
											lblButton0[x].setBackground(color2);
										}
									}
								}
							}
						}
					}
				}

			}

			JScrollPane scr = new JScrollPane(panDiv2); 
			panDiv2.setPreferredSize(new Dimension(0, 40*dbExec.getRowCount()));
			panContainer.add(scr, BorderLayout.CENTER); 
		}

		panDCRF.repaint();
		panDCRF.revalidate();
	}

	ItemListener itemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if (((JRadioButton) e.getSource()).isSelected()) {
				System.out.println("");
				System.out.println("Selected: "+((JRadioButton) e.getSource()).getText());

				createGUI(hashLogin.get(((JRadioButton) e.getSource()).getText())); 

				RemoveSelectExcept(((JRadioButton) e.getSource()).getText());
			}
		}
	};

	private void RemoveSelectExcept(String strName) {
		for (Component comp : panEnd.getComponents()) {
			if (comp instanceof JRadioButton) {                             
				if (!((JRadioButton) comp).getText().equals(strName)) {
					((JRadioButton) comp).setSelected(false);
				}
			}
		}
	}
}

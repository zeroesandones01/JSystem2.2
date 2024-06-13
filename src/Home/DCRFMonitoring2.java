package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import interfaces._GUI;

public class DCRFMonitoring2 extends JPanel implements _GUI {

	private static final long serialVersionUID = 3280869172665362662L;
	private static pgSelect dbExec; 
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);
	
	private static JPanel panDCRF;
	private static Color color; 
	
	private static Color colorHeader = new Color(91, 149, 249);
	private Color color1 = Color.WHITE; 
	private Color color2 = new Color(232, 240, 254);
	private Color colorHighlight = new Color(207, 226, 243); 
	private static Color colorHeaderFore = new Color(30, 37, 47); 
	
	public DCRFMonitoring2() {
		initGUI();
	}

	public DCRFMonitoring2(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public DCRFMonitoring2(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public DCRFMonitoring2(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		panDCRF = new JPanel(new BorderLayout(0, 0));
		createGUI();
		this.setPreferredSize(new Dimension(970, 900)); 
		this.setLayout(new BorderLayout(0, 0));
		this.add(panDCRF, BorderLayout.CENTER);
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
			default:
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("dcrf_no", new Integer(strDCRF));
				FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", "DCRF", "", mapParameters);
		}
	}
	
	private static MouseListener mouseListener = new MouseListener() {
		
		public void mouseReleased(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(color);
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(color);
		}

		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(Color.BLUE);
		}

		public void mouseClicked(MouseEvent e) {
			FncGlobal.startProgress("Please wait..");

			if (!((JLabel) e.getSource()).getText().equals("VIEW") && !UserInfo.EmployeeCode.equals("900449") && !UserInfo.EmployeeCode.equals("900748")) {
				JOptionPane.showMessageDialog(null, "You do not have authority to perform the selected action.");
			} else {
				execute(((JLabel) e.getSource()).getText(), ((JLabel) e.getSource()).getName());
				createGUI(); 
			}
			
			FncGlobal.stopProgress();
		}
	};
	
	private static void createGUI() {
		final JLabel[] lblDCRFNo;
		final JLabel[] lblError;
		final JLabel[] lblDept;
		final JLabel[] lblRequester;
		final JLabel[] lblInCharge; 
		final JLabel[] lblLapse;
		final JLabel[] lblStatus;
		final JLabel[] lblButton1;
		final JLabel[] lblButton2;
		
		dbExec = new pgSelect(); 
		dbExec.select("select * from view_dcrf_monitoring()");
		
		panDCRF.removeAll();
		
		lblDCRFNo = new JLabel[dbExec.getRowCount()]; 
		lblError = new JLabel[dbExec.getRowCount()];
		lblDept = new JLabel[dbExec.getRowCount()];
		lblRequester = new JLabel[dbExec.getRowCount()];
		lblInCharge = new JLabel[dbExec.getRowCount()];
		lblLapse = new JLabel[dbExec.getRowCount()];
		lblStatus = new JLabel[dbExec.getRowCount()];
		
		lblButton1 = new JLabel[dbExec.getRowCount()];
		lblButton2 = new JLabel[dbExec.getRowCount()];
		
		JPanel panDiv1 = new JPanel(new BorderLayout(0, 0)); 
		panDCRF.add(panDiv1, BorderLayout.PAGE_START); 		
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
						labelError.setFont(font11);
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

				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(0, 0)); 
				panDiv1.add(panEnd, BorderLayout.LINE_END); 
				panEnd.setPreferredSize(new Dimension(320, 0));
				{
					{
						JLabel labelStatus = new JLabel("Status"); 
						panEnd.add(labelStatus, BorderLayout.LINE_START); 
						labelStatus.setHorizontalAlignment(JLabel.CENTER);
						labelStatus.setFont(font11bold);
						labelStatus.setPreferredSize(new Dimension(80, 0));
						labelStatus.setOpaque(true);
						labelStatus.setBackground(colorHeader);
						labelStatus.setForeground(colorHeaderFore);
					}
					{
						JPanel panButtons = new JPanel(new GridLayout(1, 3, 0, 0)); 
						panEnd.add(panButtons, BorderLayout.CENTER); 
						panButtons.setBackground(colorHeader);
						{
							
						}
					}
				}
			}	
		}
		
		JPanel panDiv2 = new JPanel(new BorderLayout(0, 0)); 
		JScrollPane scr = new JScrollPane(panDiv2);
		panDCRF.add(scr, BorderLayout.CENTER); 
		{
			{
				JPanel panLine = new JPanel(new GridLayout(1, 3, 0, 0)); 
				panDiv2.add(panLine, BorderLayout.LINE_START); 
				panLine.setPreferredSize(new Dimension(270, 0));
				{
					{
						JPanel panDCRFNo = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
						panLine.add(panDCRFNo); 
						{
							for (int x=1; x<dbExec.getRowCount(); x++) {
								lblDCRFNo[x] = new JLabel(dbExec.getResult()[x][0].toString()); 
								panDCRFNo.add(lblDCRFNo[x]); 
								lblDCRFNo[x].setHorizontalAlignment(JLabel.CENTER);
								
								if (x%2==0) {
									lblDCRFNo[x].setOpaque(true);
									lblDCRFNo[x].setBackground(Color.LIGHT_GRAY);
								}
								
								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblDCRFNo[x].setFont(font11bold);
								} else {
									lblDCRFNo[x].setFont(font11bold);
									lblDCRFNo[x].setForeground(Color.DARK_GRAY);
								}
							}
						}
					}
					{
						JPanel panDept = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
						panLine.add(panDept); 
						{
							for (int x=1; x<dbExec.getRowCount(); x++) {
								lblDept[x] = new JLabel(dbExec.getResult()[x][1].toString()); 
								panDept.add(lblDept[x]); 
								lblDept[x].setHorizontalAlignment(JLabel.CENTER);
								lblDept[x].setFont(font11);
								
								if (x%2==0) {
									lblDept[x].setOpaque(true);
									lblDept[x].setBackground(Color.LIGHT_GRAY);
								}
								
								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblDept[x].setFont(font11bold);
								} else {
									lblDept[x].setForeground(Color.DARK_GRAY);
								}
							}
						}
					}
					{
						JPanel panLapse = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
						panLine.add(panLapse); 
						{
							for (int x=1; x<dbExec.getRowCount(); x++) {
								
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
									lblLapse[x].setBackground(Color.LIGHT_GRAY);
								}
								
								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblLapse[x].setFont(font11bold);
								} else {
									lblLapse[x].setForeground(Color.DARK_GRAY);
								}
							}
						}
					}
				}
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(0, 0)); 
				panDiv2.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JPanel panError = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
						panCenter.add(panError, BorderLayout.LINE_START); 
						{
							for (int x=1; x<dbExec.getRowCount(); x++) {
								lblError[x] = new JLabel(dbExec.getResult()[x][3].toString()); 
								panError.add(lblError[x]); 
								lblError[x].setHorizontalAlignment(JLabel.CENTER);
								lblError[x].setFont(font11);
								
								if (x%2==0) {
									lblError[x].setOpaque(true);
									lblError[x].setBackground(Color.LIGHT_GRAY);
								}
								
								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblError[x].setFont(font11bold);
								} else {
									lblError[x].setForeground(Color.DARK_GRAY);
								}
							}
						}
						panError.setPreferredSize(new Dimension(125, 0));
					}
					{
						JPanel panDiv = new JPanel(new GridLayout(1, 2, 0, 0)); 
						panCenter.add(panDiv, BorderLayout.CENTER); 
						{
							{
								JPanel panRequester = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
								panDiv.add(panRequester); 
								{
									for (int x=1; x<dbExec.getRowCount(); x++) {
										lblRequester[x] = new JLabel(dbExec.getResult()[x][2].toString()); 
										panRequester.add(lblRequester[x]); 
										lblRequester[x].setHorizontalAlignment(JLabel.LEFT);
										lblRequester[x].setFont(font11);
										
										if (x%2==0) {
											lblRequester[x].setOpaque(true);
											lblRequester[x].setBackground(Color.LIGHT_GRAY);
										}
										
										if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
											lblRequester[x].setFont(font11bold);
										} else {
											lblRequester[x].setForeground(Color.DARK_GRAY);
										}
									}
								}
							}
							{
								JPanel panInCharge = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
								panDiv.add(panInCharge); 
								{
									for (int x=1; x<dbExec.getRowCount(); x++) {
										lblInCharge[x] = new JLabel(dbExec.getResult()[x][10].toString()==null?"":dbExec.getResult()[x][10].toString()); 
										panInCharge.add(lblInCharge[x]); 
										lblInCharge[x].setHorizontalAlignment(JLabel.LEFT);
										lblInCharge[x].setFont(font11);
										
										if (x%2==0) {
											lblInCharge[x].setOpaque(true);
											lblInCharge[x].setBackground(Color.LIGHT_GRAY);
										}
										
										if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
											lblInCharge[x].setFont(font11bold);
										} else {
											lblInCharge[x].setForeground(Color.DARK_GRAY);
										}
									}
								}
							}
						}
					}
					
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(0, 0)); 
				panDiv2.add(panEnd, BorderLayout.LINE_END); 
				panEnd.setPreferredSize(new Dimension(320, 0));
				{
					{
						JPanel panStatus = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
						panEnd.add(panStatus, BorderLayout.LINE_START);
						panStatus.setPreferredSize(new Dimension(80, 0));
						{
							for (int x=1; x<dbExec.getRowCount(); x++) {
								lblStatus[x] = new JLabel(dbExec.getResult()[x][8].toString()); 
								panStatus.add(lblStatus[x]); 
								lblStatus[x].setHorizontalAlignment(JLabel.CENTER);
								lblStatus[x].setFont(font11);
								
								if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
									lblStatus[x].setFont(font11bold);
								} else {
									lblStatus[x].setForeground(Color.DARK_GRAY);
								}
								
								if (x%2==0) {
									lblStatus[x].setOpaque(true);
									lblStatus[x].setBackground(Color.LIGHT_GRAY);
								}
							}
						}
					}
					{
						JPanel panButtons = new JPanel(new GridLayout(1, 3, 0, 0)); 
						panEnd.add(panButtons, BorderLayout.CENTER); 
						{
							{
								JPanel panButton0 = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
								panButtons.add(panButton0);
								{
									for (int x=1; x<dbExec.getRowCount(); x++) {
										JLabel lblBlank; 
										if (dbExec.getResult()[x][0].toString().equals("DCRF#")) {
											lblBlank = new JLabel("");
										} else {
											lblBlank = new JLabel("↠");
										}
										 
										panButton0.add(lblBlank); 
										lblBlank.setHorizontalAlignment(JLabel.CENTER);
										lblBlank.setFont(font11bold);
										lblBlank.setForeground(Color.DARK_GRAY);
										
										if (x%2==0) {
											lblBlank.setOpaque(true);
											lblBlank.setBackground(Color.LIGHT_GRAY);
										}
									}
								}
							}
							{
								JPanel panButton1 = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
								panButtons.add(panButton1);
								{
									for (int x=1; x<dbExec.getRowCount(); x++) {
										lblButton1[x] = new JLabel(
												(dbExec.getResult()[x][5].toString()==null)?"":dbExec.getResult()[x][5].toString()
												); 
										panButton1.add(lblButton1[x]); 
										lblButton1[x].setHorizontalAlignment(JLabel.CENTER);
										lblButton1[x].setFont(font11);
										lblButton1[x].addMouseListener(mouseListener);
										lblButton1[x].setName(dbExec.getResult()[x][0].toString());
										
										if (x%2==0) {
											lblButton1[x].setOpaque(true);
											lblButton1[x].setBackground(Color.LIGHT_GRAY);
										}
									}
								}
							}
							{
								JPanel panButton2 = new JPanel(new GridLayout(dbExec.getRowCount()-1, 1, 0, 0)); 
								panButtons.add(panButton2); 
								{
									for (int x=1; x<dbExec.getRowCount(); x++) {
										lblButton2[x] = new JLabel(
												(dbExec.getResult()[x][4].toString()==null)?"":dbExec.getResult()[x][4].toString()
												);  
										panButton2.add(lblButton2[x]); 
										lblButton2[x].setHorizontalAlignment(JLabel.CENTER);
										lblButton2[x].setFont(font11);
										lblButton2[x].addMouseListener(mouseListener);
										lblButton2[x].setName(dbExec.getResult()[x][0].toString());
										
										if (x%2==0) {
											lblButton2[x].setOpaque(true);
											lblButton2[x].setBackground(Color.LIGHT_GRAY);
										}
										
										color = lblButton2[x].getForeground(); 
									}
								}
							}
						}
					}
				}
			}
		}
		
		panDCRF.repaint();
		panDCRF.revalidate();
	}
}

package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.UserInfo;

public class dashBoard extends JXPanel {

	private static final long serialVersionUID = -3784698789202185778L;
	public static Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, new Integer("14"));
	public static Font font2 = new Font(Font.SANS_SERIF, Font.PLAIN, new Integer("10"));

	private static JLabel[] lblDCRFNo;
	private static JLabel[] lblDept;
	private static JLabel[] lblDesc; 
	
	private static JButton[] btnDCRF; 

	public dashBoard() {
		ui(); 
	}

	public dashBoard(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		ui(); 
	}

	public dashBoard(LayoutManager layout) {
		super(layout);
		ui(); 
	}

	public dashBoard(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		ui(); 
	}

	public void ui() {
		this.setLayout(new BorderLayout(1,1));
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setBackground(new Color(0,0,0,0));
		
		JXPanel panMain = new JXPanel(new GridLayout(2, 1, 5, 10));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setOpaque(false);
		{
			{
				JXPanel panCenter1 = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter1);
				panCenter1.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.7f));
				panCenter1.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					{
						JLabel lblTitle = new JLabel("Pending DCRF"); 
						panCenter1.add(lblTitle, BorderLayout.PAGE_START);
						lblTitle.setPreferredSize(new Dimension(0, 20));
						lblTitle.setFont(font1);
						lblTitle.setForeground(Color.WHITE);
					}
					{
						pgSelect dbExec = new pgSelect();
						dbExec.select("select distinct x.dcrf_no, z.dept_alias, left(x.change_reason, 50) as name, x.dcrf_no, get_no_of_working_days(x.date_received,current_date)\n" + 
								"from rf_dcrf_header x\n" + 
								"inner join rf_dcrf_detail y on x.dcrf_no = y.dcrf_no\n" + 
								"inner join mf_department z on x.dept_code = z.dept_code\n" + 
								"where x.status = 'RECEIVED' \n" + 
								"and exists(select * from mf_privileges a where upper(a.privileges) = upper(y.module) and a.in_charge = '"+UserInfo.EmployeeCode+"') \n" + 
								"order by get_no_of_working_days(x.date_received, current_date) desc, x.dcrf_no limit 6");
						
						Integer intRow = 0; 
						intRow = (dbExec.getRowCount()<10?dbExec.getRowCount():6);
						
						JXPanel panList = new JXPanel(new BorderLayout(5, 5)); 
						panCenter1.add(panList, BorderLayout.CENTER); 
						panList.setOpaque(false);
						{	
							{
								JXPanel panDiv1 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panList.add(panDiv1, BorderLayout.LINE_START); 
								panDiv1.setPreferredSize(new Dimension(75, 0));
								panDiv1.setOpaque(false);
								{
									{
										JXPanel panDCRFNo = new JXPanel(new GridLayout(6, 1, 5, 5)); 
										panDiv1.add(panDCRFNo); 
										panDCRFNo.setOpaque(false);
										{
											lblDCRFNo = new JLabel[10]; 
											
											for (int x=0; x<intRow; x++) {
												lblDCRFNo[x] = new JLabel(dbExec.getResult()[x][0].toString()); 
												panDCRFNo.add(lblDCRFNo[x]); 
												lblDCRFNo[x].setHorizontalAlignment(JLabel.LEADING);
												lblDCRFNo[x].setFont(font2);
												lblDCRFNo[x].setForeground(Color.WHITE);
											}
										}
									}
									{
										JXPanel panDept = new JXPanel(new GridLayout(6, 1, 5, 5)); 
										panDiv1.add(panDept); 
										panDept.setOpaque(false);
										{
											lblDept = new JLabel[10]; 
											
											for (int x=0; x<intRow; x++) {
												lblDept[x] = new JLabel(dbExec.getResult()[x][1].toString()); 
												panDept.add(lblDept[x]); 
												lblDept[x].setHorizontalAlignment(JLabel.LEADING);
												lblDept[x].setFont(font2);
												lblDept[x].setForeground(Color.WHITE);
											}
										}
									}
								}
							}
							{
								JXPanel panDiv2 = new JXPanel(new GridLayout(6, 2, 5, 5)); 
								panList.add(panDiv2, BorderLayout.CENTER); 
								panDiv2.setOpaque(false);
								{
									lblDesc = new JLabel[10]; 
									
									for (int x=0; x<intRow; x++) {
										lblDesc[x] = new JLabel(dbExec.getResult()[x][2].toString()); 
										panDiv2.add(lblDesc[x]); 
										lblDesc[x].setHorizontalAlignment(JLabel.LEADING);
										lblDesc[x].setFont(font2);
										lblDesc[x].setForeground(Color.WHITE);
									}
								}
							}
							{
								JXPanel panDiv3 = new JXPanel(new GridLayout(6, 2, 5, 5)); 
								panList.add(panDiv3, BorderLayout.LINE_END);
								panDiv3.setPreferredSize(new Dimension(50, 0));
								panDiv3.setOpaque(false);
								{
									btnDCRF = new JButton[10]; 
									
									for (int x=0; x<intRow; x++) {
										btnDCRF[x] = new JButton("View"); 
										panDiv3.add(btnDCRF[x]); 
										btnDCRF[x].setFont(font2);
										btnDCRF[x].setForeground(Color.WHITE);
										btnDCRF[x].setActionCommand(dbExec.getResult()[x][0].toString());
										btnDCRF[x].setOpaque(false);
									}
								}
								
							}
						}
					}
				}
			}
			{
				JXPanel panCenter2 = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter2);
				panCenter2.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.7f));
				panCenter2.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					{
						JLabel lblTitle = new JLabel("Fixed DCRF"); 
						panCenter2.add(lblTitle, BorderLayout.PAGE_START);
						lblTitle.setPreferredSize(new Dimension(0, 20));
						lblTitle.setFont(font1);
						lblTitle.setForeground(Color.WHITE);
					}
					{
						pgSelect dbExec = new pgSelect();
						dbExec.select("select distinct x.dcrf_no, z.dept_alias, left(x.change_reason, 50) as name, x.dcrf_no, get_no_of_working_days(x.date_received,current_date)\n" + 
								"from rf_dcrf_header x\n" + 
								"inner join rf_dcrf_detail y on x.dcrf_no = y.dcrf_no\n" + 
								"inner join mf_department z on x.dept_code = z.dept_code\n" + 
								"where x.status = 'RECEIVED' \n" + 
								"and exists(select * from mf_privileges a where upper(a.privileges) = upper(y.module) and a.in_charge = '"+UserInfo.EmployeeCode+"') \n" + 
								"order by get_no_of_working_days(x.date_received, current_date) desc, x.dcrf_no limit 6");
						
						Integer intRow = 0; 
						intRow = (dbExec.getRowCount()<10?dbExec.getRowCount():6);
						
						JXPanel panList = new JXPanel(new BorderLayout(5, 5)); 
						panCenter2.add(panList, BorderLayout.CENTER); 
						panList.setOpaque(false);
						{	
							{
								JXPanel panDiv1 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panList.add(panDiv1, BorderLayout.LINE_START); 
								panDiv1.setPreferredSize(new Dimension(75, 0));
								panDiv1.setOpaque(false);
								{
									{
										JXPanel panDCRFNo = new JXPanel(new GridLayout(6, 1, 5, 5)); 
										panDiv1.add(panDCRFNo); 
										panDCRFNo.setOpaque(false);
										{
											lblDCRFNo = new JLabel[10]; 
											
											for (int x=0; x<intRow; x++) {
												lblDCRFNo[x] = new JLabel(dbExec.getResult()[x][0].toString()); 
												panDCRFNo.add(lblDCRFNo[x]); 
												lblDCRFNo[x].setHorizontalAlignment(JLabel.LEADING);
												lblDCRFNo[x].setFont(font2);
												lblDCRFNo[x].setForeground(Color.WHITE);
											}
										}
									}
									{
										JXPanel panDept = new JXPanel(new GridLayout(6, 1, 5, 5)); 
										panDiv1.add(panDept); 
										panDept.setOpaque(false);
										{
											lblDept = new JLabel[10]; 
											
											for (int x=0; x<intRow; x++) {
												lblDept[x] = new JLabel(dbExec.getResult()[x][1].toString()); 
												panDept.add(lblDept[x]); 
												lblDept[x].setHorizontalAlignment(JLabel.LEADING);
												lblDept[x].setFont(font2);
												lblDept[x].setForeground(Color.WHITE);
											}
										}
									}
								}
							}
							{
								JXPanel panDiv2 = new JXPanel(new GridLayout(6, 2, 5, 5)); 
								panList.add(panDiv2, BorderLayout.CENTER); 
								panDiv2.setOpaque(false);
								{
									lblDesc = new JLabel[10]; 
									
									for (int x=0; x<intRow; x++) {
										lblDesc[x] = new JLabel(dbExec.getResult()[x][2].toString()); 
										panDiv2.add(lblDesc[x]); 
										lblDesc[x].setHorizontalAlignment(JLabel.LEADING);
										lblDesc[x].setFont(font2);
										lblDesc[x].setForeground(Color.WHITE);
									}
								}
							}
							{
								JXPanel panDiv3 = new JXPanel(new GridLayout(6, 2, 5, 5)); 
								panList.add(panDiv3, BorderLayout.LINE_END);
								panDiv3.setPreferredSize(new Dimension(50, 0));
								panDiv3.setOpaque(false);
								{
									btnDCRF = new JButton[10]; 
									
									for (int x=0; x<intRow; x++) {
										btnDCRF[x] = new JButton("View"); 
										panDiv3.add(btnDCRF[x]); 
										btnDCRF[x].setFont(font2);
										btnDCRF[x].setForeground(Color.WHITE);
										btnDCRF[x].setActionCommand(dbExec.getResult()[x][0].toString());
										btnDCRF[x].setOpaque(false);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private String strFixed() {
		return ""; 
	}
}

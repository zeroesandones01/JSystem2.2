package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.poi.hssf.util.HSSFColor.RED;
import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JDateChooser;

import Buyers.ClientServicing.pnlReferences_BankAccounts;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import System.DataChangeRequest;
import interfaces._GUI;

public class pnlDcrf  extends JXPanel implements _GUI,ActionListener{
	private Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	private static final long serialVersionUID = -373592247766315546L;
	private JLabel[] lblDcrf;
	private JButton[] btnPrev;
	private JButton[] btnApp;
	private JButton[] btnDisapp;
	private JScrollPane scrollPane;
	
	private JPanel pnlMain;
	private JPanel pnlScrollLbl;
	static JPanel pnlTrueMain;
	private JPanel mainCenter;
	private JPanel pnlPrev;
	private JPanel pnlApp;
	private JPanel pnlDis;

	public pnlDcrf (){
		initGUI();
	}
	public pnlDcrf (boolean  isDoubleBuffered ){
		super(isDoubleBuffered);
		initGUI();
	}
	public pnlDcrf(LayoutManager layout){
		super(layout);
	}
	public pnlDcrf (LayoutManager layout, boolean isDoubleBuffered){
		super(layout,isDoubleBuffered);
		initGUI();

	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(1, 1));
		this.setOpaque(true);
		this.setBorder(empty);
		this.setBackground(new Color(0, 0, 0, 0));

		{
			pnlTrueMain = new JPanel(new BorderLayout());
			this.add(pnlTrueMain);
			pnlTrueMain.setPreferredSize(new Dimension(400, 200));
			pnlTrueMain.setBorder(components._JTBorderFactory.createTitleBorder("- \t DCRF Checker"));
			pnlTrueMain.setOpaque(false);
//			scrollPane.repaint();
//			scrollPane.revalidate();
//			pnlTrueMain.revalidate();
//			pnlTrueMain.repaint();
//			repeat();
			{
				pnlMain = new JPanel(new BorderLayout(5,5));
				pnlMain.setBorder(new EmptyBorder(10,10,10,10));
				pnlMain.setOpaque(false);
				//pnlMain.setPreferredSize(new Dimension(10,10));
				repeat();
				
				//			this.add(pnlMain);
				//			pnlMain.setPreferredSize(new Dimension (425,db.getRowCount()*47));
						
			}
		}

	}
private void repeat(){
		
		System.out.println("Dumaan dito");
		 pgSelect db = new pgSelect();
			db.select("select a.dcrf_no from rf_dcrf_header a \n" + 
					"inner join em_employee b on a.created_by = b.emp_code \n" + 
					"inner join rf_entity c on b.entity_id = c.entity_id \n" + 
					"where status = 'ACTIVE' /*authorized_date IS NULL*/\n" + 
					"order by a.date_created");
			
			lblDcrf = new JLabel[db.getRowCount()];
			btnPrev = new JButton[db.getRowCount()];
			btnApp = new JButton[db.getRowCount()];
			btnDisapp = new JButton[db.getRowCount()];
			int row_count = db.getRowCount();
			
			if(row_count<= 6){
				row_count = 6;
			}else{
				row_count = db.getRowCount();
			}
			
//			pnlMain = new JPanel(new BorderLayout(5,5));
////			pnlMain.setBorder(new EmptyBorder(10,10,10,10));
//			pnlMain.setPreferredSize(new Dimension(0,150));
			
			{
				pnlScrollLbl = new JPanel(new GridLayout(row_count,1,5,5));
//				pnlScrollLbl.setBorder(new EmptyBorder(10,10,10,10));
				pnlScrollLbl.setBorder(new EmptyBorder(10,2,10,2));
				pnlMain.add(pnlScrollLbl,BorderLayout.WEST);
				//						pnlScrollLbl.setBorder(BorderFactory.createLineBorder(Color.RED));


				for (int x = 0; x<db.getRowCount(); x++){
					lblDcrf[x] = new JLabel("DCRF#"+db.getResult()[x][0].toString());
					pnlScrollLbl.add(lblDcrf[x]);

				}
			}
			
				mainCenter = new JPanel(new GridLayout(1,3,5,5));
				pnlMain.add(mainCenter,BorderLayout.CENTER);
//				mainCenter.setPreferredSize(new Dimension(0,150));
//				mainCenter.setBorder(BorderFactory.createLineBorder(Color.BLUE));

				{
					 pnlPrev = new JPanel(new GridLayout(row_count,1,5,5));
//					 pnlPrev = new JPanel(new GridLayout(db.getRowCount(),3,5,5));
//					pnlPrev.setBorder(new EmptyBorder(10,10,10,10));
					 pnlPrev.setBorder(new EmptyBorder(10,2,10,2));
					mainCenter.add(pnlPrev,BorderLayout.CENTER);
					int row = db.getRowCount();
					for(int x = 0;x<row;x++){
						btnPrev[x] = new JButton("Preview");
						pnlPrev.add(btnPrev[x]);
						btnPrev[x].setActionCommand("Preview");
						btnPrev[x].addActionListener(this);
						btnPrev[x].setName(db.getResult()[x][0].toString());
//						btnPrev[x].setPreferredSize(new Dimension(0,0));
					}
				}
				{
					pnlApp = new JPanel(new GridLayout(row_count,1,5,5));
//					pnlApp.setBorder(new EmptyBorder(10,10,10,10));
					pnlApp.setBorder(new EmptyBorder(10,2,10,2));
					mainCenter.add(pnlApp,BorderLayout.CENTER);
					for(int x = 0;x<db.getRowCount();x++){
						btnApp[x] = new JButton("Approve");
						pnlApp.add(btnApp[x]);
						btnApp[x].setActionCommand("Approve");
						btnApp[x].addActionListener(this);
						btnApp[x].setName(db.getResult()[x][0].toString());
					}
				}
				{
					 pnlDis = new JPanel(new GridLayout(row_count,1,5,5));
//					pnlDis.setBorder(new EmptyBorder(10,10,10,10));
					 pnlDis.setBorder(new EmptyBorder(10,2,10,3));
					mainCenter.add(pnlDis,BorderLayout.EAST);
					for(int x = 0;x<db.getRowCount();x++){
						btnDisapp[x] = new JButton("Disapprove");
						pnlDis.add(btnDisapp[x]);
						btnDisapp[x].setActionCommand("Disapprove");
						btnDisapp[x].addActionListener(this);
						btnDisapp[x].setName(db.getResult()[x][0].toString());
					}
				}
			
				scrollPane = new JScrollPane(pnlMain);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				pnlTrueMain.add(scrollPane,BorderLayout.CENTER);
				scrollPane.setOpaque(false);
				
//				scrollPane.setViewportView(pnlMain);
//				pnlTrueMain.repaint();
//				pnlTrueMain.revalidate();
				System.out.println("Dumaan dito");				
			
	}
	
	private void preview(String dcrf){	
		
		Integer dcrf_no = Integer.parseInt(dcrf);
		String criteria = "DCRF";		
		String reportTitle = String.format("%s (%s)", DataChangeRequest.title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("dcrf_no",dcrf_no);
		FncReport.generateReport("/Reports/rptDCRF_preview.jasper", reportTitle,DataChangeRequest.company, mapParameters);
	}
	
	private void approved(String dcrf){
		
		Integer dcrf_no = Integer.parseInt(dcrf);
		pgUpdate db = new pgUpdate();
		
		{
		String query = "UPDATE rf_dcrf_header set authorized_date = '"+FncGlobal.getDateToday()+"',status = 'APRROVED' WHERE dcrf_no = '"+dcrf_no+"'";
		db.executeUpdate(query, true);
		}
		db.commit();		
	
	}
	
	private void disApproved(String dcrf){
	
		Integer dcrf_no = Integer.parseInt(dcrf);
		pgUpdate db = new pgUpdate();
		
		{
		String query = "UPDATE rf_dcrf_header set disapproved_date = '"+FncGlobal.getDateToday()+"',status = 'DISAPRROVED' WHERE dcrf_no = '"+dcrf_no+"'";
		db.executeUpdate(query, true);
		}
		db.commit();
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Preview")){

			preview((((JButton) e.getSource()).getName()));
//			System.out.println("dcrf"+(((JButton) e.getSource()).getName()));
			
		}
		if(e.getActionCommand().equals("Approve")){
			
			approved((((JButton) e.getSource()).getName()));
			
			JOptionPane.showMessageDialog(this, "dcrf#: \t"+ (((JButton) e.getSource()).getName())+"\t has been approved");
			this.removeAll();
			initGUI();
			
		}
		if(e.getActionCommand().equals("Disapprove")){
			disApproved((((JButton) e.getSource()).getName()));
			
			JOptionPane.showMessageDialog(this, "dcrf#: \t"+ (((JButton) e.getSource()).getName())+"\t has been disapproved");
			this.removeAll();
			initGUI();
							
		}
	}

}

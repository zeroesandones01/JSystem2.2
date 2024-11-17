package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Functions.FncFocusTraversalPolicy;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class UDOAS_Monitoring extends _JInternalFrame implements _GUI, ActionListener{
	
	static String title = "UDOAS Monitoring";
	static Dimension frameSize = new Dimension(500, 200);
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	private JPanel pnlMain;
	private JButton btnPreview;
	private _JLookup lookupCompany;
	private _JLookup lookupProj;
	private _JLookup lookupPhase;
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;

	
	public UDOAS_Monitoring() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UDOAS_Monitoring(String title) {
		super(title);
		initGUI();
	}

	public UDOAS_Monitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		
		{
			pnlMain = new JPanel(new BorderLayout(5, 5)); 
			getContentPane().add(pnlMain, BorderLayout.CENTER); 
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5, 5)); 
				pnlMain.add(pnlCenter, BorderLayout.CENTER); 
				{
					JPanel pnlWest = new JPanel(new GridLayout(3, 2, 5, 5)); 
					pnlMain.add(pnlWest, BorderLayout.WEST); 
					pnlWest.setPreferredSize(new Dimension(120, 0));
					{
						JLabel lblComp = new JLabel("Company", JLabel.TRAILING); 
						pnlWest.add(lblComp); 
					}
					{
						lookupCompany = new _JLookup(null, "Company", 0); 
						pnlWest.add(lookupCompany); 
						lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							
							private String co_id;
							private String co_name;

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object data [] = ((_JLookup) event.getSource()).getDataSet();
								
								if(data != null) { 
									co_id = (String) data[0]; 
									co_name = (String) data[1]; 
									
									lookupProj.setLookupSQL(_JInternalFrame.SQL_PROJECT(co_id));
									txtCompany.setText(co_name);
									manager.focusNextComponent();
								} else {
									return;
								}
							}
						});
						
						
						
					}
					{
						JLabel lblProj = new JLabel("Project", JLabel.TRAILING); 
						pnlWest.add(lblProj); 
					}
					{
						lookupProj = new _JLookup(null, "Project", 0); 
						pnlWest.add(lookupProj); 
						lookupProj.addLookupListener(new LookupListener() {
							
							private String proj_id;
							private String proj_name;

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object data [] = ((_JLookup) event.getSource()).getDataSet();
								
								if(data != null) { 
									proj_id = (String) data[0]; 
									proj_name = (String) data[1]; 
									lookupPhase.setLookupSQL(_JInternalFrame.SQL_PHASE(proj_id));
									txtProject.setText(proj_name);
									manager.focusNextComponent();
								} else {
									return;
								}
							

							}
						});
						
						
					}
					{
						JLabel lblPhase = new JLabel("Phase", JLabel.TRAILING); 
						pnlWest.add(lblPhase); 
					}
					{
						lookupPhase = new _JLookup(null, "Phase", 0); 
						pnlWest.add(lookupPhase); 
						lookupPhase.addLookupListener(new LookupListener() {
							
							private String phase;
							private String phase_desc;

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object data [] = ((_JLookup) event.getSource()).getDataSet();
								
								if(data != null) { 
									phase = (String) data[0]; 
									phase_desc = (String) data[1]; 
									txtPhase.setText(phase_desc);
									manager.focusNextComponent();
								} else {
									return;
								}
							}
						});
					}
					
				}
				{
					JPanel pnlCenterComp = new JPanel(new GridLayout(3, 1, 5, 5)); 
					pnlCenter.add(pnlCenterComp, BorderLayout.CENTER); 
					{
						txtCompany = new JTextField(); 
						pnlCenterComp.add(txtCompany);
					}
					{
						txtProject = new JTextField(); 
						pnlCenterComp.add(txtProject);
					}
					{
						txtPhase = new JTextField(); 
						pnlCenterComp.add(txtPhase);
					}
					
				}
			}
			{
				JPanel pnlSouth = new JPanel(); 
				pnlMain.add(pnlSouth, BorderLayout.SOUTH); 
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview"); 
					pnlSouth.add(btnPreview); 
					btnPreview.addActionListener(new ActionListener() {
			
						@Override
						public void actionPerformed(ActionEvent e) {
							Map<String, Object> mapParameters = new HashMap<String, Object>();
							
							System.out.println("Company ID: " + lookupCompany.getValue());
							
							mapParameters.put("co_id", lookupCompany.getValue());
							mapParameters.put("company", txtCompany.getText());
							mapParameters.put("proj_id", lookupProj.getValue());
							mapParameters.put("phase", lookupPhase.getValue());
							mapParameters.put("prepared_by", UserInfo.FullName);
							
							FncReport.generateReport("/Reports/rptUDOAS_Monitoring.jasper", "Unilateral DOAS Report", mapParameters);
						}
					});
					
					this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProj, lookupPhase, btnPreview));
				}
			}
		}
		
		
		
		
	} //XXX END OF INIT GUI

}

package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class NotFoundAsset extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Not Found Asset";
	public static Dimension framesize= new Dimension(400, 125);
	private JPanel pnlMain;
	private JPanel pnldivision;
	private JPanel pnlNorth;
	private JTextField txtdivision;
	private _JLookup lookupdivision;
	private JPanel pnlEast;
	private JButton btnPreview;
	protected String div_code;
	protected String div_name;
	protected String div_alias;
	private JPanel pnlEastextra1;
	private JPanel pnlEastextra2;
	private JTextField txtEastextra1;
	private JTextField txtEastextra2;
	
	
	public NotFoundAsset(){
		super(title, false, true, true, true);
		initGUI();
		
	}
	public NotFoundAsset(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public NotFoundAsset(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(framesize);
		this.setTitle(title);
		{
			pnlMain = new JPanel(new GridLayout(2, 1,3,3));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(JTBorderFactory.createTitleBorder("Select division"));
			
			{
				pnlNorth = new JPanel(new BorderLayout(3,3));
				pnlMain.add(pnlNorth,BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 10));
				{
					lookupdivision = new _JLookup();
					pnlNorth.add(lookupdivision, BorderLayout.WEST);
					lookupdivision.setLookupSQL(getDivision());
					lookupdivision.setPreferredSize(new Dimension(100, 0));
					lookupdivision.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object [] data= ((_JLookup)event.getSource()).getDataSet();
							
							if(data !=null){
								 div_code = (String)data [0];
								  div_name = (String) data [1];
								  div_alias = (String) data [2];
								
								lookupdivision.setValue(div_code);
								txtdivision.setText(div_name);
								
								System.out.println(div_code);
							}
						}
					});
				}
				{
					txtdivision = new JTextField();
					pnlNorth.add(txtdivision,BorderLayout.CENTER);
				}
			}
			{
				pnlEast = new JPanel(new BorderLayout());
				pnlMain.add(pnlEast);
				{
					txtEastextra1 = new JTextField();
					pnlEast.add(txtEastextra1, BorderLayout.WEST);
					txtEastextra1.setPreferredSize(new Dimension(150, 0));
					txtEastextra1.setBorder(_EMPTY_BORDER);
				}
				{
					btnPreview = new JButton("Preview");
					pnlEast.add(btnPreview);
					//btnPreview.setEnabled(false);
					btnPreview.setPreferredSize(new Dimension(20, 0));
					btnPreview.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//FncReport.generateReport("/Reports/Rpt_Masterlist_Not_found.jasper", "Masterlist Not Found", null);
							if(lookupdivision !=null){
								btnPreview.setEnabled(true);
								previewnotfoundassets(div_code, div_alias);
								lookupdivision.setValue("");
								txtdivision.setText("");
							}
						}
					});
				}
				{
					txtEastextra2= new JTextField();
					pnlEast.add(txtEastextra2, BorderLayout.EAST);
					txtEastextra2.setPreferredSize(new Dimension(150, 0));
					txtEastextra2.setBorder(_EMPTY_BORDER);
				}
			}
		}
	}
	public static String  getDivision(){
		return"select division_code, division_name, division_alias from mf_division where status_id='A'";
	}
	public static void previewnotfoundassets(String div_code, String div_alias){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("div_code", div_code);
		mapParameters.put("div_alias", div_alias);
		
		//FncReport.generateReport("/Reports/Rpt_Masterlist_Not_found.jasper", "Masterlist Not Found", null);
		FncReport.generateReport("/Reports/Rpt_Masterlist_Not_found.jasper", "Masterlist Not Found", mapParameters);
		
	}
		

}

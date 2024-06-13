package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import components._JInternalFrame;

public class RegularBillingAndNotices extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2556119714948825286L;
	static String title = "Regular Billing & Notices";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension frameSize = new Dimension(900, 600);

	private JPanel pnlMain;

	private JTabbedPane tabCenter;

	private pnlRB_RegularBilling pnlRegularBilling;

	private JPanel pnlNotices;
	private JTabbedPane tabNotices;
	private pnlRB_LegalImplication pnlLegalImplication;

	private pnlRB_RegularNotices pnlRegularNotices;

	private pnlRB_Monitoring_v2 pnlMonitoring_v2;

	public RegularBillingAndNotices() {
		super(title, true, true, true, true);
		pnlMonitoring_v2 = new pnlRB_Monitoring_v2();
		initGUI();
	}

	public RegularBillingAndNotices(String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean cancel) {
		super(title, true, true, true, true);
		pnlMonitoring_v2 = new pnlRB_Monitoring_v2(entity_id, proj_id, pbl_id, seq_no,cancel);
		initGUI();
		tabCenter.setSelectedIndex(2);

	}
	
	public RegularBillingAndNotices(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		super(title, true, true, true, true);
		pnlMonitoring_v2 = new pnlRB_Monitoring_v2(entity_id, proj_id, pbl_id, seq_no);
		initGUI();
		tabCenter.setSelectedIndex(2);
	}
	
	public RegularBillingAndNotices(String title) {
		super(title);
		pnlMonitoring_v2 = new pnlRB_Monitoring_v2();
		initGUI();
	}

	public RegularBillingAndNotices(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		pnlMonitoring_v2 = new pnlRB_Monitoring_v2();
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel(new BorderLayout());
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				{
					pnlRegularBilling = new pnlRB_RegularBilling(SQL_CLIENT(), SQL_PROJECT());
					tabCenter.addTab("  Regular Billing  ", null, pnlRegularBilling, null);
				}
				{
					pnlNotices = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("  Notices  ", null, pnlNotices, null);
					{
						tabNotices = new JTabbedPane();
						pnlNotices.add(tabNotices, BorderLayout.CENTER);
						{
							pnlLegalImplication = new pnlRB_LegalImplication(SQL_CLIENT(), sqlProject());
							tabNotices.addTab("  Legal Implication  ", null, pnlLegalImplication, null);
						}
						{
							pnlRegularNotices = new pnlRB_RegularNotices(SQL_PROJECT());
							tabNotices.addTab("  Regular Notices  ", null, pnlRegularNotices, null);
						}
					}
				}
				{
					//pnlMonitoring = new pnlRB_Monitoring();
					//tabCenter.addTab("  Monitoring  ", null, pnlMonitoring, null);
					//pnlMonitoring = new _pnlRB_Monitoring();
					//	tabCenter.addTab("  Monitoring  ", null, pnlMonitoring, null);
					tabCenter.addTab("  Monitoring  ", null, pnlMonitoring_v2, null);
				}
			}
		}
	}// End of GUI
	
	private String sqlProject() {//XXX Project
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\" FROM mf_project where co_id in ('01', '02','04','05') ORDER BY proj_id;";
		return SQL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX Action

	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) { }

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

}

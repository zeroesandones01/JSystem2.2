package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncTables;
import Renderer.DateRenderer;
import components._JTableMain;
import tablemodel.model_itsreal_dues;

public class iutil_payment_template_dues extends JPanel {

	private static final long serialVersionUID = 6169275660626452106L;

	private JScrollPane scrDues;
	private static model_itsreal_dues modelDues;	
	private static _JTableMain tblDues;
	private static JList rowDues;
	
	private static _JXFormattedTextField txtAmountToUpdate;
	private static _JDateChooser dteAssumedPayment; 
	private JCheckBox chkExclude; 
	
	private static Integer intTemplateID;
	private static Integer intGracePeriod;
	private static BigDecimal bdIntRate;
	private static BigDecimal bdPenRate; 
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	
	public static void setTemplateID(Integer template) {
		intTemplateID = template;
	}

	public static void setGracePeriod(Integer grace) {
		intGracePeriod = grace;
	}
	
	public static void setIntRate(BigDecimal intrate) {
		bdIntRate = intrate; 
	}
	
	public static void setPenRate(BigDecimal penrate) {
		bdPenRate = penrate; 
	}
	
	public iutil_payment_template_dues() {
		initGUI(); 
	}

	public iutil_payment_template_dues(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public iutil_payment_template_dues(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public iutil_payment_template_dues(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}

	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JPanel panControls = new JPanel(new GridLayout(1, 5, 5, 5)); 
				panMain.add(panControls, BorderLayout.PAGE_START);
				panControls.setPreferredSize(new Dimension(0, 30));
				{
					{
						JLabel label = new JLabel("Assumed pay date: "); 
						panControls.add(label);
						label.setHorizontalAlignment(JLabel.CENTER);
					}
					{
						dteAssumedPayment = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panControls.add(dteAssumedPayment, BorderLayout.CENTER);
						dteAssumedPayment.getCalendarButton().setVisible(true);
						dteAssumedPayment.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteAssumedPayment.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								try {
									reloadDues();	
								} catch (NullPointerException e) {
									
								}
							}
						});
					}
					{
						JLabel label = new JLabel("Amount to update: "); 
						panControls.add(label);
						label.setHorizontalAlignment(JLabel.CENTER);
					}
					{
						txtAmountToUpdate = new _JXFormattedTextField("0.00");
						panControls.add(txtAmountToUpdate, BorderLayout.CENTER);
						txtAmountToUpdate.setHorizontalAlignment(JTextField.TRAILING);
						txtAmountToUpdate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAmountToUpdate.setEditable(false);
						txtAmountToUpdate.setValue(new BigDecimal("0.00"));
					}
					{
						chkExclude = new JCheckBox("Exclude last date"); 
						panControls.add(chkExclude); 
						chkExclude.setHorizontalAlignment(JCheckBox.CENTER);
						chkExclude.setSelected(false);
					}
				}
			}
			{
				scrDues = new JScrollPane();
				panMain.add(scrDues, BorderLayout.CENTER);
				scrDues.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				{
					modelDues = new model_itsreal_dues(); 
					modelDues.setEditable(false);

					tblDues = new _JTableMain(modelDues);
					rowDues = tblDues.getRowHeader();
					scrDues.setViewportView(tblDues);

					tblDues.getColumnModel().getColumn(0).setPreferredWidth(85);
					tblDues.getColumnModel().getColumn(1).setPreferredWidth(75);
					tblDues.getColumnModel().getColumn(2).setPreferredWidth(65);

					tblDues.getColumnModel().getColumn(3).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(4).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(5).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(6).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(7).setPreferredWidth(65);

					tblDues.getColumnModel().getColumn(8).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(9).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(10).setPreferredWidth(65);
					tblDues.getColumnModel().getColumn(11).setPreferredWidth(80);
					tblDues.getColumnModel().getColumn(12).setPreferredWidth(80);

					tblDues.getColumnModel().getColumn(13).setPreferredWidth(80);
					tblDues.getColumnModel().getColumn(14).setPreferredWidth(80);
					
					tblDues.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));

					rowDues = tblDues.getRowHeader();
					rowDues.setModel(new DefaultListModel());
					scrDues.setRowHeaderView(rowDues);
					scrDues.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}	
		}
	}
	
	@SuppressWarnings("static-access")
	private static void loadDues(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 

		String SQL = ""; 
		Boolean blnVatable = FncGlobal.GetBoolean("select coalesce(vat, 0) > 0 from rf_template_itsreal_payment where template_id = "+intTemplateID+"");
		
		if (blnVatable) {
			SQL = "select c_scheddate, c_procfee, null as rpt, c_res, c_dp, c_mri, c_fire, c_vat, c_soi, c_sop, c_dpp, c_interest, c_principal, c_balance, \n" + 
				"(coalesce(c_procfee, 0)+coalesce(c_res, 0)+coalesce(c_dp, 0)+coalesce(c_mri, 0)+coalesce(c_fire, 0)+coalesce(c_vat, 0)+ \n" + 
				"coalesce(c_soi, 0)+coalesce(c_sop, 0)+coalesce(c_dpp, 0)+coalesce(c_interest, 0)+coalesce(c_principal, 0))::numeric(19, 2) as amount \n" +
				"from fn_template_regular_dues_vat("+intTemplateID+"::int, "+intGracePeriod+"::int, "+bdIntRate+"::numeric, "+bdPenRate+"::numeric, '"+dteAssumedPayment.getDate()+"'::timestamp) \n" +
				"order by c_scheddate";			
		} else {
			SQL = "select c_scheddate, c_procfee, null as rpt, c_res, c_dp, c_mri, c_fire, c_vat, c_soi, c_sop, c_dpp, c_interest, c_principal, c_balance, \n" + 
				"(coalesce(c_procfee, 0)+coalesce(c_res, 0)+coalesce(c_dp, 0)+coalesce(c_mri, 0)+coalesce(c_fire, 0)+coalesce(c_vat, 0)+ \n" + 
				"coalesce(c_soi, 0)+coalesce(c_sop, 0)+coalesce(c_dpp, 0)+coalesce(c_interest, 0)+coalesce(c_principal, 0))::numeric(19, 2) as amount \n" +
				"from fn_template_regular_dues("+intTemplateID+"::int, "+intGracePeriod+"::int, "+bdIntRate+"::numeric, "+bdPenRate+"::numeric, '"+dteAssumedPayment.getDate()+"'::timestamp) \n" +
				"order by c_scheddate";			
		}

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			
		};

		if (blnVatable) {
			txtAmountToUpdate.setValue(FncGlobal.GetDecimal("select sum(amount)::numeric(19, 2) \n" + 
					"from (select (coalesce(c_procfee, 0)+coalesce(c_res, 0)+coalesce(c_dp, 0)+coalesce(c_mri, 0)+coalesce(c_fire, 0)+coalesce(c_vat, 0)+\n" + 
					"coalesce(c_soi, 0)+coalesce(c_sop, 0)+coalesce(c_dpp, 0)+coalesce(c_interest, 0)+coalesce(c_principal, 0))::numeric(19, 2) as amount \n" + 
					"from fn_template_regular_dues_vat("+intTemplateID+"::int, "+intGracePeriod+"::int, "+bdIntRate+"::numeric, "+bdPenRate+"::numeric, '"+dteAssumedPayment.getDate()+"'::timestamp)) a; "));
		} else {
			txtAmountToUpdate.setValue(FncGlobal.GetDecimal("select sum(amount)::numeric(19, 2) \n" + 
					"from (select (coalesce(c_procfee, 0)+coalesce(c_res, 0)+coalesce(c_dp, 0)+coalesce(c_mri, 0)+coalesce(c_fire, 0)+coalesce(c_vat, 0)+\n" + 
					"coalesce(c_soi, 0)+coalesce(c_sop, 0)+coalesce(c_dpp, 0)+coalesce(c_interest, 0)+coalesce(c_principal, 0))::numeric(19, 2) as amount \n" + 
					"from fn_template_regular_dues("+intTemplateID+"::int, "+intGracePeriod+"::int, "+bdIntRate+"::numeric, "+bdPenRate+"::numeric, '"+dteAssumedPayment.getDate()+"'::timestamp)) a; "));	
		}
	}
	
	public static void reloadDues() {
		loadDues(modelDues, rowDues); 
	}
}

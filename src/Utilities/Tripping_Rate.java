package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import interfaces._GUI;

public class Tripping_Rate extends _JInternalFrame implements _GUI, ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Tripping Rate";
	public static Dimension frameSize = new Dimension(450, 280);





	private ButtonGroup grpButton = new ButtonGroup();
	private JXPanel pnlSouth;
	private _JButton btnNew;
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnCancel;
	private JXPanel pnlNorth;
	private JLabel lblCode;
	private JLabel lblEffectivity;
	private _JLookup lookupCode;
	private _JDateChooser dteEffectivity;
	private JCheckBox chkCancelledTrip;
	private JCheckBox chkFixedRate;
	private JLabel lblStandrRate;
	private _JXFormattedTextField txtStarndRate;
	private JLabel lblMinHrs;
	private JLabel lblExHrRate;
	private _JXFormattedTextField txtMinHrs;
	private _JXFormattedTextField txtExHrRate;
	private JLabel lblMinKM;
	private JLabel lblExKmRate;
	private _JXFormattedTextField txtMinKM;
	private _JXFormattedTextField txtExKmRate;
	private DefaultComboBoxModel cmbStatusModel;
	private JComboBox cmbStatus;
	private JLabel lblStatus;
	private String rate_code;

	public Tripping_Rate() {
		super(title, true, true, false, true);
		initGUI();
		FormLoad();
	}

	public Tripping_Rate(String title) {
		super(title);
		initGUI();
		FormLoad();
	}

	public Tripping_Rate(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		FormLoad();
	}

	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(frameSize));
			this.getContentPane().setLayout(new BorderLayout());
			this.setResizable(false);

			{
				JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setLayout(new BorderLayout(5, 5));
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 200));
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder(""));
					{
						JXPanel pnlCode = new JXPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlCode,BorderLayout.NORTH);
						pnlCode.setPreferredSize(new Dimension(this.getWidth(), 50));
						{
							JXPanel pnlCode_West = new JXPanel(new BorderLayout(3, 3));
							pnlCode.add(pnlCode_West,BorderLayout.WEST);
							pnlCode_West.setPreferredSize(new Dimension(250,50 ));
							//pnlWest.setBorder(FncGlobal.lineBorder);
							{
								{
									JXPanel pnlWest = new JXPanel(new GridLayout(2, 1, 3, 3));
									pnlCode_West.add(pnlWest,BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(100,50 ));
									//pnlWest.setBorder(FncGlobal.lineBorder);
									{
										{
											lblCode = new JLabel("Code ");
											pnlWest.add(lblCode);
										}
										{
											lblEffectivity = new JLabel("Effectivity ");
											pnlWest.add(lblEffectivity);
										}
									}
									JXPanel pnlCenter = new JXPanel(new GridLayout(2, 1, 3, 3));
									pnlCode_West.add(pnlCenter,BorderLayout.CENTER);
									//	pnlCenter.setPreferredSize(new Dimension(200,50 ));
									{
										lookupCode = new _JLookup("", "Code", 0) ; /// XXX lookupCode 
										pnlCenter.add(lookupCode);
										lookupCode.setPreferredSize(new Dimension(200, 30));
										lookupCode.setReturnColumn(0);
										lookupCode.setLookupSQL("SELECT rate_code,\n" + 
												"       effectivity, \n" + 
												"       standard_rate, \n" + 
												"       cancelled_rate, \n" + 
												"       fixed_rate, \n" + 
												"       status_id\n" + 
												"FROM mf_tripping_rate");
										
										lookupCode.addLookupListener(new LookupListener() {
											
											

											@Override
											public void lookupPerformed(LookupEvent e) {

												Object[] data = ((_JLookup)e.getSource()).getDataSet();

												if (data != null) {
													rate_code = data[0].toString();
													
													EditProcess(rate_code);
												}
											}
										});
									}
									{

										dteEffectivity = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter.add(dteEffectivity);
										dteEffectivity.setDate(dateFormat(getDateSQL()));
									}
								}
							}
							{
								JXPanel pnlCode_East = new JXPanel(new BorderLayout(3, 3));
								pnlCode.add(pnlCode_East,BorderLayout.EAST);
								pnlCode_East.setPreferredSize(new Dimension(120,50 ));
								{
									JXPanel pnlEast = new JXPanel(new GridLayout(2, 1, 3, 3));
									pnlCode_East.add(pnlEast,BorderLayout.EAST);
									pnlEast.setPreferredSize(new Dimension(120,50 ));
									//pnlWest.setBorder(FncGlobal.lineBorder);
									{
										{
											chkCancelledTrip = new  JCheckBox("Cancelled Trip");
											pnlEast.add(chkCancelledTrip);
										}
										{
											chkFixedRate = new  JCheckBox("Fixed Rate");
											pnlEast.add(chkFixedRate);
										}
									}
								}
							}


						}//pnlCode

						{
							JXPanel pnlCenterRate = new JXPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlCenterRate,BorderLayout.CENTER);
							{
								JXPanel pnlNorthRate = new JXPanel(new BorderLayout(3, 3));
								pnlCenterRate.add(pnlNorthRate,BorderLayout.NORTH);
								pnlNorthRate.setPreferredSize(new Dimension(this.getWidth(), 25));
								{
									JXPanel pnlWest = new JXPanel(new GridLayout(1, 1, 3, 3));
									pnlNorthRate.add(pnlWest,BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(100,25 ));
									//pnlWest.setBorder(FncGlobal.lineBorder);
									{
										{
											lblStandrRate = new JLabel("Standard Rate ");
											pnlWest.add(lblStandrRate);
										}
									}

									JXPanel pnlCenter = new JXPanel(new GridLayout(1, 1, 3, 3));
									pnlNorthRate.add(pnlCenter,BorderLayout.CENTER);
									{
										txtStarndRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCenter.add(txtStarndRate);
										txtStarndRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									//	txtStarndRate.setText("0.00");
									}
									JXPanel pnlCode_East = new JXPanel(new BorderLayout(3, 3));
									pnlNorthRate.add(pnlCode_East,BorderLayout.EAST);
									pnlCode_East.setPreferredSize(new Dimension(167,25 ));

								}

								JXPanel pnlCenter = new JXPanel(new GridLayout(1, 2, 3, 3));
								pnlCenterRate.add(pnlCenter,BorderLayout.CENTER);
								pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 25));
								{
									JXPanel pnlWest = new JXPanel(new BorderLayout(3, 3));
									pnlCenter.add(pnlWest);
									pnlWest.setPreferredSize(new Dimension(100,25 ));
									{
										JXPanel pnlMin = new JXPanel(new GridLayout(2, 1, 3, 3));
										pnlWest.add(pnlMin,BorderLayout.WEST);
										pnlMin.setPreferredSize(new Dimension(100,50 ));
										{
											lblMinHrs = new JLabel("Min Hrs.");
											pnlMin.add(lblMinHrs);

											lblExHrRate = new JLabel("Excess Hr Rate");
											pnlMin.add(lblExHrRate);

										}										
									}
									{

										JXPanel pnlMin = new JXPanel(new GridLayout(2, 1, 3, 3));
										pnlWest.add(pnlMin,BorderLayout.CENTER);
										pnlMin.setPreferredSize(new Dimension(100,50 ));
										{
											{
												txtMinHrs = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
												pnlMin.add(txtMinHrs);
												txtMinHrs.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											//	txtMinHrs.setText("0.00");
											}
											{
												txtExHrRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
												pnlMin.add(txtExHrRate);
												txtExHrRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											//	txtExHrRate.setText("0.00");
											}

										}										
									}

								}
								{
									JXPanel pnlEast = new JXPanel(new BorderLayout(3, 3));
									pnlCenter.add(pnlEast);
									pnlEast.setPreferredSize(new Dimension(100,25 ));

									{
										JXPanel pnlMin = new JXPanel(new GridLayout(2, 1, 3, 3));
										pnlEast.add(pnlMin,BorderLayout.CENTER);
										pnlMin.setPreferredSize(new Dimension(100,50 ));
										{
											lblMinKM = new JLabel("Min KM");
											pnlMin.add(lblMinKM);

											lblExKmRate = new JLabel("Excess KM Rate");
											pnlMin.add(lblExKmRate);

										}										
									}
									{

										JXPanel pnlMin = new JXPanel(new GridLayout(2, 1, 3, 3));
										pnlEast.add(pnlMin,BorderLayout.EAST);
										pnlMin.setPreferredSize(new Dimension(100,50 ));
										{
											{
												txtMinKM = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
												pnlMin.add(txtMinKM);
												txtMinKM.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											//	txtMinKM.setText("0.00");
											}
											{
												txtExKmRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
												pnlMin.add(txtExKmRate);
												txtExKmRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											//	txtExKmRate.setText("0.00");
											}

										}										
									}
								}
							}
						}
						{
							JXPanel pnlStatus = new JXPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlStatus,BorderLayout.SOUTH);
							pnlStatus.setPreferredSize(new Dimension(100,25 ));
							{
								JXPanel pnlWest = new JXPanel(new GridLayout(1, 1, 3, 3));
								pnlStatus.add(pnlWest,BorderLayout.WEST);
								pnlWest.setPreferredSize(new Dimension(100,25 ));
								{
									lblStatus = new JLabel("Status");
									pnlWest.add(lblStatus);
								}
							}
							{
								JXPanel pnlCenter = new JXPanel(new GridLayout(1, 1, 3, 3));
								pnlStatus.add(pnlCenter,BorderLayout.CENTER);
								pnlCenter.setPreferredSize(new Dimension(100,25 ));
								{

									cmbStatusModel = new DefaultComboBoxModel(new String[] {"Active", "Inactive"});
									cmbStatus = new JComboBox();
									pnlCenter.add(cmbStatus);
									cmbStatus.setModel(cmbStatusModel);
								}
							}
							{
								JXPanel pnlEast = new JXPanel(new GridLayout(1, 1, 3, 3));
								pnlStatus.add(pnlEast,BorderLayout.EAST);
								pnlEast.setPreferredSize(new Dimension(167,25 ));
							}
						}
					}
				}
				{

					pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setLayout(new BorderLayout(3, 3));
					pnlSouth.setPreferredSize(new Dimension(this.getWidth(), 35));
					{
						JXPanel pnlButton= new JXPanel();
						pnlSouth.add(pnlButton,BorderLayout.CENTER);
						pnlButton.setLayout(new GridLayout(1, 4, 3, 3));
						{

							{
								btnNew = new _JButton("New");
								pnlButton.add(btnNew);
								btnNew.addActionListener(this);
								btnNew.setActionCommand("New");
								grpButton.add(btnNew);
							}
							{
								btnEdit = new _JButton("Edit");
								pnlButton.add(btnEdit);
								btnEdit.addActionListener(this);
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);
							}
							{
								btnSave = new _JButton("Save");
								pnlButton.add(btnSave);
								btnSave.addActionListener(this);
							}
							{
								btnCancel = new _JButton("Cancel");
								pnlButton.add(btnCancel);
								btnCancel.addActionListener(this);
							}

						}
					}

				}

			}
		} catch (Exception e) {
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("New")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
		}
		
		if (e.getActionCommand().equals("Edit")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			
			if (lookupCode.getValue().equals("")) {
				JOptionPane.showMessageDialog(null, "Please select first the rate code.","Edit",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			this.setComponentsEnabled(pnlNorth, true);
			this.setComponentsEditable(pnlNorth, true);
			lookupCode.setEditable(false);
			btnState(false, false, true, true);
		}
		
		
		if (e.getSource().equals(btnSave)) {
			
			if (checkRequiredFields()) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					SaveProcess();
					CancelProcess();
					FormLoad();
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Successfully saved", "Save", JOptionPane.INFORMATION_MESSAGE);	
				}
			}
		}
		
		if (e.getSource().equals(btnCancel)) {
			CancelProcess();
			FormLoad();
			
		}
		
	}//actionPerformed


	
	private void FormLoad(){
		
		this.setComponentsEnabled(pnlNorth, false);
		lblCode.setEnabled(true);
		lookupCode.setEnabled(true);
		lookupCode.setEditable(true);
		btnState(true, true, false, false);
	}
	
	private void CancelProcess(){
		lookupCode.setValue("");
		dteEffectivity.setDate(dateFormat(getDateSQL()));
		txtStarndRate.setText("0.00");
		txtMinHrs.setText("0.00");
		txtMinKM.setText("0.00");
		txtExHrRate.setText("0.00");
		txtExKmRate.setText("0.00");
		cmbStatus.setSelectedIndex(0);
		
		
		
	}
	
	private void btnState(Boolean sAdd, Boolean sEdit, Boolean sSave, Boolean sCance){
		btnNew.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCance);

	}
	
	
	private void NewProcess(){
		this.setComponentsEnabled(pnlNorth, true);
		this.setComponentsEditable(pnlNorth, true);
		lookupCode.setValue(getCode());
		lookupCode.setEditable(false);
		
		dteEffectivity.setDate(dateFormat(getDateSQL()));
		
		btnState(false, false, true, true);
	}
	
	private void EditProcess(String rate_code){
		
		
		
		pgSelect db = new pgSelect();
		
		
		db.select("SELECT effectivity, \n" + 
				"       standard_rate, \n" + 
				"       min_hrs, \n" + 
				"       min_km, \n" + 
				"       excess_hr_rate, \n" + 
				"       excess_km_rate, \n" + 
				"       cancelled_rate, \n" + 
				"       fixed_rate, \n" + 
				"       status_id\n" + 
				"FROM mf_tripping_rate\n" + 
				"where rate_code = '"+rate_code+"';\n" + 
				"");
		
		BigDecimal standard_rate = (BigDecimal)  db.Result[0][1];
		BigDecimal min_hrs = (BigDecimal)  db.Result[0][2];
		BigDecimal min_km = (BigDecimal)  db.Result[0][3];
		BigDecimal excess_hr = (BigDecimal)  db.Result[0][4];
		BigDecimal excess_km = (BigDecimal)  db.Result[0][5];
		
		Boolean cancelled_rate =  (Boolean) db.Result[0][6];
		Boolean fixed_rate =  (Boolean) db.Result[0][7];
		
		if (db.isNotNull()) {
			
			dteEffectivity.setDate((Date) db.Result[0][0]);
			txtStarndRate.setValue(standard_rate);
			txtMinHrs.setValue(min_hrs);
			txtMinKM.setValue(min_km);
			txtExHrRate.setValue(excess_hr);
			txtExKmRate.setValue(excess_km);
			
			chkCancelledTrip.setSelected(cancelled_rate);
			chkFixedRate.setSelected(fixed_rate);
			
			cmbStatus.setSelectedIndex(db.Result[0][8].toString().equals("A") ? 0 : 1);
			
		}
	}
	
	private void SaveProcess(){
		
		pgUpdate dbU = new pgUpdate();
		
		
		String SQL = "";
		
		String rate_code = lookupCode.getValue().equals("") || lookupCode.getValue() == null ? "" : lookupCode.getValue(); 
		Date dateEffct = dteEffectivity.getDate();
		
		BigDecimal standard_rate = new BigDecimal(txtStarndRate.getText().replace(",", ""));
		BigDecimal min_hrs = new BigDecimal(txtMinHrs.getText().replace(",", ""));
		BigDecimal excesshrate = new BigDecimal(txtExHrRate.getText().replace(",", ""));
		BigDecimal minkm = new BigDecimal(txtMinKM.getText().replace(",", ""));
		BigDecimal excesskmrate = new BigDecimal(txtExKmRate.getText().replace(",", ""));
		
		Boolean cancelledtrip = chkCancelledTrip.isSelected();
		Boolean fixedRate = chkFixedRate.isSelected();
		
		String status_id = cmbStatus.getSelectedIndex() == 0 ? "A" : "I";
		
		if (grpButton.getSelection().getActionCommand().equals("New")) {
			SQL = "INSERT INTO mf_tripping_rate(\n" + 
					"            rate_code, \n" + 
					"            effectivity, \n" + 
					"            standard_rate, \n" + 
					"            min_hrs, \n" + 
					"            min_km, \n" + 
					"            excess_hr_rate, \n" + 
					"            excess_km_rate, \n" + 
					"            cancelled_rate, \n" + 
					"            fixed_rate, \n" + 
					"            status_id)\n" + 
					"    VALUES ('"+rate_code+"',---rate_code, \n" + 
					"            '"+dateEffct+"',---effectivity, \n" + 
					"            '"+standard_rate+"',---standard_rate, \n" + 
					"            '"+min_hrs+"',---min_hrs, \n" + 
					"            '"+minkm+"',---min_km, \n" + 
					"            '"+excesshrate+"',---excess_hr_rate, \n" + 
					"            '"+excesskmrate+"',---excess_km_rate, \n" + 
					"            "+cancelledtrip+",---cancelled_rate, \n" + 
					"            "+fixedRate+",---fixed_rate, \n" + 
					"            '"+status_id+"'---status_id\n" + 
					"            );\n" + 
					"";
		}
		
		if (grpButton.getSelection().getActionCommand().equals("Edit")) {
			
			SQL = "UPDATE mf_tripping_rate\n" + 
					"   SET effectivity = '"+dateEffct+"', \n" + 
					"       standard_rate = '"+standard_rate+"', \n" + 
					"       min_hrs ='"+min_hrs+"', \n" + 
					"       min_km = '"+minkm+"', \n" + 
					"       excess_hr_rate = '"+excesshrate+"', \n" + 
					"       excess_km_rate = '"+excesskmrate+"', \n" + 
					"       cancelled_rate = "+cancelledtrip+", \n" + 
					"       fixed_rate = "+fixedRate+", \n" + 
					"       status_id = '"+status_id+"'\n" + 
					" WHERE rate_code = '"+rate_code+"';\n" + 
					"";
		}
		
		
		dbU.executeUpdate(SQL, true);
		dbU.commit();
	}
	
	private String  getCode(){
		pgSelect db = new pgSelect();
		db.select("SELECT TO_CHAR(MAX(DISTINCT(rate_code)::int)+ 1, 'FM00') FROM mf_tripping_rate");
		
		return db.Result[0][0] == null ? "001" : db.Result[0][0].toString();
		
		
	}
	public static  String getDateSQL(){
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();
	}
	
	
	


	public static Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		} 

		return date;
	}
	
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		if(txtStarndRate.getText().equals("0.00") || txtStarndRate.getText().equals("")){
			required_fields = required_fields + "Standard Rate \n";
			toSave = false;
		}
		if(txtMinHrs.getText().equals("0.00") || txtMinHrs.getText().equals("")){
			required_fields = required_fields + "Min Hrs \n";
			toSave = false;
		}
		if(txtExHrRate.getText().equals("0.00") || txtExHrRate.getText().equals("")){
			required_fields = required_fields + "Excess Hr Rate \n";
			toSave = false;
		}

		if(txtMinKM.getText().equals("0.00") || txtMinKM.getText().equals("")){
			required_fields = required_fields + "Min KM \n";
			toSave = false;
		}
		if(txtExKmRate.getText().equals("0.00") || txtExKmRate.getText().equals("")){
			required_fields = required_fields + "Excess KM Rate \n";
			toSave = false;
		}


		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
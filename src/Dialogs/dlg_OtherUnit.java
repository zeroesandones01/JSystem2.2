package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.math.BigDecimal;
import java.awt.Dialog.ModalityType;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Buyers.ClientServicing._HoldingAndReservation;
import Buyers.ClientServicing.pnlTemporaryReservation;
import Database.pgSelect;
import Functions.FncBigDecimal;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JDialog;
import components._JXTextField;
import interfaces._GUI;

public class dlg_OtherUnit extends _JDialog implements _GUI {

	private static final long serialVersionUID = 3655730446339794369L;
	private Dimension SIZE = new Dimension(300, 80);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	private String title = "Other Unit";
	
	private JPanel pnlMain;
	private JPanel pnlMainLabel;
	private JLabel lblOtherUnit;
	private JPanel pnlMainComponent;
	private _JLookup lookupOtherUnit;
	private _JXTextField txtOtherUnit;
	
	private String other_unit = "";
	private String proj_id = "";
	private pnlTemporaryReservation pnlTR;
	
	public dlg_OtherUnit(String proj_id, String unit_id, pnlTemporaryReservation pnlTR) {
		initGUI();
		this.pnlTR = pnlTR;
		initializeComponents(proj_id, unit_id);
	}

	public dlg_OtherUnit(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_OtherUnit(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_OtherUnit(Window owner, String entity_name, String entity_id, Integer pbl_id, Integer seq_no,
			List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_OtherUnit(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_OtherUnit(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_OtherUnit(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_OtherUnit(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_OtherUnit(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_OtherUnit(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_OtherUnit(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_OtherUnit(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_OtherUnit(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_OtherUnit(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_OtherUnit(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_OtherUnit(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(SIZE);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlMainLabel = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlMainLabel, BorderLayout.WEST);
				{
					lblOtherUnit = new JLabel("Unit");
					pnlMainLabel.add(lblOtherUnit);
				}
			}
			{
				pnlMainComponent = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlMainComponent, BorderLayout.CENTER);
				{
					lookupOtherUnit = new _JLookup(null, "Other Unit");
					pnlMainComponent.add(lookupOtherUnit, BorderLayout.WEST);
					lookupOtherUnit.setPreferredSize(new Dimension(100, 0));
					lookupOtherUnit.addLookupListener(new LookupListener() {
						
						@Override
						public void lookupPerformed(LookupEvent event) {
							Object [] data = ((_JLookup) event.getSource()).getDataSet();
							FncSystem.out("Display SQL for Lookup of Unit", lookupOtherUnit.getLookupSQL());
							
							if(data != null){
								
								String unit_id = (String) data[0];
								
								if(_HoldingAndReservation.isOpen(proj_id, unit_id)){
									String unit_desc = (String) data[1];
									String model_id = (String) data[2];
									String model_desc = (String) data[3];
									
									lookupOtherUnit.setValue(unit_id);
									txtOtherUnit.setText(unit_desc);
									//other_unit = unit_id;
									
									pnlTR.computeCombinedUnit(unit_id, model_id, model_desc);
									
									exit();
								}else{
									lookupOtherUnit.setValue(null);
									JOptionPane.showMessageDialog(null, "Unit has been reserved already", "Unit", JOptionPane.WARNING_MESSAGE);
								}
							}
						}
					});
				}
				{
					txtOtherUnit = new _JXTextField();
					pnlMainComponent.add(txtOtherUnit, BorderLayout.CENTER);
				}
			}
		}
		this.pack();
	}//XXX END OF INIT GUI
	
	private void initializeComponents(String proj_id, String unit_id){
		this.proj_id = proj_id;
		
		lookupOtherUnit.setValue(null);
		txtOtherUnit.setText("");
		lookupOtherUnit.setLookupSQL("SELECT * FROM view_holding_and_reservation_open_units('"+proj_id+"') WHERE \"ID\" != '"+unit_id+"'");
	}
	
	private Object[] computeCombinedUnits(String proj_id, String unit_id1, String unit_id2){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_combined_pricelist('"+proj_id+"', '"+unit_id1+"', '"+unit_id2+"')";
		db.select(SQL);
		
		BigDecimal nsp = FncBigDecimal.zeroValue();
		
		if(db.isNotNull()){
			 nsp = (BigDecimal) db.getResult()[0][16];
		}
		
		return new Object[]{nsp};
	}
	
	public String getOtherUnit(){
		String other_unit = this.other_unit;
		return other_unit;
	}
	
	private void exit(){
		this.dispose();
	}
	
}

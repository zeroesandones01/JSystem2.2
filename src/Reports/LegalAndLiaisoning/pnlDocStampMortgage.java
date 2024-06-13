package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelDocStampMortgage;
import tablemodel.modelTransactionSummary;

public class pnlDocStampMortgage extends JDialog implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JDateChooser dteDate;
	private JTextField txtTagDate;
	private JScrollPane scrollMortgage;
	private modelDocStampMortgage modelMortgage;
	private _JTableMain tblMortgage;
	private java.util.Date dteTag;
	private JButton btnSave;
	private JButton btnRefresh;
	private String co_id;
	private String proj_id;

	public pnlDocStampMortgage(Frame owner, String title, java.util.Date dteTag, String co_id, String proj_id) {
		// TODO Auto-generated constructor stub
		super(owner, title);
		this.dteTag = dteTag;
		this.co_id = co_id;
		this.proj_id = proj_id;
		initGUI();
	}
	
	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(5,5));
		this.setSize(new Dimension(490,350));
		this.setModal(true);
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,30));
				{
					JPanel pnlLabel = new JPanel(new GridLayout(1,1,5,5));
					pnlNorth.add(pnlLabel, BorderLayout.WEST);
					{
						JLabel lblTagDate = new JLabel("Tag Date:");
						pnlLabel.add(lblTagDate);
					}
				}
				{
					JPanel pnlDate = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlDate, BorderLayout.CENTER);
					{
						txtTagDate = new JTextField("");
						pnlDate.add(txtTagDate, BorderLayout.WEST);
						txtTagDate.setEnabled(false);
						txtTagDate.setPreferredSize(new Dimension(100,0));
						txtTagDate.setHorizontalAlignment(JTextField.CENTER);
					}
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollMortgage = new JScrollPane();
					pnlCenter.add(scrollMortgage);
					scrollMortgage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				}
				{
					modelMortgage = new modelDocStampMortgage();
					tblMortgage = new _JTableMain(modelMortgage);

					scrollMortgage.setViewportView(tblMortgage);
					tblMortgage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1,2,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouth.add(btnRefresh);
				}
			}
		}
		
		initiliaze_comp();
	}

	private void initiliaze_comp() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = dateFormat.format(dteTag);  
		txtTagDate.setText(strDate);
		
		displayForBatchingDS(modelMortgage, dteTag);
		
	}
	
	private void displayForBatchingDS(DefaultTableModel modelMain, java.util.Date dteTag) {
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		
		String sql =
				"select \n" + 
				"false,\n" + 
				"a.entity_id,\n" + 
				"b.entity_name,\n" + 
				"a.pbl_id,\n" + 
				"format('%s-%s-%s', c.phase, c.block, c.lot) as unit,\n" + 
				"a.seq_no,\n" + 
				"a.date_created::date\n" + 
				"from tmp_transfer_dst_mortgage a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id\n" + 
				"where a.date_created::date = '"+dteTag+"'\n" +
				"and a.status_id = 'A'";
		
		FncSystem.out("SQL description", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}
		
		tblMortgage.packAll();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("Save")) {	save();}
		
	}

	private void save() {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(getContentPane(), "This will generate batch number to all selected clients.\n" + "Are you sure you want to continue?", "Save",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			
			for(int x = 0; x < tblMortgage.getRowCount(); x++){
				Boolean isSelected 	= (Boolean) modelMortgage.getValueAt(x, 0);
				String entity_id 	= (String) modelMortgage.getValueAt(x, 1);
				String pbl_id 		= (String) modelMortgage.getValueAt(x, 3);
				Integer seq_no 		= (Integer) modelMortgage.getValueAt(x, 5);
				String emp_code		= UserInfo.EmployeeCode;
				
				if(isSelected) {
					String sql = "select sp_save_transfer_cost_frmTempToMain ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+emp_code+"')";
					
					FncSystem.out("SQL for tranferring temp to main", sql);
					pgSelect db = new pgSelect();
					db.select(sql);
				}
			}
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully generated!");
			
			displayForBatchingDS(modelMortgage, dteTag);
		}
	}
}

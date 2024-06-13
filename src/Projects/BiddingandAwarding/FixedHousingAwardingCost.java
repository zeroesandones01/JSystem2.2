package Projects.BiddingandAwarding;
import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTitledSeparator;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelFixedHousingAwardingCost;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncTables;
import Functions.UserInfo;
import components._JInternalFrame;
import components._JTableMain;

public class FixedHousingAwardingCost extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6500003584501090022L;
	
	static String title = "Fixed Housing Awarding Cost";
	Dimension frameSize = new Dimension(400, 400);//510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	
	private JPanel pnlNorth;
	private JLabel lblModel;
	private JComboBox cmbModel;
	private JLabel lblCurrentCost;
	private _JXFormattedTextField txtCurrentCost;
	
	private JXTitledSeparator separator; 
	private JLabel lblNewCost;
	private _JXFormattedTextField txtNewCost;

	private JScrollPane scrollCenter;
	private _JTableMain tblHistory;
	private modelFixedHousingAwardingCost modelHistory;
	private JList rowHistory;
	
	private JPanel pnlSouth;
	private JButton btnSave;
	private JButton btnDelete;

	private String model_desc;
	
	public FixedHousingAwardingCost() {
		super(title, true, true, false, true);
		initGUI();
	}

	public FixedHousingAwardingCost(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public FixedHousingAwardingCost(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("House Details"));
				pnlNorth.setPreferredSize(new Dimension(388, 118));
				{
					lblModel = new JLabel("Model");
					pnlNorth.add(lblModel);
					lblModel.setBounds(10, 28, 92, 22);
				}
				{
					cmbModel = new JComboBox();
					pnlNorth.add(cmbModel);
					cmbModel.setActionCommand("Model");
					cmbModel.setBounds(102, 28, 175, 22);
					cmbModel.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							String model = (String) ((JComboBox) arg0.getSource()).getSelectedItem();
							displayHistory(model);
						}
					});
					cmbModel.addActionListener(this);
				}
				{
					lblNewCost = new JLabel("New Cost");
					pnlNorth.add(lblNewCost);
					lblNewCost.setBounds(10, 60, 92, 22);
				}
				{
					txtNewCost = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlNorth.add(txtNewCost);
					txtNewCost.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtNewCost.setActionCommand("Amount");
					txtNewCost.setBounds(102, 60, 120, 22);
					txtNewCost.addActionListener(this);
				}
			}
			{
				scrollCenter = new JScrollPane();
				pnlMain.add(scrollCenter, BorderLayout.CENTER);
				scrollCenter.setBorder(components.JTBorderFactory.createTitleBorder("History"));
				{
					modelHistory = new modelFixedHousingAwardingCost();
					
					tblHistory = new _JTableMain(modelHistory);
					scrollCenter.setViewportView(tblHistory);
					tblHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
					tblHistory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {
							try {
								if(!arg0.getValueIsAdjusting()){
									int row = tblHistory.convertRowIndexToModel(tblHistory.getSelectedRow());
									
									BigDecimal house_cost = (BigDecimal) modelHistory.getValueAt(row, 0);
									//String model_desc = (String) modelHistory.getValueAt(row, 1);
									
									btnDelete.setEnabled(true);
									btnSave.setEnabled(false);
									//txtNewCost.setEnabled(false);
									
									txtNewCost.setValue(house_cost);
									//cmbModel.setSelectedItem(model_desc);
									
									}
								
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					});
					tblHistory.setSortable(false);
					tblHistory.hideColumns("Time Updated");
				}
				{
					rowHistory = tblHistory.getRowHeader();
					rowHistory.setModel(new DefaultListModel());
					scrollCenter.setRowHeaderView(rowHistory);
					scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 30));
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouth.add(btnDelete);
					//btnDelete.setAlignmentX(50);
					//btnDelete.setAlignmentY(0.5f);
					btnDelete.setMaximumSize(new Dimension(100, 30));
					btnDelete.setMnemonic(KeyEvent.VK_D);
					btnDelete.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					//btnSave.setAlignmentX(35);
					//btnSave.setAlignmentY(0.5f);
					btnSave.setMaximumSize(new Dimension(100, 30));
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
			}
		}

		listModel();
		
		displayHistory((String) cmbModel.getSelectedItem());
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbModel, txtNewCost, btnSave, btnDelete));
		btnDelete.setEnabled(false);
	}
	
	private void listModel() {
		pgSelect db = new pgSelect();
		db.select("select distinct trim(model_code) || ' ' || trim(firewall_type) from mf_product_model order by trim(model_code) || ' ' || trim(firewall_type);");
		
		if(db.isNotNull()){
			ArrayList<Object> listModel = new ArrayList<Object>();
			for(int x=0; x < db.getRowCount(); x++) {
				listModel.add(db.getResult()[x][0]);
			}
			cmbModel.setModel(new DefaultComboBoxModel(listModel.toArray()));
		}
	}
	
	//private BigDecimal getCurrentCost(String model) {
		//String sql = "select a.house_cost\n" +
				//"from (select unnest(model_id) as _id, * from co_house_cost) a\n" +
				//"left join mf_product_model b on b.model_id = a._id\n" +
				//"left join em_employee c on c.emp_code = a.updated_by\n" +
				//"left join rf_entity d on d.entity_id = c.entity_id\n" +
				//"where trim(b.model_code) || ' ' || trim(b.firewall_type) = '"+ model +"'\n" +
				//"group by a.house_cost, a.date_updated\n" +
				//"order by a.date_updated desc limit 1;";
		//pgSelect db = new pgSelect();
		//db.select(sql);
		
		//if(db.isNotNull()){
			//return (BigDecimal) db.getResult()[0][0];
		//}else{
			//return null;
		//}
		
	//}
	
	private void displayHistory(String model) {
		//txtCurrentCost.setValue(getCurrentCost(model));
		
		modelHistory.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHistory.setModel(listModel);
		
		String sql = "select a.house_cost, a.model_desc, trim(d.entity_name), a.date_created, a.date_created\n" +
				"from (select unnest(model_id) as _id, * from co_house_cost_new) a\n" +
				"left join mf_product_model b on b.model_id = a._id\n" +
				"left join em_employee c on c.emp_code = a.created_by\n" +
				"left join rf_entity d on d.entity_id = c.entity_id\n" +
				"where trim(b.model_code) || ' ' || trim(b.firewall_type) = '"+ model +"'\n" +
				"and a.status_id = 'A'" +
				"group by a.house_cost, a.model_desc, trim(d.entity_name), a.date_created\n" +
				"order by a.date_created desc;";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelHistory.addRow(db.getResult()[x]);
				listModel.addElement(modelHistory.getRowCount());
			}
			scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblHistory.getRowCount())));
			tblHistory.packAll();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Model")){
			txtNewCost.setValue(null);
			btnDelete.setEnabled(false);
			btnSave.setEnabled(true);
			txtNewCost.setEnabled(true);
		}
		if(actionCommand.equals("Amount")){
			//KEYBOARD_MANAGER.focusNextComponent();
		}
		if(actionCommand.equals("Save")){
			if(txtNewCost.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input New Cost.", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else {
			String sql = "INSERT INTO co_house_cost_new(\n" +
					"rec_id, model_id, model_desc, house_cost, status_id, created_by, date_created)\n" +
					"VALUES ("
					+ "(select coalesce(max(rec_id), 0) + 1 from co_house_cost_new), "
					+ "(select array_agg(model_id)::varchar[] from mf_product_model where trim(model_code) || ' ' || trim(firewall_type) = '"+ cmbModel.getSelectedItem() +"'), "
					+ "'"+ cmbModel.getSelectedItem() +"',"
					+ "'"+ txtNewCost.getValued() +"', "
					+ "'A',"
					+ "'"+ UserInfo.EmployeeCode +"', "
					+ "now());";
			
			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			txtNewCost.setValue(null);
			displayHistory((String) cmbModel.getSelectedItem());
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Cost has been saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(actionCommand.equals("Delete")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to delete selected cost?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				BigDecimal house_cost = (BigDecimal) txtNewCost.getValue();
				
				String sql = "UPDATE co_house_cost_new SET status_id = 'I', created_by = '"+ UserInfo.EmployeeCode +"', date_created = now() WHERE house_cost = '" + house_cost + "';";
				
				pgUpdate db = new pgUpdate();
				db.executeUpdate(sql, false);
				db.commit();
				
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cost has been deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
				
				txtNewCost.setValue(null);
				txtNewCost.setEnabled(true);
				btnDelete.setEnabled(false);
				btnSave.setEnabled(true);
				
				//modelHistory.clear();
				displayHistory((String) cmbModel.getSelectedItem());
				
				//displayHistory(model_desc);
				
			}
		}
	}

}

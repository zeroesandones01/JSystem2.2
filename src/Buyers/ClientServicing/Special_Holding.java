package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.jdesktop.swingx.JXTextField;

import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._ButtonGroup;
import components._JInternalFrame;

public class Special_Holding extends _JInternalFrame implements ActionListener, _GUI, AncestorListener, InternalFrameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239579436521587382L;

	static String title = "Special Holding";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	private _JLookup lookupClient;
	private JXTextField txtClient;
	private JTabbedPane tabCenter;
	private pnlSpecialHolding pnlHolding;
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnView;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();	
	private String entity_id = null;

	public Special_Holding() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Special_Holding(String title) {
		super(title);
		initGUI();
	}

	public Special_Holding(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		
		this.addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				System.out.println("Dumaan dito sa CLosing");
				if(tabCenter.getSelectedIndex() == 1){
					//pnlHolding.cancelHolding();
					System.out.println("Tab Selected is 1");
				}
				if(tabCenter.getSelectedIndex() == 2){
					//pnlTR.cancelTR();
					System.out.println("Tab Selected is 2");
				}
				
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		//disabled due to unnecessary icon on toolbar
		/*Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/buyers/holdingandreservation.png")).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		this.setFrameIcon(new ImageIcon(iconInternalFrame));*/
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Client"));
				pnlNorth.setPreferredSize(new Dimension(788, 55));// XXX
				{
					JPanel pnlNorthNorth = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthNorth, BorderLayout.CENTER);
					{
						lookupClient = new _JLookup(null, "Client", 0);
						pnlNorthNorth.add(lookupClient, BorderLayout.WEST);
						lookupClient.setFilterName(true);
						lookupClient.setPrompt("Client ID");
						lookupClient.setLookupSQL(getAccountWidFloatingPmt());
						lookupClient.setPreferredSize(new Dimension(150, 0));
						lookupClient.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								FncSystem.out("Display SQL for Client", lookupClient.getLookupSQL());
								
								if (data != null) {
									String rcpt_no = (String) data[2];
									entity_id = (String) data[0];
									txtClient.setText((String) data[1]);
									pnlSpecialHolding.setPayment(rcpt_no);
									pnlSpecialHolding.entity_id = entity_id;
									pnlSpecialHolding.receipt_no = rcpt_no;
									btnState(true, false, false, false, true);
									btnNew.setEnabled(true);
								}
							}
						});
					}
					{
						txtClient = new JXTextField();
						pnlNorthNorth.add(txtClient, BorderLayout.CENTER);
						txtClient.setPrompt("Client Name");
						txtClient.setEditable(false);
					}
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);				
				{
					pnlHolding = new pnlSpecialHolding(this);
					tabCenter.addTab("Hold Unit", null, pnlHolding, null);
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				// pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(500, 30));
				
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 4, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(520, 0));
				
					{
						btnEdit = new JButton("Edit");
						pnlNavigation.add(btnEdit);
						btnEdit.setVisible(false);
						btnEdit.setActionCommand("Edit");
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.addActionListener(this);

						btnEdit.addPropertyChangeListener("enabled", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {
								btnPreview.setEnabled((Boolean) arg0.getNewValue());
							}
						});
					}
					{
						btnView = new JButton("View");
						pnlNavigation.add(btnView);
						btnView.setActionCommand("View");
						btnView.setMnemonic(KeyEvent.VK_V);
						btnView.setVisible(false);
						btnView.addActionListener(this);
					}
					{
						btnNew = new JButton("Assign");
						pnlNavigation.add(btnNew);
						btnNew.setActionCommand("Assign");
						btnNew.setMnemonic(KeyEvent.VK_A);
						btnNew.addActionListener(this);
						btnNew.setEnabled(false);
					}
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(this);
					}
				}
				{
					btnPreview = new JButton("Preview");
					//pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
				}
				{
					btnDelete = new JButton("Delete");
					btnDelete.setActionCommand("Delete");
					btnDelete.setMnemonic(KeyEvent.VK_D);
					btnDelete.addActionListener(this);
					btnDelete.setVisible(false);
				}
			}
		}
		{
			JLabel lblStatusBar = new JLabel("* - Required Fields");
			getContentPane().add(lblStatusBar, BorderLayout.SOUTH);
			lblStatusBar.setFont(lblStatusBar.getFont().deriveFont(10.0f));
			lblStatusBar.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		}
		
		btnState(false, false, true, false, false);
	}

	public void btnState(Boolean sNew, Boolean sEdit, Boolean sView, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnView.setEnabled(sView);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	public void setClientEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				if (comp.getName().equals("scrollPhase")) {
					panelLooper((JScrollPane) comp, enable);
				}
			} else {
				if (comp.getName() != null) {
					
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupClient.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		int selectedTab = tabCenter.getSelectedIndex();

		//XXX NEW
		if(action.equals("Assign")) {

			if(entity_id.equals("")){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "Assign", JOptionPane.WARNING_MESSAGE);
				return;
			}

			else {
				lookupClient.setEditable(false);
				pnlHolding.setEntityID(lookupClient.getValue());
				pnlHolding.newHolding(false);
				btnState(false, false, false, true, true);
			}
		}

		if(action.equals("Edit")) {
			grpNewEdit.setSelectedButton(arg0);
			lookupClient.setEditable(true);
			btnState(false, false, false, true, true);
		}

		if(action.equals("View")) {
			if(lookupClient.getValue() == null && selectedTab > 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "New", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		//XXX SAVE
		if(action.equals("Save")) {

			if(pnlHolding.saveHolding(lookupClient.getText())){				
				lookupClient.setEditable(true);
				JOptionPane.showMessageDialog(null,"Special Holding saved.","Information",JOptionPane.INFORMATION_MESSAGE);
				btnState(false, false, false, false, true);
			}
		}

		//XXX CANCEL
		if(action.equals("Cancel")) {
			lookupClient.setValue("");
			txtClient.setText("");
			pnlSpecialHolding.clearFields();
			pnlSpecialHolding.clearFields2();
			entity_id = "";
			lookupClient.setEditable(true);
			btnState(false, false, true, false, false);
			
		}
	}

	public static String getAccountWidFloatingPmt(){
		return 
				"SELECT * from ( " +
				"SELECT \r\n" + 
				"a.entity_id as \"Client ID\", " +
				"get_client_name(a.entity_id) as \"Client Name\", \r\n" + 
				"a.or_no as \"Receipt No.\",\r\n" + 				
				"a.trans_date as \"Pmt. Date\",\r\n" + 
				"a.amount as \"Amount\" \r\n," +
				"'From OP' as \"Source\"" + 
				"	\r\n" + 
				"FROM rf_payments a\r\n" + 
				"\r\n" + 
				"WHERE a.co_id = '02'\r\n" + 
				"AND a.status_id != 'I'\r\n" + 
				"AND a.pay_part_id = '203' --FLOATING PAYMENTS\r\n" +
				"AND coalesce(a.pbl_id,'') = '' \r\n" + 
				"AND (a.or_no, (case when or_doc_id is not null then or_doc_id else pr_doc_id end)) \r\n" + 
				"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)\r\n" + 
				"\r\n" + 
				"union all \r\n" + 
				"\r\n" + 
				"SELECT \r\n" + 
				"a.entity_id ,  \r\n" + 
				"get_client_name(a.entity_id) , \r\n" + 
				"a.receipt_no ,		\r\n" + 
				"a.trans_date,\r\n" + 
				"a.total_amt_paid, 	\r\n" +
				"'From Holding' as \"Source\" \r\n" + 
				"FROM (select * from rf_tra_header where pbl_id is null) a\r\n" + 
				"left join rf_tra_detail b on a.client_seqno = b.client_seqno\r\n" + 
				"WHERE a.co_id IN ('02', '01') \r\n" + 
				"AND a.status_id != 'I' \r\n" + 
				"AND (a.receipt_no, '03')  \r\n" + 
				"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)  \r\n" + 
				") b ORDER BY  get_client_name(\"Client ID\")  \r\n" + 
				""  ;

	}
}

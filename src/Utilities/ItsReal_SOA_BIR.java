package Utilities;

	import java.awt.BorderLayout;
	import java.awt.Dimension;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;


	import javax.swing.DefaultListModel;
	import javax.swing.JButton;
	import javax.swing.JLabel;
	import javax.swing.JList;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTabbedPane;
	import javax.swing.JTextField;
	import javax.swing.border.EmptyBorder;
	import javax.swing.event.ChangeEvent;
	import javax.swing.event.ChangeListener;

	import javax.swing.table.DefaultTableModel;


	import Buyers.ClientServicing._CARD;
	import Database.pgSelect;
	import Functions.FncSystem;
	import Functions.FncTables;
	import Lookup.LookupEvent;
	import Lookup.LookupListener;
	import Lookup._JLookup;
	import components._JInternalFrame;
	import components._JTableMain;
	import interfaces._GUI;
	import tablemodel.modelTable_Ledger;


	public class ItsReal_SOA_BIR extends _JInternalFrame implements _GUI,ActionListener  {


		static String title = "ItsReal SOA BIR";
		Dimension frameSize = new Dimension(750, 500);

		private JPanel pnlMain; 
		private _JLookup lookupClient;
		private JButton btnCancel,btnSave;
		static _JTableMain tblLedger; 
		private JLabel lblClient,lblProj,lblUnit,lblClient2,lblProj2,lblUnit2;
		private JList rowLedger;
		private static  JTabbedPane tabDesc;
		private modelTable_Ledger modelTblLedger;
		private JScrollPane scrollLedger;
		private JTextField txtProj,txtUnit;
		private String pbl_id;
		private Integer seq_no;



		public ItsReal_SOA_BIR(){
			super(title, true, true, true,true);
			initGUI();
		}
		public ItsReal_SOA_BIR(String title){
			super(title);
			initGUI();

		}
		public ItsReal_SOA_BIR(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconiflable){
			super(title,resizable,closable,maximizable,iconiflable);
			initGUI();

		}

		public void initGUI() {
			this.setLayout(new BorderLayout(5,5));		
			this.setPreferredSize(frameSize);
			this.setSize(frameSize);
			{
				pnlMain = new JPanel(new BorderLayout(5,5));
				this.add(pnlMain);
				pnlMain.setBorder(new EmptyBorder(5,5,5,5));
				{
					JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
					pnlMainNorth.setBorder(new EmptyBorder(5,5,5,5));
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
						pnlMainNorth.add(pnlNorth,BorderLayout.NORTH);
						pnlNorth.setPreferredSize(new Dimension(0,80));
						{
							JPanel pnlNorthWest = new JPanel(new GridLayout(3,1,3,3));
							pnlNorth.add(pnlNorthWest,BorderLayout.WEST);
							{
								lblClient = new JLabel("Client:");
								pnlNorthWest.add(lblClient);
								lblProj = new JLabel("Project:");
								pnlNorthWest.add(lblProj);
								lblUnit = new JLabel("Unit/Seq:");
								pnlNorthWest.add(lblUnit);
							}
						}
						{
							JPanel pnlNorthCenter = new JPanel(new GridLayout(3,1,3,3));
							pnlNorth.add(pnlNorthCenter,BorderLayout.CENTER);
							{
								JPanel pnlCleint = new JPanel(new BorderLayout(5,5));
								pnlNorthCenter.add(pnlCleint);
								{
									JPanel pnlClientWest = new JPanel(new BorderLayout(5,5));
									pnlCleint.add(pnlClientWest,BorderLayout.WEST);
									pnlClientWest.setPreferredSize(new Dimension(100,0));
									{
										lookupClient = new _JLookup(null,"Client");
										pnlClientWest.add(lookupClient);
										lookupClient.setEnabled(true);
										lookupClient.setLookupSQL(_CARD.sqlClients());
										lookupClient.setReturnColumn(0);
										lookupClient.setFilterIndex(2);
										lookupClient.addLookupListener(new LookupListener() {

											@Override
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();


												if(data != null){
													lookupClient.setValue((String) data[1]);
													lblClient2.setText(String.format("[%s]", (String) data[2]));
													txtProj.setText((String) data[7]);
													lblProj2.setText(String.format("[%s]", (String) data[8]));
													txtUnit.setText((String) data[4]);
													lblUnit2.setText(String.format("[%s] [%s]", (String) data[3], Integer.toString((Integer) data[5])));
																							
													pbl_id = (String) data[4];
													seq_no = (Integer) data[5];
													btnCancel.setEnabled(true);	
													displayValueLedger(modelTblLedger, lookupClient.getValue(), txtProj.getText(), pbl_id, seq_no);
												

												}
											}

										});

									}
								}
								{
									JPanel pnlClientCenter = new JPanel(new BorderLayout(5,5));
									pnlCleint.add(pnlClientCenter,BorderLayout.CENTER);
									{
										lblClient2 = new JLabel("[]");
										pnlClientCenter.add(lblClient2);
									}
								}
							}
							{
								JPanel pnlProj = new JPanel(new BorderLayout(5,5));
								pnlNorthCenter.add(pnlProj);
								{
									JPanel pnlProjWest = new JPanel(new BorderLayout(5,5));
									pnlProj.add(pnlProjWest,BorderLayout.WEST);
									pnlProjWest.setPreferredSize(new Dimension(50,0));
									{
										txtProj = new JTextField();
										pnlProjWest.add(txtProj);
										txtProj.setHorizontalAlignment(JTextField.CENTER);
										txtProj.setEnabled(false);
									}
								}
								{
									JPanel pnlProjCenter = new JPanel(new BorderLayout(5,5));
									pnlProj.add(pnlProjCenter,BorderLayout.CENTER);
									{
										lblProj2 = new JLabel("[]");
										pnlProjCenter.add(lblProj2);
									}

								}
							}
							{

								JPanel pnlUnit = new JPanel(new BorderLayout(5,5));
								pnlNorthCenter.add(pnlUnit);
								{
									JPanel pnlUnitWest = new JPanel(new BorderLayout(5,5));
									pnlUnit.add(pnlUnitWest,BorderLayout.WEST);
									pnlUnitWest.setPreferredSize(new Dimension(50,0));
									{
										txtUnit = new JTextField();
										pnlUnitWest.add(txtUnit);
										txtUnit.setHorizontalAlignment(JTextField.CENTER);
										txtUnit.setEnabled(false);
									}
								}
								{
									JPanel pnlUnitCenter = new JPanel(new BorderLayout(5,5));
									pnlUnit.add(pnlUnitCenter,BorderLayout.CENTER);
									{
										lblUnit2 = new JLabel("[]");
										pnlUnitCenter.add(lblUnit2);
									}

								}

							}
						}
					}

				}
				{
					JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
					{
						tabDesc = new JTabbedPane();
						pnlMainCenter.add(tabDesc);


						{
							scrollLedger = new JScrollPane();
							tabDesc.addTab("Ledger", scrollLedger);
							scrollLedger.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
							scrollLedger.setBorder(new EmptyBorder(5,5,5,5));

						}
						{
							modelTblLedger = new modelTable_Ledger();
							tblLedger = new _JTableMain(modelTblLedger);
							scrollLedger.setViewportView(tblLedger);
							tblLedger.setSortable(false);
							tblLedger.setEnabled(false);

						}
						{
							rowLedger = tblLedger.getRowHeader();
							rowLedger.setModel(new DefaultListModel());
							scrollLedger.setRowHeaderView(rowLedger );
							scrollLedger.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						
						tabDesc.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent arg0) {
								if(tabDesc.getSelectedIndex()==2) {
										btnSave.setEnabled(true);	
								}
								else {
									btnSave.setEnabled(false);
								}
							}
						});
					}
				}
				{
					JPanel pnlMainSouth = new JPanel(new GridLayout(1,8,3,3));
					pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
					pnlMainSouth.setPreferredSize(new Dimension(0,30));

					{
						btnSave = new JButton("Save");
						pnlMainSouth.add(btnSave);
						btnSave.setEnabled(false);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
							}

						});
					} 

					{
						btnCancel = new JButton("Cancel");
						pnlMainSouth.add(btnCancel);
						btnCancel.setEnabled(false);

						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								lookupClient.setValue(null);
								lblClient2.setText("[]");
								txtProj.setText("");
								lblProj2.setText("[]");
								txtUnit.setText("");
								lblUnit2.setText("[]");
								rowLedger.setModel(new DefaultListModel());
								modelTblLedger.clear();
								btnCancel.setEnabled(false);
								btnSave.setEnabled(false);
							}

						});
					}
				}
			}
		}

		private void displayValueLedger(DefaultTableModel model,String entity_id,String proj_id,String pbl_id,Integer seq_no) {

			FncTables.clearTable(model);	
			DefaultListModel listModel = new DefaultListModel();
			rowLedger.setModel(listModel);

				String sql = "SELECT * \n" +
						"FROM view_itsRealSoaBir_ledger('"+ entity_id+"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +")"; 

				FncSystem.out("Display description", sql);
				pgSelect db = new pgSelect();
				db.select(sql);

				if(db.isNotNull()){ 
					for(int x=0; x < db.getRowCount(); x++){
						model.addRow(db.getResult()[x]);
						listModel.addElement(model.getRowCount());

					}
				}
				tblLedger.packAll();
		}

	}


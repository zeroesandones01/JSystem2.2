package Reports.BiddingandAwarding;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Projects.BiddingandAwarding._NoticeToProceed;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.model_credit_balance;

public class ContractorCreditBalance extends _JInternalFrame {

	private static final long serialVersionUID = -1097839125563682020L;
	private static Dimension frameSize = new Dimension(750, 600);
	private static String title = "Contractors Credit Balance";

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;
	private _JLookup lookupContractor;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPhase;
	private JTextField txtContractor;

	private _JDateChooser dteDateFrom;
	private JCheckBox chkAll;
	private JCheckBox chkOngoing; 
	private String strLogo; 

	private model_credit_balance modelCredit; 	
	public static _JTableMain tblCredit;
	public static JList rowCredit;
	
	private JButton btnSave; 

	public ContractorCreditBalance() {
		super(title, false, true, true, true);
		initGUI();
	}

	public ContractorCreditBalance(String title) {
		super(title);
		initGUI();
	}

	public ContractorCreditBalance(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setSize(frameSize);
		this.setLayout(new BorderLayout(5, 5));
		this.setMinimumSize(frameSize);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				super.componentResized(e);
			}
		});
		this.setMinimumSize(new Dimension(750, 600));

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panCenter = new JXPanel(new GridBagLayout());
				panMain.add(panCenter, BorderLayout.CENTER);
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.ipady = 10;
					c.insets = new Insets(5, 5, 5, 5);

					/*        Company             */
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 0;

						JLabel label = new JLabel("Company"); 
						label.setHorizontalAlignment(JLabel.LEADING);
						label.setFont(FncGlobal.sizeLabel);
						panCenter.add(label, c);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 0;

						lookupCompany = new _JLookup(null, "Company");
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(lookupMethods.getCompany());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									lookupCompany.setValue(data[0].toString());
									txtCompany.setText(data[1].toString());
									strLogo = (String) data[3];
									lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
									reload(); 
								}
							}
						});
						lookupCompany.addKeyListener(new KeyListener() {

							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookupCompany.setValue("");
									txtCompany.setText("All");
									reload(); 
								}
							}

							public void keyReleased(KeyEvent e) {

							}

							public void keyPressed(KeyEvent e) {

							}
						});

						lookupCompany.setFont(FncGlobal.sizeTextValue);     
						panCenter.add(lookupCompany, c);
						lookupCompany.setValue("02");
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 0;
						c.gridwidth = 2;

						txtCompany  = new JTextField("CENQHOMES DEVELOPMENT CORPORATION"); 
						txtCompany.setHorizontalAlignment(JLabel.CENTER);
						txtCompany.setFont(FncGlobal.sizeTextValue);
						txtCompany.setEditable(false);
						txtCompany.setFocusable(false);
						panCenter.add(txtCompany , c);
					}

					/*        Project             */
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 1;
						c.gridwidth = 1;

						JLabel label = new JLabel("Project"); 
						label.setHorizontalAlignment(JLabel.LEADING);
						label.setFont(FncGlobal.sizeLabel);
						panCenter.add(label, c);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 1;

						lookupProject = new _JLookup("", "Project");
						lookupProject.setReturnColumn(0);
						lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									lookupProject.setValue(data[0].toString());
									txtProject.setText(data[1].toString());
									lookupPhase.setLookupSQL(lookupMethods.getPhase(lookupProject.getValue()));
									reload(); 
								}
							}
						});
						lookupProject.addKeyListener(new KeyListener() {

							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookupProject.setValue("");
									txtProject.setText("All");
									reload(); 
								}
							}

							public void keyReleased(KeyEvent e) {

							}

							public void keyPressed(KeyEvent e) {

							}
						});
						lookupProject.setFont(FncGlobal.sizeTextValue);     
						panCenter.add(lookupProject, c);
						lookupProject.setValue("");
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 1;
						c.gridwidth = 2;

						txtProject = new JTextField("All"); 
						txtProject.setHorizontalAlignment(JLabel.CENTER);
						txtProject.setFont(FncGlobal.sizeTextValue);
						txtProject.setEditable(false);
						txtProject.setFocusable(false);                                                            
						panCenter.add(txtProject, c);
					}

					/*        Phase               */
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 2;

						c.gridwidth = 1;

						JLabel label = new JLabel("Phase"); 
						label.setHorizontalAlignment(JLabel.LEADING);
						label.setFont(FncGlobal.sizeLabel);
						panCenter.add(label, c);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 2;

						lookupPhase = new _JLookup("", "Phase");
						lookupPhase.setReturnColumn(0);
						lookupPhase.setLookupSQL(lookupMethods.getPhase(lookupProject.getValue()));
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									lookupPhase.setValue(data[0].toString());
									txtPhase.setText(data[1].toString());
									reload(); 
								}
							}
						});
						lookupPhase.addKeyListener(new KeyListener() {

							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookupPhase.setValue("");
									txtPhase.setText("All");
									reload(); 
								}
							}

							public void keyReleased(KeyEvent e) {

							}

							public void keyPressed(KeyEvent e) {

							}
						});
						lookupPhase.setFont(FncGlobal.sizeTextValue);                                                              
						panCenter.add(lookupPhase, c);
						lookupPhase.setValue("");
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 2;
						c.gridwidth = 2; 

						txtPhase = new JTextField("All"); 
						txtPhase.setHorizontalAlignment(JLabel.CENTER);
						txtPhase.setFont(FncGlobal.sizeTextValue);
						txtPhase.setEditable(false);
						txtPhase.setFocusable(false);                                                            
						panCenter.add(txtPhase, c);
					}

					/*        Contractor                    */
					{
						c.weightx = 0.25;
						c.weighty = 1;

						c.gridx = 0; 
						c.gridy = 3;

						c.gridwidth = 1; 

						JLabel label = new JLabel("Contractor"); 
						label.setHorizontalAlignment(JLabel.LEADING);
						label.setFont(FncGlobal.sizeLabel);
						panCenter.add(label, c);
					}
					{
						c.weightx = 0.5;
						c.weighty = 1;

						c.gridx = 1; 
						c.gridy = 3;

						lookupContractor = new _JLookup("", "Contractor");
						lookupContractor.setReturnColumn(0);
						lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());
						lookupContractor.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									lookupContractor.setValue(data[0].toString());
									txtContractor.setText(data[1].toString());
									reload(); 
								}
							}
						});
						lookupContractor.addKeyListener(new KeyListener() {

							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookupContractor.setValue("");
									txtContractor.setText("All");
									reload(); 
								}
							}

							public void keyReleased(KeyEvent e) {

							}

							public void keyPressed(KeyEvent e) {

							}
						});
						lookupContractor.setFont(FncGlobal.sizeTextValue);                                                            
						panCenter.add(lookupContractor, c);
						lookupContractor.setValue("");
					}
					{
						c.weightx = 1.5;
						c.weighty = 1;

						c.gridx = 2; 
						c.gridy = 3;
						c.gridwidth = 2; 

						txtContractor = new JTextField("All"); 
						txtContractor.setHorizontalAlignment(JLabel.CENTER);
						txtContractor.setFont(FncGlobal.sizeTextValue);
						txtContractor.setEditable(false);
						txtContractor.setFocusable(false);                                                            
						panCenter.add(txtContractor, c);
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 350));
				{
					{
						JPanel panDate = new JPanel(new GridLayout(1, 4, 5, 5)); 
						panEnd.add(panDate, BorderLayout.PAGE_START);
						panDate.setPreferredSize(new Dimension(0, 30));
						{
							{
								chkAll = new JCheckBox("All Types");
								panDate.add(chkAll);
								chkAll.setHorizontalAlignment(JLabel.CENTER);
								chkAll.setFont(FncGlobal.sizeLabel);
								chkAll.setSelected(true);
								chkAll.addItemListener(item);
							}
							{
								chkOngoing = new JCheckBox("w/ Ongoing");
								panDate.add(chkOngoing);
								chkOngoing.setHorizontalAlignment(JLabel.CENTER);
								chkOngoing.setFont(FncGlobal.sizeLabel);
								chkOngoing.setSelected(true);
								chkOngoing.setSelected(false);
								chkOngoing.addItemListener(item);
							}
							{
								JLabel label = new JLabel("NTP Date");
								panDate.add(label); 
								label.setHorizontalAlignment(JLabel.TRAILING);
								label.setFont(FncGlobal.sizeLabel);
							}
							{
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								panDate.add(dteDateFrom); 
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(true);
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateFrom.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
									public void propertyChange(PropertyChangeEvent evt) {
										reload();
									}
								});
								dteDateFrom.setToolTipText("Press delete to clear date");
								((Component) dteDateFrom.getDateEditor()).addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											System.out.println();
											System.out.println("dteDateFrom: "+"'"+dteDateFrom.getDate().toString()+"'");
											
											dteDateFrom.setDate(null);
										}
									}

									public void keyReleased(KeyEvent e) {

									}

									public void keyPressed(KeyEvent e) {

									}
								});
							}
						}
					}
					{
						JPanel panGrid = new JPanel(new BorderLayout(5, 5)); 
						panEnd.add(panGrid, BorderLayout.CENTER); 
						panGrid.setBorder(JTBorderFactory.createTitleBorder("List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							panGrid.add(getGrid(), BorderLayout.CENTER); 
						}
					}
					{
						JPanel panButton = new JPanel(new GridLayout(1, 4, 5, 5));
						panEnd.add(panButton, BorderLayout.PAGE_END); 
						panButton.setPreferredSize(new Dimension(0, 30));
						{
							{
								btnSave = new JButton("Save");
								panButton.add(btnSave);
								btnSave.setFont(FncGlobal.sizeControls);
								btnSave.setEnabled(false);
								btnSave.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (JOptionPane.showConfirmDialog(getContentPane(), "Save values?", "Confirm", 
												JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION) {
											save();
											reload();
											btnSave.setEnabled(false);
										}
									}
								});
							}
							{
								panButton.add(new JLabel(""));                                                             
								panButton.add(new JLabel(""));
							}
							{
								JButton btnPreview = new JButton("Preview");
								panButton.add(btnPreview);
								btnPreview.setFont(FncGlobal.sizeControls);
								btnPreview.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										btnSave.setEnabled(false);
										save(); 
										preview();                                                                                 
									}
								});
							}
						}
					}
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupContractor, dteDateFrom, chkAll));
		loadContractors(modelCredit, rowCredit); 
	}


	private void preview() {
		String criteria = "Contractor's Credit Balance";            
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		String all_ntp = "";
		String ntp_type = ""; 

		if (chkAll.isSelected()) {
			all_ntp = "yes"; 
			ntp_type = "All";                              
		} else {
			all_ntp = "no"; 
			ntp_type = "Housing Only";                              
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProject.getValue());  
		mapParameters.put("project", txtProject.getText());          
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+strLogo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("phase_code", lookupPhase.getValue());      
		mapParameters.put("phase_no", txtPhase.getText());
		mapParameters.put("all_ntp", chkAll.isSelected());
		mapParameters.put("contractor_id", lookupContractor.getValue());
		mapParameters.put("contractor_name", txtContractor.getText());
		mapParameters.put("ntp_type", ntp_type);
		mapParameters.put("with_ongoing", chkOngoing.isSelected());
		FncReport.generateReport("/Reports/rptContractorCreditBalance.jasper",reportTitle, txtProject.getText(), mapParameters);
	}

	private JPanel getGrid() {
		JPanel panGrid = new JPanel(new BorderLayout(5, 5)); 
		{
			JScrollPane scr = new JScrollPane();
			panGrid.add(scr, BorderLayout.CENTER);
			scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelCredit = new model_credit_balance();
				modelCredit.setEditable(false);

				tblCredit = new _JTableMain(modelCredit);
				rowCredit = tblCredit.getRowHeader();
				scr.setViewportView(tblCredit);

				tblCredit.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblCredit.getColumnModel().getColumn(1).setPreferredWidth(297);
				tblCredit.getColumnModel().getColumn(2).setPreferredWidth(82);
				tblCredit.getColumnModel().getColumn(3).setPreferredWidth(72);
				tblCredit.getColumnModel().getColumn(4).setPreferredWidth(72);
				tblCredit.getColumnModel().getColumn(5).setPreferredWidth(102);

				tblCredit.hideColumns("entity_id");
				tblCredit.setSortable(false);
				tblCredit.addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {
						btnSave.setEnabled(true);
					}

					public void focusGained(FocusEvent e) {
						
					}
				});

				rowCredit = tblCredit.getRowHeader();
				rowCredit.setModel(new DefaultListModel());
				scr.setRowHeaderView(rowCredit);
				scr.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		return panGrid; 
	}

	ItemListener item = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {
			try {
				reload();
			} catch (NullPointerException x) {
				
			}
		}
	};
	
	private void reload() {
		loadContractors(modelCredit, rowCredit); 
	}
	
	private void loadContractors(DefaultTableModel model, JList row) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		row.setModel(listModel); 
		
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select * " + 
				"from view_contractor_credit_balance_list( \n" + 
				"'"+lookupCompany.getValue()+"', \n" + 
				"'"+lookupProject.getValue()+"', \n" + 
				"'"+lookupPhase.getValue()+"', \n" + 
				"'"+lookupPhase.getValue()+"', \n" + 
				""+((dteDateFrom.getDate()==null)?"null":"'"+dteDateFrom.getDate().toString()+"'")+"::date, \n" + 
				""+chkAll.isSelected()+", \n" + 
				""+chkOngoing.isSelected()+" \n" +
				"); ");
		
		if (dbExec.isNotNull()) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				model.addRow(dbExec.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	private void save() {
		pgUpdate dbExec = new pgUpdate(); 
		for (int x=0; x<modelCredit.getRowCount(); x++) {
			dbExec.executeUpdate("update rf_contractor_sup_details \n" +
					"set other_factor = '"+modelCredit.getValueAt(x, 2).toString()+"'::numeric(19, 2), \n" +
					"fs = '"+((modelCredit.getValueAt(x, 3)==null)?"0":modelCredit.getValueAt(x, 3).toString())+"'::int, \n" +
					"selected = "+(Boolean) modelCredit.getValueAt(x, 0)+", \n" +
					"con_new = '"+(((Boolean) modelCredit.getValueAt(x, 4))?"Yes":"No")+"', \n" +
					"equity = "+modelCredit.getValueAt(x, 5).toString()+" \n" +
					"where con_name = '"+modelCredit.getValueAt(x, 6).toString()+"'", false);
		}
		dbExec.commit();
	}
}

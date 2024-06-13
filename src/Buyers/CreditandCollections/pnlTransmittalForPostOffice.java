package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import components._JButton;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_NoticesTagBatch;

@SuppressWarnings({"rawtypes","unchecked"})
public class pnlTransmittalForPostOffice extends JXPanel implements _GUI,MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXPanel pnlNorth;
	private JXPanel pnlListBatch;
	private JLabel lblSearch;
	private JXPanel pnlSearch;
	private _JXTextField txtSearch;
	private JXPanel pnlTableBatch;
	private JScrollPane scrollBatch;
	private model_NoticesTagBatch modelBatchModel;
	private _JTableMain tblBatch;
	private JList rowHeadeBatch;
	private JXPanel pnlButton;
	private _JButton btnPreview;
	private _JButton btnCancel;
	private _JTabbedPane tabCenter;
	private JPanel pnlAllTransmitall;
	private JXPanel pnlListBatchAll;
	private JXPanel pnlSearchAll;
	private _JXTextField txtSearchAll;
	private JXPanel pnlTableBatchAll;
	private JScrollPane scrollAllTransmittal;
	private model_NoticesTagBatch modelAllTransModel;
	private _JTableMain tblAllTransmittal;
	private _JButton btnRefresh;
	private JLabel lblStatus;
	private JLabel lblDate;
	private DefaultComboBoxModel cmbStatusModel;
	private JComboBox cmbStatus;
	private _JDateChooser dtePreparedDate;
	private JButton btnGenerate;

	public pnlTransmittalForPostOffice(Transmittal trans) {
		initGUI();
	}

	public pnlTransmittalForPostOffice(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlTransmittalForPostOffice(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlTransmittalForPostOffice(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		{
			{
				pnlNorth = new JXPanel();
				this.add(pnlNorth,BorderLayout.NORTH);
				pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlNorth.setLayout(new BorderLayout(3,3));
				pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 60));
				
				{
					JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlWest,BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(300, 0));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlWest.add(pnlLabel,BorderLayout.WEST);
						{
							lblStatus = new JLabel("Status");
							pnlLabel.add(lblStatus);
							
							lblDate = new JLabel("Prepared Date");
							pnlLabel.add(lblDate);
							
						}
						{

							JPanel pnlAction = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlWest.add(pnlAction,BorderLayout.CENTER);
							{
								cmbStatusModel = new DefaultComboBoxModel(
										new String[] {"All", "Transmitted", "For Sending"});
								cmbStatus = new JComboBox();
								pnlAction.add(cmbStatus);
								cmbStatus.setModel(cmbStatusModel);
								cmbStatus.setSelectedItem(null);
								cmbStatus.addActionListener(this);

							
								
								dtePreparedDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlAction.add(dtePreparedDate);
								dtePreparedDate.setDate(dateFormat(getDateSQL()));

							}
							
						
						}
						
					}

				}
				{
					JPanel pnlCenter= new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setPreferredSize(new Dimension(300, 0));
					{
						btnGenerate = new JButton("Generate");
						pnlCenter.add(btnGenerate,BorderLayout.CENTER);
						btnGenerate.addActionListener(this);
					}
				}
				
			
			}
			
			
			
			{
				pnlListBatch = new JXPanel();
				this.add(pnlListBatch,BorderLayout.CENTER);
				pnlListBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlListBatch.setLayout(new BorderLayout(3,3));
				//pnlListBatch.setPreferredSize(new Dimension(this.getWidth(), 200));
				pnlListBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of Batch"));
				{

					{
						JXPanel pnlSearch = new JXPanel(new BorderLayout(3,3));
						pnlListBatch.add(pnlSearch,BorderLayout.NORTH);
						{
							lblSearch = new JLabel("Search : ");
							pnlSearch.add(lblSearch, BorderLayout.WEST);
						}
						{

						}
						{
							txtSearch = new _JXTextField("Enter Batch No. to Search");
							pnlSearch.add(txtSearch, BorderLayout.CENTER);
							txtSearch.setEditable(true);
							txtSearch.setFont(new Font("Tahoma",Font.PLAIN,12));
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {
									SearchBatch();
								}
							});
							/*txtSearch.getDocument().addDocumentListener(new DocumentListener() {

								@Override
								public void removeUpdate(DocumentEvent e) {
									searchText();

								}

								@Override
								public void insertUpdate(DocumentEvent e) {
									searchText();

								}

								@Override
								public void changedUpdate(DocumentEvent e) {
									searchText();

								}
							});*/

						}
					}
					{
						pnlTableBatch = new JXPanel(new BorderLayout(3,3));
						pnlListBatch.add(pnlTableBatch,BorderLayout.CENTER);
						{


							scrollBatch = new JScrollPane();
							pnlTableBatch.add(scrollBatch, BorderLayout.CENTER);
							scrollBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollBatch.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblBatch.clearSelection();
								}
							});

							{

								modelBatchModel = new model_NoticesTagBatch();
								modelBatchModel.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHeadeBatch.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeadeBatch.getModel()).addElement(modelBatchModel.getRowCount());
										}
									}
								});

								tblBatch = new _JTableMain(modelBatchModel);
								scrollBatch.setViewportView(tblBatch);
								modelBatchModel.setEditable(true);
								tblBatch.setHorizontalScrollEnabled(true);
								tblBatch.addMouseListener(this);
								tblBatch.packAll();

								/** Repaint for Highlight **/
								tblBatch.getTableHeader().addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent evt) {
										tblBatch.repaint();
									}
								});
							}
							{
								rowHeadeBatch = tblBatch.getRowHeader();
								rowHeadeBatch.setModel(new DefaultListModel());
								scrollBatch.setRowHeaderView(rowHeadeBatch);
								scrollBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
				}
			
			
			}
			
			{
				pnlButton = new JXPanel();
				this.add(pnlButton,BorderLayout.SOUTH);
				pnlButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlButton.setLayout(new BorderLayout(3, 3));
				pnlButton.setPreferredSize(new Dimension(this.getWidth(), 50));
				//pnlButton.setBorder(components.JTBorderFactory.createTitleBorder("Tagged Notices"));
				
				{
					JPanel west = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlButton.add(west, BorderLayout.WEST);
					west.setPreferredSize(new Dimension(100, 50));
					{
						btnRefresh = new _JButton("Refresh");
						west.add(btnRefresh);
						btnRefresh.addActionListener(this);
					}
					
					JPanel east = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlButton.add(east, BorderLayout.EAST);
					{
						btnPreview = new _JButton("Preview / Print");
						east.add(btnPreview);
						btnPreview.addActionListener(this);

					}
					{
						btnCancel = new _JButton("Cancel");
						east.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				
				}
				
			}
		}
		//generateBatchNo(modelBatchModel);
		generateAllTransmittal(modelBatchModel, cmbStatus.getSelectedIndex(),dtePreparedDate.getDate());
		
	}
	private void searchText(){
		// Searches the table with the search string provided and goto or scroll to the corresponding table cell
		int rows = tblBatch.getRowCount();
		int col =   1;
		java.awt.Rectangle rect;
		Boolean bl = false;

		for(int i=0;i<rows;i++)
		{
			String value = tblBatch.getValueAt(i, col).toString(); 
			if(value.toUpperCase().startsWith(txtSearch.getText().toUpperCase()) && bl == false)
			{
				tblBatch.setRowSelectionInterval(i, i);

				rect = tblBatch.getCellRect(i, col, true);
				tblBatch.scrollRectToVisible(rect);

				bl = true;
			}
		}	// for
	} // searchText
	
	private void searchTextAll(){
		// Searches the table with the search string provided and goto or scroll to the corresponding table cell
		int rows = tblAllTransmittal.getRowCount();
		int col =   1;
		java.awt.Rectangle rect;
		Boolean bl = false;

		for(int i=0;i<rows;i++)
		{
			String value = tblAllTransmittal.getValueAt(i, col).toString(); 
			if(value.toUpperCase().startsWith(txtSearch.getText().toUpperCase()) && bl == false)
			{
				tblAllTransmittal.setRowSelectionInterval(i, i);

				rect = tblAllTransmittal.getCellRect(i, col, true);
				tblAllTransmittal.scrollRectToVisible(rect);

				bl = true;
			}
		}	// for
	} // searchText

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	private void generateBatchNo(model_NoticesTagBatch model){
		pgSelect db = new pgSelect();
		model.clear();


		String SQL = "";

		SQL = "select false,* from (select distinct on (a.batch_no)  a.batch_no,(case sent_to when 'POST OFFICE' then 'Transmitted' else '' end) as status\n" + 
				"from rf_client_notices  a\n" + 
				"left join rf_notices_sent b on a.batch_no = b.batch_no \n" + 
				"and a.batch_no is not null\n" + 
				"order by a.batch_no\n" + 
				") a\n" + 
				"";


		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			//JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
		tblBatch.packAll();


	}
	
	private void generateAllTransmittal(model_NoticesTagBatch model, Integer status, Date datePrepared){
		pgSelect db = new pgSelect();
		model.clear();


		String SQL = "";

		if (status == 0 || status == 1 || status == 2) {
			SQL = "select false,* from (select distinct on (a.batch_no)  a.batch_no,(case sent_to when 'POST OFFICE' then 'Transmitted' else 'For Sending' end) as status, dateprep::date\n" + 
					"from rf_client_notices  a\n" + 
					"left join rf_notices_sent b on a.batch_no = b.batch_no \n" + 
					"and a.batch_no is not null\n" + 
					"order by a.batch_no) a\n" + 
					(status  == 0  ? "--"  : status  == 1 ? "where status = 'Transmitted'" : "where status = 'For Sending'" ) + " \n" +
					(status  == 0  ? "where dateprep = '"+datePrepared+"'::date"  :"AND dateprep = '"+datePrepared+"'::date ") + " \n" +
					"";
		}else{
			

			SQL = "select false,* from (select distinct on (a.batch_no)  a.batch_no,(case sent_to when 'POST OFFICE' then 'Transmitted' else 'For Sending' end) as status, dateprep::date\n" + 
					"from rf_client_notices  a\n" + 
					"left join rf_notices_sent b on a.batch_no = b.batch_no \n" + 
					"and a.batch_no is not null\n" + 
					"order by a.batch_no) a\n" + 
				//	(status  == 0  ? "--"  : status  == 1 ? "where status = 'Transmitted'" : "where status = 'For Sending'" ) + " \n" +
				//	(status  == 0  ? "where dateprep = '"+datePrepared+"'::date"  :"AND dateprep = '"+datePrepared+"'::date ") + " \n" +
					"";

		}
		
		

		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			///JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
		tblBatch.packAll();


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnGenerate)) {
			generateAllTransmittal(modelBatchModel,cmbStatus.getSelectedIndex(),dtePreparedDate.getDate());
		}
		
		if (e.getSource().equals(btnPreview)) {
				Preview(modelBatchModel);
				
		}	
		
		if (e.getSource().equals(btnCancel)) {
			Cancel_Process();
		}
		
		if (e.getSource().equals(btnRefresh)) {
			//generateBatchNo(modelBatchModel);
			generateAllTransmittal(modelBatchModel,cmbStatus.getSelectedIndex(),dtePreparedDate.getDate());
		}
		
	}

	private Boolean hasSelected(model_NoticesTagBatch model) {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < model.getRowCount(); x++){
			listSelected.add((Boolean) model.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	
	private void Cancel_Process(){
		generateBatchNo(modelBatchModel);
		txtSearch.setText("");
	}
	
	private void Preview(model_NoticesTagBatch model){
		

		
		ArrayList<String> listBatches = new ArrayList<String>();
		
		
		if (hasSelected(model)) {
			for (int i = 0; i < model.getRowCount(); i++) {
				Boolean isSelected = (Boolean) model.getValueAt(i, 0);
				String batch_no = (String) model.getValueAt(i, 1);
				
				if (isSelected) {
					System.out.println("Batch no " + batch_no);	
					listBatches.add(batch_no);
					
				}
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			/*for (int i = 0; i < listBatches.size(); i++) {
				
				mapParameters.put("batch_no", listBatches.get(i));
				//mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
				//mapParameters.put("prepared_by", UserInfo.Alias);
				
			}*/
			
			System.out.printf("%s%n", listBatches.toString().replace("[", "'").replace("]", "'"));
			
			
			//mapParameters.put("batch_no", listBatches.toString().replace("[", "'").replace("]", "'"));
			mapParameters.put("batch_no", listBatches.toString().replace("[", "").replace("]", ""));
			FncReport.generateReport(String.format("/Reports/%s.jasper", "subrptTransmittalForPostOffice"), "Transmittal For Post Office", mapParameters);
		}else{
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select batch no.", "Preview/Print", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	private void SearchBatch(){

		String search = txtSearch.getText().toUpperCase().replace("'", "");
		try{
			modelBatchModel.clear();
		} catch (ArrayIndexOutOfBoundsException e){}
		String sql = "select false,* from (select distinct on (a.batch_no)  a.batch_no,(case sent_to when 'POST OFFICE' then 'Transmitted' else 'For Sending' end) as status, dateprep::date\n" + 
				"				from rf_client_notices  a\n" + 
				"				left join rf_notices_sent b on a.batch_no = b.batch_no \n" + 
				"				and a.batch_no is not null\n" + 
				"				";

		if(txtSearch.getText().isEmpty() == false){
			sql = sql + "where a.batch_no like '%"+search+"' )a order by a.batch_no \n";
		}else{
			sql = sql + ") a order by a.batch_no";
		}

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display Employees", sql);
		if(db.isNotNull()){
			for (int x = 0; x<db.getRowCount(); x++){
				modelBatchModel.addRow(db.getResult()[x]);
			}
			scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBatch.getRowCount())));
			tblBatch.packAll();
		}
	
	}
	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e){
		} 

		return date;
	}

	private  String getDateSQL(){
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();

	}

}

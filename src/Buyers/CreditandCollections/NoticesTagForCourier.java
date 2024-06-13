package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import tablemodel.model_NoticesTagBatch;
import tablemodel.model_NoticesTaggedBatch;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import interfaces._GUI;
import components._JButton;
import components._JInternalFrame;
import components._JTableMain;

public class NoticesTagForCourier extends _JInternalFrame implements _GUI,MouseListener,KeyListener,ActionListener{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Notices Tag for Courier";
	public static Dimension frameSize = new Dimension(500, 600);
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JXPanel pnlDateBatch;
	private JXPanel pnlProcess;
	private _JButton btnProcess;
	private JXPanel pnlCenter;
	private JXPanel pnlSouth;
	private JXPanel pnlListBatch;
	private JXPanel pnlTaggedBatch;
	private JTextField txtSearch;
	private JXPanel pnlSearch;
	private JXPanel pnlS;
	private JLabel lblSearch;
	private JXPanel pnlTableBatch;
	private JScrollPane scrollBatch;
	private model_NoticesTagBatch modelBatchTaggingModel;
	private _JTableMain tblBatchTagging;
	private JList rowHeadeBatchTagging;
	private JXPanel pnlTagBatch;
	private JScrollPane scrollBatchTagged;
	private _JTableMain tblBatchTagged;
	private JList rowHeaderBatchTagged;
	private model_NoticesTaggedBatch modelBatchTaggedModel;
	private JXPanel pnlLabelDate;
	private JXPanel pnlTo;
	private JLabel lblFrom;
	private _JDateChooser dteFrom;
	private JLabel lblTo;
	private _JDateChooser dteTo;

	public NoticesTagForCourier() {
		super(title, true, true, true, true);
		initGUI();
	}

	public NoticesTagForCourier(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public NoticesTagForCourier(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(frameSize));
			getContentPane().setLayout(new BorderLayout());

			{

				pnlMain = new JXPanel();
				this.add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(3,3));

				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNorth.setLayout(new GridLayout(1, 2, 3, 3));
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 100));
					{
						pnlDateBatch = new JXPanel();
						pnlNorth.add(pnlDateBatch);
						pnlDateBatch.setBorder(components.JTBorderFactory.createTitleBorder("Date of Batch"));
						pnlDateBatch.setLayout(new BorderLayout(3, 3));
						{
							pnlLabelDate = new JXPanel(new GridLayout(2, 1, 3, 3));
							pnlDateBatch.add(pnlLabelDate,BorderLayout.WEST);
							{
								lblFrom = new JLabel("From :");
								pnlLabelDate.add(lblFrom);
								
							}
							{
								lblTo = new JLabel("To :");
								pnlLabelDate.add(lblTo);
							}
							
						}
								
						{
							pnlTo = new JXPanel(new GridLayout(2, 1, 3, 3));
							pnlDateBatch.add(pnlTo,BorderLayout.CENTER);
							{
								dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlTo.add(dteFrom);
								dteFrom.setDate(null);
							}
							
							{
								dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlTo.add(dteTo);
								dteTo.setDate(null);
							}
						}
					}
					{
						pnlProcess = new JXPanel();
						pnlNorth.add(pnlProcess);
						pnlProcess.setLayout(new BorderLayout());
						//pnlProcess.setBorder(components.JTBorderFactory.createTitleBorder("Date of Batch"));
						{
							btnProcess = new _JButton("Generate");
							pnlProcess.add(btnProcess,BorderLayout.CENTER);
							btnProcess.addActionListener(this);
						}
					}

					//
				}//pnlNorth
				{
					pnlCenter = new JXPanel();
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCenter.setLayout(new BorderLayout(3,3));
					pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 400));
					//					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Batch"));

					{
						pnlListBatch = new JXPanel();
						pnlCenter.add(pnlListBatch,BorderLayout.NORTH);
						pnlListBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlListBatch.setLayout(new BorderLayout(3,3));
						pnlListBatch.setPreferredSize(new Dimension(this.getWidth(), 200));
						pnlListBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of Batch"));
						{

							{
								pnlSearch = new JXPanel(new BorderLayout(3,3));
								pnlListBatch.add(pnlSearch,BorderLayout.NORTH);
								{
									lblSearch = new JLabel("Seach : ");
									pnlSearch.add(lblSearch, BorderLayout.WEST);
								}
								{

								}
								{
									txtSearch = new JTextField();
									pnlSearch.add(txtSearch, BorderLayout.CENTER);
									txtSearch.setFont(new Font("Tahoma",Font.PLAIN,12));
									txtSearch.addKeyListener(new KeyAdapter() {
										public void keyPressed(KeyEvent pwede) {
											// Return value to internal frame when user presses ENTER on table
											/*if(pwede.getKeyCode() == KeyEvent.VK_ENTER){
											returnValue();
										}*/
											if(pwede.getKeyCode() == KeyEvent.VK_DOWN || pwede.getKeyCode() == KeyEvent.VK_TAB){
												///tblDefault.requestFocus();
											}
										}
										/*public void keyReleased(KeyEvent pwede){
										searchText(((JTextField) pwede.getSource()).getText().trim().toUpperCase());
									}*/
									});

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
											tblBatchTagging.clearSelection();
										}
									});

									{

										modelBatchTaggingModel = new model_NoticesTagBatch();
										modelBatchTaggingModel.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeadeBatchTagging.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeadeBatchTagging.getModel()).addElement(modelBatchTaggingModel.getRowCount());
												}
											}
										});

										tblBatchTagging = new _JTableMain(modelBatchTaggingModel);
										scrollBatch.setViewportView(tblBatchTagging);
										modelBatchTaggingModel.setEditable(true);
										
										tblBatchTagging.setHorizontalScrollEnabled(true);
										tblBatchTagging.addMouseListener(this);
										tblBatchTagging.addKeyListener(this);
										tblBatchTagging.packAll();

										/** Repaint for Highlight **/
										tblBatchTagging.getTableHeader().addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent evt) {
												tblBatchTagging.repaint();
											}
										});
									}
									{
										
										rowHeadeBatchTagging = tblBatchTagging.getRowHeader();
										rowHeadeBatchTagging.setModel(new DefaultListModel());
										scrollBatch.setRowHeaderView(rowHeadeBatchTagging);
										scrollBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										
									}
								
								}
							}
							
						}

					}
					{
						pnlTaggedBatch = new JXPanel();
						pnlCenter.add(pnlTaggedBatch,BorderLayout.CENTER);
						pnlTaggedBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlTaggedBatch.setLayout(new BorderLayout(3,3));
						pnlTaggedBatch.setPreferredSize(new Dimension(this.getWidth(), 200));
						pnlTaggedBatch.setBorder(components.JTBorderFactory.createTitleBorder("Tagged Notices"));

						{

							pnlTagBatch = new JXPanel(new BorderLayout(3,3));
							pnlTaggedBatch.add(pnlTagBatch,BorderLayout.CENTER);
							{


								scrollBatchTagged = new JScrollPane();
								pnlTagBatch.add(scrollBatchTagged, BorderLayout.CENTER);
								scrollBatchTagged.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblBatchTagging.clearSelection();
									}
								});

								{

									modelBatchTaggedModel = new model_NoticesTaggedBatch();
									modelBatchTaggedModel.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											//Addition of rows
											if(e.getType() == 1){
												((DefaultListModel)rowHeaderBatchTagged.getModel()).addElement(modelBatchTaggedModel.getRowCount());

												if(modelBatchTaggedModel.getRowCount() == 0){
													rowHeaderBatchTagged.setModel(new DefaultListModel());
												}
											}
										}
									});

									tblBatchTagged = new _JTableMain(modelBatchTaggedModel);
									scrollBatchTagged.setViewportView(tblBatchTagged);
									modelBatchTaggedModel.setEditable(true);
									tblBatchTagged.setHorizontalScrollEnabled(true);
									tblBatchTagged.addMouseListener(this);
									tblBatchTagged.addKeyListener(this);
									tblBatchTagged.packAll();

									/** Repaint for Highlight **/
									tblBatchTagged.getTableHeader().addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											tblBatchTagged.repaint();
										}
									});
								}
								{
									rowHeaderBatchTagged = tblBatchTagged.getRowHeader();
									rowHeaderBatchTagged.setModel(new DefaultListModel());
									scrollBatchTagged.setRowHeaderView(rowHeaderBatchTagged);
									scrollBatchTagged.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									scrollBatchTagged.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

								}
							
							}
						
						}

					}

				}
				{

					pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlSouth.setLayout(new BorderLayout(3,3));
					pnlSouth.setPreferredSize(new Dimension(this.getWidth(), 50));
					//pnlSouth.setBorder(components.JTBorderFactory.createTitleBorder("Tagged Notices"));

				}
			}


		} catch (Exception e) {
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) { // XXX actionPerformed
		
		if (e.getSource().equals(btnProcess)) {
			
			getBatchForTagging(modelBatchTaggingModel, dteFrom.getDate(), dteTo.getDate());
			scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagging.getRowCount())));
		}
	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void getBatchForTagging(model_NoticesTagBatch model, Date dteFrom, Date dteTo){
		pgSelect db = new pgSelect();
		model.clear();
		

		String SQL = "";
		
		SQL = "SELECT false,* FROM sp_generate_batch_tagging('"+dteFrom+"', '"+ dteTo +"')";


		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	
		
		
	}

}

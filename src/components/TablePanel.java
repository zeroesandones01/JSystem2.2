package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.TableColumnExt;

import Functions.FncTables;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

public class TablePanel<T> extends JXPanel {

	private static final long serialVersionUID = 1032666119797277349L;

	private JScrollPane scrollOldMainCenter;
	private _JTableMain tblMain;
	private T modelMain;
	private JList rowHeaderMain;
	private TableRowSorter<DefaultTableModel> sorterOldMain;
	
	private JScrollPane scrollOldMainSouth;
	private _JTableTotal tblTotal;
	private T modelTotal;
	
	public TablePanel(String title, T modelMain, T modelTotal) {
		this.modelMain = modelMain;
		this.modelTotal = modelTotal;
	
		initThis(title);
		bindColumnTables();
	}

	public TablePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public TablePanel(LayoutManager layout) {
		super(layout);
	}

	public TablePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
	
	private void initThis(String title) {
		setLayout(new BorderLayout());
		setBorder(JTBorderFactory.createTitleBorder(title));
		{
			scrollOldMainCenter = new JScrollPane();//XXX Center
			add(scrollOldMainCenter, BorderLayout.CENTER);
			scrollOldMainCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollOldMainCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollOldMainCenter.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblMain.clearSelection();
					tblTotal.clearSelection();
				}
			});
			{
				tblMain = new _JTableMain((TableModel) modelMain);
				scrollOldMainCenter.setViewportView(tblMain);
				tblMain.setFillsViewportHeight(false);
				tblMain.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if(tblMain.rowAtPoint(e.getPoint()) == -1){
							tblTotal.clearSelection();
						}else{
							tblMain.setCellSelectionEnabled(true);
						}
					}
					public void mouseReleased(MouseEvent e) {
						if(tblMain.rowAtPoint(e.getPoint()) == -1){
							tblTotal.clearSelection();
						}else{
							tblMain.setCellSelectionEnabled(true);
						}
					}
				});
				sorterOldMain = new TableRowSorter<DefaultTableModel>((DefaultTableModel) modelMain);
				tblMain.setRowSorter(sorterOldMain);
			}
			{
				setRowHeader(tblMain.getRowHeader());
				scrollOldMainCenter.setRowHeaderView(getRowHeader());
				scrollOldMainCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		{
			scrollOldMainSouth = new JScrollPane();
			add(scrollOldMainSouth, BorderLayout.SOUTH);
			scrollOldMainSouth.setPreferredSize(new Dimension(799, 37));
			scrollOldMainSouth.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollOldMainSouth.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollOldMainSouth.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 17));
			scrollOldMainSouth.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					scrollOldMainCenter.getHorizontalScrollBar().setValue(e.getValue());
				}
			});
			scrollOldMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer("0"));
			{
				((DefaultTableModel) modelTotal).addRow(new Object[] { null, "Total", 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00 });

				tblTotal = new _JTableTotal((TableModel) modelTotal, tblMain);
				scrollOldMainSouth.setViewportView(tblTotal);
				tblTotal.setTableHeader(null);

				tblTotal.setRowHeight(20);
				tblTotal.setCellSelectionEnabled(true);

				tblTotal.setDefaultRenderer(Object.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
				tblTotal.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
				tblTotal.setFillsViewportHeight(false);

				tblTotal.addHighlighter(FncTables.getHighlighterTOTAL(1));

				tblTotal.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						((_JTableTotal)e.getSource()).clearSelection();
					}
				});

				/*********************** Main Table ***********************/
				/*tblMain.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
					public void columnAdded(TableColumnModelEvent arg0) { }
					public void columnMarginChanged(ChangeEvent arg0) {
						TableColumnModel tableColumnModel = tblMain.getColumnModel();
						TableColumnModel footerColumnModel = tblTotal.getColumnModel();
						for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
							int w = tableColumnModel.getColumn(i).getWidth();
							footerColumnModel.getColumn(i).setMinWidth(w);
							footerColumnModel.getColumn(i).setMaxWidth(w);
							footerColumnModel.getColumn(i).setPreferredWidth(w);
						}
						tblTotal.doLayout();
						tblTotal.repaint();
						repaint();
					}
					public void columnMoved(TableColumnModelEvent arg0) { }
					public void columnRemoved(TableColumnModelEvent arg0) { }
					public void columnSelectionChanged(ListSelectionEvent arg0) { }
				});*/
			}
		}
	}

	/**
	 * Bind column of Main table into Total table
	 */
	private void bindColumnTables(){
		for(int x=0; x < tblMain.getColumnCount(); x++){
			String headerValue = (String) tblMain.getColumnExt(x).getHeaderValue();

			tblMain.getColumnExt(headerValue).addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					TableColumnExt columnExt = (TableColumnExt) evt.getSource();
					if(evt.getPropertyName().equals("visible")){
						tblTotal.getColumnExt(columnExt.getHeaderValue()).setVisible((Boolean) evt.getNewValue());
					}
				}
			});
		}
	}

	/**
	 * @return the tblMain
	 */
	public _JTableMain getTableMain() {
		return tblMain;
	}

	/**
	 * @param tblMain the tblMain to set
	 */
	public void setTableMain(_JTableMain tblMain) {
		this.tblMain = tblMain;
	}

	/**
	 * @return the modelMain
	 */
	public T getModelMain() {
		return modelMain;
	}

	/**
	 * @param modelMain the modelMain to set
	 */
	public void setModelMain(T modelMain) {
		this.modelMain = modelMain;
	}

	/**
	 * @return the tblTotal
	 */
	public _JTableTotal getTableTotal() {
		return tblTotal;
	}

	/**
	 * @param tblTotal the tblTotal to set
	 */
	public void setTableTotal(_JTableTotal tblTotal) {
		this.tblTotal = tblTotal;
	}

	/**
	 * @return the modelTotal
	 */
	public T getModelTotal() {
		return modelTotal;
	}

	/**
	 * @param modelTotal the modelTotal to set
	 */
	public void setModelTotal(T modelTotal) {
		this.modelTotal = modelTotal;
	}

	/**
	 * @return the rowHeaderMain
	 */
	public JList getRowHeader() {
		return rowHeaderMain;
	}

	/**
	 * @param rowHeaderMain the rowHeaderMain to set
	 */
	public void setRowHeader(JList rowHeaderMain) {
		this.rowHeaderMain = rowHeaderMain;
	}

}

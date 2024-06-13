/**
 * 
 */
package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.math.BigDecimal;

import interfaces._GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXFormattedTextField;

import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_ChangePagibigContribution extends JPanel implements
		_GUI {

	private static final long serialVersionUID = -5900431086689190915L;
	
	private JPanel pnlMain;
	
	private JPanel pnlWest;
	private JLabel lblPagibigContri;
	private JLabel lblDateCancelled;
	
	private JPanel pnlCenter;
	private _JXFormattedTextField txtPagibigContri;
	private _JDateChooser dteCancelled;
	private pnlOtherRequest_OldData od;

	public pnlOtherReq_ChangePagibigContribution() {
		initGUI();
	}

	public pnlOtherReq_ChangePagibigContribution(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public pnlOtherReq_ChangePagibigContribution(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherReq_ChangePagibigContribution(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(100, 100));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.NORTH);
			{
				pnlWest = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				{
					lblPagibigContri = new JLabel("Pagibig Contribution");
					pnlWest.add(lblPagibigContri);
				}
				/*{
					txtPagibigContri = new _JXFormattedTextField();
					pnlWest.add(txtPagibigContri, BorderLayout.CENTER);
					txtPagibigContri.setHorizontalAlignment(JXFormattedTextField.RIGHT);
					txtPagibigContri.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtPagibigContri.setValue(new BigDecimal("0.00"));
					txtPagibigContri.setPreferredSize(new Dimension(150, 20));
				}*/
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter);
				{
					txtPagibigContri = new _JXFormattedTextField();
					pnlCenter.add(txtPagibigContri);
					txtPagibigContri.setHorizontalAlignment(JXFormattedTextField.RIGHT);
					txtPagibigContri.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtPagibigContri.setValue(new BigDecimal("0.00"));
					txtPagibigContri.setPreferredSize(new Dimension(150, 20));
				}
			}
		}
	}//XXX END OF INIT GUI
	
	public void getDataChangePagibigContribution(){
		
	}
	
	public void editChangePagibigContribution(){
		txtPagibigContri.setEditable(true);
	}
	
	public void displayChangePagibigContri(){
		
	}
}

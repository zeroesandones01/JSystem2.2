/**
 * 
 */
package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Database.pgSelect;
import tablemodel.modelFADProcessAdmin;
import components._JInternalFrame;

/**
 * @author John Lester Fatallo
 */
public class FADProcessAdmin extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -673570866013567718L;

	Dimension frameSize = new Dimension(500, 500);
	static String title = "FAD Process Admin";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;

	private JPanel pnlCenter;
	private modelFADProcessAdmin modelFADProcess;

	private JPanel pnlSouth;

	public FADProcessAdmin() {
		super(title, true, true, true, true);
		initGUI();
	}

	public FADProcessAdmin(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(LINE_BORDER);
			{
				pnlNorthWest = new JPanel();
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
			}
			{
				pnlNorthEast = new JPanel();
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
			}
		}
		{
			pnlCenter = new JPanel();
			this.add(pnlCenter, BorderLayout.CENTER);
			{
				
			}
		}
		{
			pnlSouth = new JPanel();
			this.add(pnlSouth, BorderLayout.SOUTH);
			
		}
	}//END OF INIT GUI

	private void displayAllProcess(){
		try{
			modelFADProcess.clear();
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		pgSelect db = new pgSelect();

		String sql = "select false, process_no, TRIM(process_name) from mf_fad_process";
		db.select(sql);
		if(db.isNotNull()){
			for (int x = 0; x<db.getRowCount(); x++){
				modelFADProcess.addRow(db.getResult()[x]);
			}
		}
	}
}

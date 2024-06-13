package jasper;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import Functions.FncLookAndFeel;

/**
 * 
 * @author Alvin Gonzales - edited (2015-07-29)
 *
 */
public class _JasperViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	public _JasperViewer(JasperPrint jasperPrint, String title) {
		super(title);

		initComponents(new _JRViewer(jasperPrint, null));
	}

	public _JasperViewer(JasperPrint jasperPrint, String title, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		super(title);

		initComponents(new _JRViewer(jasperPrint, null, doc_id, entity_id, proj_id, pbl_id, seq_no, printable));
	}

	private void initComponents(Component comp) {
		this.setLayout(new BorderLayout());
		this.setIconImage(FncLookAndFeel.iconSystem);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(comp, BorderLayout.CENTER);
		this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF")).getImage());


		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if(JOptionPane.showConfirmDialog(_JasperViewer.this, "This report is only printable once.\n\nAre you sure you want to close this report?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
					return;
				}
				_JasperViewer.this.dispose();
			}
		});
		//this.pack();
	}

}

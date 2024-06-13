package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.UserInfo;
import components._JInternalFrame;
import interfaces._GUI;

public class UploadFacilitiesPayments extends _JInternalFrame implements _GUI, ActionListener {

	static String title = "Upload Facilites Payment";
	Dimension frameSize = new Dimension(250, 100);
	
	private static JButton btnSave;

	
	public UploadFacilitiesPayments() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public UploadFacilitiesPayments(String title) {
		super(title);
		initGUI(); 
	}

	public UploadFacilitiesPayments(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {

		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JPanel pnlButton = new JPanel(new BorderLayout(5,5));
//			pnlButton.setPreferredSize(new Dimension(30,30));
			panMain.add(pnlButton,BorderLayout.CENTER);
			{
				btnSave = new JButton("Upload Facilities Payment");
				pnlButton.add(btnSave,BorderLayout.CENTER);
				btnSave.setPreferredSize(new Dimension(30,30));
				btnSave.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						pgSelect db = new pgSelect();
							String query = "SELECT sp_upload_pmd_facilities_pmt('"+UserInfo.Alias+"', '"+FncGlobal.connectionPassword+"')";
							db.select(query);
					JOptionPane.showMessageDialog(getContentPane(),"Uploaded Payments","Succes",JOptionPane.INFORMATION_MESSAGE);

					}
				});
			}
		}
	}
}

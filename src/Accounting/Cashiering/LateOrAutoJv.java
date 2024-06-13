package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.modelMBTC_2;

public class LateOrAutoJv extends _JInternalFrame {


	private static final long serialVersionUID = -8523330824696650932L;
	static String title = "Late OR Auto JV";
	Dimension frameSize = new Dimension(250, 100);
	

	
	private static JButton btnSave;

	


	
	
	public LateOrAutoJv() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public LateOrAutoJv(String title) {
		super(title);
		initGUI(); 
	}

	public LateOrAutoJv(String title, boolean resizable, boolean closable, boolean maximizable,
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
				btnSave = new JButton("RUN");
				pnlButton.add(btnSave,BorderLayout.CENTER);
				btnSave.setPreferredSize(new Dimension(30,30));
				btnSave.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						pgSelect db = new pgSelect();
							String query = "SELECT sp_run_or();";
							db.select(query);
					JOptionPane.showMessageDialog(getContentPane(),"Run Successfully","Succes",JOptionPane.INFORMATION_MESSAGE);


						
					}
				});
			}
		
			
		}
		
		
	}
	
	
	

}

package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTextField;

import Database.pgUpdate;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTagLabel;

public class AgentTIN_tagging extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "TIN Tagging";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlSouthCenterb;
	private JPanel pnlAgent;
	private JPanel pnlAgent_a1;
	private JPanel pnlAgent_a2;
	private JPanel pnlAgent_a;
	private JPanel pnlAgent_b;

	private JButton btnSave;
	private JButton btnEdit;
	
	private JLabel lblAgentName;
	private JLabel lblTIN;
	private _JTagLabel tagTIN;
	
	private JXTextField txtTIN;	
	private JXTextField txtName;
	
	private _JLookup lookupAgent;
	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private String entity_id = "";
	private String entity_name = "";
	private String tin_no = "";	

	public AgentTIN_tagging() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AgentTIN_tagging(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AgentTIN_tagging(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(567, 149));
		this.setBounds(0, 0, 567, 149);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(517, 131));

		{ //start of Company

			pnlAgent = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlAgent, BorderLayout.NORTH);			
			pnlAgent.setPreferredSize(new java.awt.Dimension(507, 76));	
			pnlAgent.setBorder(lineBorder);

			{
				pnlAgent_a = new JPanel(new BorderLayout(0, 0));
				pnlAgent.add(pnlAgent_a, BorderLayout.WEST);	
				pnlAgent_a.setPreferredSize(new java.awt.Dimension(221, 71));
				pnlAgent_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

				{
					pnlAgent_a1 = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlAgent_a.add(pnlAgent_a1, BorderLayout.WEST);	
					pnlAgent_a1.setPreferredSize(new java.awt.Dimension(94, 67));
					pnlAgent_a1.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));	

					{
						lblAgentName = new JLabel("Agent Name", JLabel.TRAILING);
						pnlAgent_a1.add(lblAgentName);
						lblAgentName.setBounds(8, 11, 62, 12);
						lblAgentName.setPreferredSize(new java.awt.Dimension(115, 25));
						lblAgentName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}
					{
						lblTIN = new JLabel("TIN  ", JLabel.TRAILING);
						pnlAgent_a1.add(lblTIN);
						lblTIN.setBounds(8, 11, 62, 12);
						lblTIN.setEnabled(false);
						lblTIN.setPreferredSize(new java.awt.Dimension(96, 26));
						lblTIN.setFont(new java.awt.Font("Segoe UI",Font.PLAIN&Font.BOLD,12));
					}						
				}
				{
					pnlAgent_a2 = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlAgent_a.add(pnlAgent_a2, BorderLayout.CENTER);	
					pnlAgent_a2.setPreferredSize(new java.awt.Dimension(110, 116));
					pnlAgent_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
					{
						lookupAgent = new _JLookup(null, "Agent List",0,2);
						pnlAgent_a2.add(lookupAgent);
						lookupAgent.setLookupSQL(getEntity());
						lookupAgent.setReturnColumn(0);
						lookupAgent.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									entity_id = (String) data[0];	
									entity_name = (String) data[1];	
									txtName.setText(entity_name);
									btnEdit.setEnabled(true);
									txtTIN.setText("");
									lblTIN.setEnabled(false);
									txtTIN.setEnabled(false);
									btnSave.setEnabled(false);
									tagTIN.setEnabled(false);	
									if (data[2]==null) {tin_no = null; tagTIN.setTag("No TIN yet");}
									else {tin_no = (String) data[2];txtTIN.setText(tin_no);tagTIN.setTag("");}
								}
							}
						});
					}	
					{
						txtTIN = new JXTextField("");
						pnlAgent_a2.add(txtTIN);
						txtTIN.setEnabled(false);	
						txtTIN.setEditable(true);
						txtTIN.setBounds(120, 25, 300, 22);	
						txtTIN.setHorizontalAlignment(JTextField.CENTER);	
					}						
				}					
			}
			{
				pnlAgent_b = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlAgent.add(pnlAgent_b, BorderLayout.CENTER);	
				pnlAgent_b.setPreferredSize(new java.awt.Dimension(337, 74));	
				pnlAgent_b.setBorder(BorderFactory.createEmptyBorder(5, 0, 7, 5));

				{
					txtName = new JXTextField("");
					pnlAgent_b.add(txtName);
					txtName.setEnabled(false);	
					txtName.setEditable(false);
					txtName.setBounds(120, 25, 300, 22);	
					txtName.setHorizontalAlignment(JTextField.CENTER);						
				}	
				{
					tagTIN = new _JTagLabel("[ ]");
					pnlAgent_b.add(tagTIN);
					tagTIN.setBounds(209, 27, 700, 22);
					tagTIN.setEnabled(false);	
					tagTIN.setPreferredSize(new java.awt.Dimension(314, 32));
				}
			}			

		} //end of Company
		{			
			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlMain.add(pnlSouthCenterb, BorderLayout.CENTER);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{				
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							lblTIN.setEnabled(true);
							txtTIN.setEnabled(true);	
							btnEdit.setEnabled(false);
							btnSave.setEnabled(true);
							tagTIN.setEnabled(true);	
						}
					});
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							saveTIN();
						}
					});
				}
			}
		}
	}

	
	//lookup, sql
	public String getEntity(){

		String sql = 
			"select a.agent_id as \"Entity ID\", trim(b.entity_name) as \"Entity Name\", c.tin_no as \"TIN\" \r\n" + 
			"\r\n" + 
			"from mf_sellingagent_info a\r\n" + 
			"left join rf_entity b on a.agent_id = b.entity_id \r\n" + 
			"left join rf_entity_id_no c on a.agent_id = c.entity_id \r\n" + 
			"\r\n" + 
			"order by b.entity_name" + 
			"" ;		

		return sql;
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if(e.getActionCommand().equals("Save")){ 
		}
	}

	public void mouseClicked(MouseEvent evt) {	
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


	//save
	public void saveTIN(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Update agent's TIN.?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	
			String sqlDetail = "";
			
			if (tin_no==null)
			{
				sqlDetail = 
					"insert into rf_entity_id_no (entity_id,tin_no,status_id,created_by,date_created) \n" +
					"values('"+entity_id+"','"+txtTIN.getText().trim()+"','A','"+UserInfo.EmployeeCode+"','"+Calendar.getInstance().getTime()+"') \n" ;	
			}
			else
			{
				sqlDetail = 
					"update rf_entity_id_no set \n" +
					"tin_no = "+txtTIN.getText().trim()+"  \n" +  	//1
					"where entity_id = '"+entity_id+"'  \n" ;	
			}

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"Agent's TIN updated.","Information",JOptionPane.INFORMATION_MESSAGE);
			btnEdit.setEnabled(true);
			lblTIN.setEnabled(false);
			txtTIN.setEnabled(false);
			btnSave.setEnabled(false);
			tagTIN.setEnabled(false);
			tagTIN.setTag("");
		}

	}


}

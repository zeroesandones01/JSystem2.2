package Dialogs;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import DateChooser._JDateChooser;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JXTextField;

public class dlgDM_VenueLocation extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(500, 130);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlVenueLocation;
	private JLabel lblVenueLocation;

	private JPanel pnlLookups;
	private _JLookup lookupVenueLocation;
	private _JXTextField txtVenueLocation;

	private JPanel pnlDate;
	private JLabel lblDate;
	private _JDateChooser date;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;

	private String venue_id;
	private String venue_name;
	private Date venue_date;

	public dlgDM_VenueLocation() {
		initGUI();
	}

	public dlgDM_VenueLocation(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_VenueLocation(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_VenueLocation(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_VenueLocation(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_VenueLocation(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_VenueLocation(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_VenueLocation(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_VenueLocation(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_VenueLocation(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_VenueLocation(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_VenueLocation(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_VenueLocation(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_VenueLocation(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_VenueLocation(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_VenueLocation(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlVenueLocation = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlVenueLocation);
					{
						lblVenueLocation = new JLabel("Venue");
						pnlVenueLocation.add(lblVenueLocation, BorderLayout.WEST);
						lblVenueLocation.setPreferredSize(new Dimension(50, 37));
					}
					{
						pnlLookups = new JPanel(new BorderLayout(3, 3));
						pnlVenueLocation.add(pnlLookups, BorderLayout.CENTER);
						{
							lookupVenueLocation = new _JLookup(null, "Insurance Company");
							pnlLookups.add(lookupVenueLocation, BorderLayout.WEST);
							lookupVenueLocation.setReturnColumn(0);
							lookupVenueLocation.setLookupSQL(getSQL());
							lookupVenueLocation.setPrompt("ID");
							lookupVenueLocation.setPreferredSize(new Dimension(100, 0));
							lookupVenueLocation.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String venue_name = (String) data[1];

										txtVenueLocation.setText(venue_name);
									}
								}
							});
						}
						{
							txtVenueLocation = new _JXTextField("Venue Name");
							pnlLookups.add(txtVenueLocation, BorderLayout.CENTER);
						}
					}
				}
				{
					pnlDate = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDate);
					{
						lblDate = new JLabel("Date");
						pnlDate.add(lblDate, BorderLayout.WEST);
						lblDate.setPreferredSize(new Dimension(50, 37));
					}
					{
						JPanel panel = new JPanel(new BorderLayout());
						pnlDate.add(panel, BorderLayout.CENTER);
						{
							date = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							panel.add(date, BorderLayout.WEST);
							date.setPreferredSize(new Dimension(120, 37));
						}
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(205, 30));
					{
						btnOK = new JButton("OK");
						pnlNavigation.add(btnOK);
						btnOK.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		this.pack();
	}//XXX END OF INIT GUI

	private String getSQL() {
		String SQL = "select trim(branch_id) as \"ID\", trim(branch_name) as \"Venue Name\", trim(branch_alias) as \"Alias\"\n" + 
				"from mf_office_branch\n" + 
				"where status_id = 'A';";
		return SQL;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setVenueID(lookupVenueLocation.getValue());
			setVenueName(txtVenueLocation.getText());
			setVenueDate(date.getDate());
			dispose();
		}
		if(action.equals("Cancel")){
			setVenueID(null);
			setVenueName(null);
			setVenueDate(null);
			dispose();
		}
		if(action.equals("Escape")){
			setVenueID(null);
			setVenueName(null);
			setVenueDate(null);
			dispose();
		}
	}

	/**
	 * @return the venue_id
	 */
	public String getVenueID() {
		return venue_id;
	}

	/**
	 * @param venue_id the venue_id to set
	 */
	public void setVenueID(String venue_id) {
		this.venue_id = venue_id;
	}

	/**
	 * @return the venue_name
	 */
	public String getVenueName() {
		return venue_name;
	}

	/**
	 * @param venue_name the venue_name to set
	 */
	public void setVenueName(String venue_name) {
		this.venue_name = venue_name;
	}

	/**
	 * @return the venue_date
	 */
	public Date getVenueDate() {
		return venue_date;
	}

	/**
	 * @param venue_date the venue_date to set
	 */
	public void setVenueDate(Date venue_date) {
		this.venue_date = venue_date;
	}

}

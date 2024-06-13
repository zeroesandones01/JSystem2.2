package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class waterReadingAdjustment extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -487159299301892349L;
	static String title = "Water Reading";
	Dimension frameSize = new Dimension(600, 600);

	private waterReading_panel1 panel1;
	private waterReading_panel2 panel2; 
	
	private _JLookup txtCoID;
	private _JLookup txtProID; 
	private _JLookup txtPhaseID;
	
	private JTextField txtCo;
	private JTextField txtPro;
	private JTextField txtPhase;
	
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	
	public waterReadingAdjustment() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public waterReadingAdjustment(String title) {
		super(title);
		initGUI(); 
	}

	public waterReadingAdjustment(String title, boolean resizable, boolean closable, boolean maximizable,
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
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 110));
				{
					{
						JXPanel panDetails = new JXPanel(new GridLayout(3, 1, 5, 5)); 
						panPage.add(panDetails, BorderLayout.LINE_START);
						panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						panDetails.setPreferredSize(new Dimension(450, 0));
						{
							{
								JXPanel panCo = new JXPanel(new BorderLayout(5, 5)); 
								panDetails.add(panCo); 
								{
									{
										JXPanel panCoLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panCo.add(panCoLabel, BorderLayout.LINE_START);
										panCoLabel.setPreferredSize(new Dimension(150, 0));
										{
											{
												JLabel lblCo = new JLabel("Company"); 
												panCoLabel.add(lblCo);
												lblCo.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												txtCoID = new _JLookup(); 
												panCoLabel.add(txtCoID); 
												txtCoID.setLookupSQL("");
												txtCoID.setReturnColumn(0);
												txtCoID.setHorizontalAlignment(JTextField.CENTER);
												txtCoID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {

													}
												});
											}
										}
									}
									{
										txtCo = new JTextField(""); 
										panCo.add(txtCo, BorderLayout.CENTER); 
										txtCo.setHorizontalAlignment(JTextField.CENTER);
										txtCo.setEditable(false);
									}
								}
							}
							{
								JXPanel panPro = new JXPanel(new BorderLayout(5, 5)); 
								panDetails.add(panPro); 
								{
									{
										JXPanel panProLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panPro.add(panProLabel, BorderLayout.LINE_START);
										panProLabel.setPreferredSize(new Dimension(150, 0));
										{
											{
												JLabel lblPro = new JLabel("Project"); 
												panProLabel.add(lblPro);
												lblPro.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												txtProID = new _JLookup(); 
												panProLabel.add(txtProID); 
												txtProID.setLookupSQL("");
												txtProID.setReturnColumn(0);
												txtProID.setHorizontalAlignment(JTextField.CENTER);
												txtProID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {

													}
												});
											}
										}
									}
									{
										txtPro = new JTextField(""); 
										panPro.add(txtPro, BorderLayout.CENTER); 
										txtPro.setHorizontalAlignment(JTextField.CENTER);
										txtPro.setEditable(false);
									}
								}
							}
							{
								JXPanel panPhase = new JXPanel(new BorderLayout(5, 5)); 
								panDetails.add(panPhase); 
								{
									{
										JXPanel panPhaseLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panPhase.add(panPhaseLabel, BorderLayout.LINE_START);
										panPhaseLabel.setPreferredSize(new Dimension(150, 0));
										{
											{
												JLabel lblPhase = new JLabel("Phase"); 
												panPhaseLabel.add(lblPhase);
												lblPhase.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												txtPhaseID = new _JLookup(); 
												panPhaseLabel.add(txtPhaseID); 
												txtPhaseID.setLookupSQL("");
												txtPhaseID.setReturnColumn(0);
												txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
												txtPhaseID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {

													}
												});
											}
										}
									}
									{
										txtPhase = new JTextField(""); 
										panPhase.add(txtPhase, BorderLayout.CENTER); 
										txtPhase.setHorizontalAlignment(JTextField.CENTER);
										txtPhase.setEditable(false);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXEditorPane;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.UserInfo;
import components.JButton_Home;
import interfaces._GUI;

public class pnlAnnouncement extends JXPanel implements _GUI {
	private Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	private JXPanel pnlText;
	static JXPanel pnlTextArea;
	private JScrollPane scTxtArea;
	private JButton_Home btnRefresh;
	private JButton_Home btnLeft;
	private JButton_Home btnRight;
	private JXEditorPane txtarea;
	
	

	public pnlAnnouncement() {
		initGUI();
	}

	public pnlAnnouncement(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlAnnouncement(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlAnnouncement(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout(1,1));
		this.setOpaque(true);
		this.setBorder(empty);
		this.setBackground(new Color(0,0,0,0));
		{
			

			pnlText = new JXPanel(new BorderLayout());
			this.add(pnlText, BorderLayout.NORTH);
			pnlText.setPreferredSize(new Dimension(400, 200));
			pnlText.setOpaque(false);
			pnlText.setBorder(empty);

			{


				pnlTextArea = new JXPanel(new BorderLayout(5, 5));
				pnlText.add(pnlTextArea, BorderLayout.CENTER);
				pnlTextArea.setPreferredSize(new Dimension(20, 200));

				pnlTextArea.setBorder(components._JTBorderFactory.createTitleBorder("- \t Announcement"));
				pnlTextArea.setOpaque(false);


				{

/*

					scTxtArea = new JScrollPane();
					pnlTextArea.add(scTxtArea,BorderLayout.CENTER);
					//scTxtArea.setBorder(title);
					scTxtArea.setPreferredSize(new Dimension(400, 200));
					scTxtArea.setOpaque(false);
					//scTxtArea.setBackground(new Color(0,0,0,0));
					scTxtArea.setBorder(empty);
					{
						txtarea = new JXEditorPane();
						scTxtArea.add(txtarea);
						scTxtArea.setViewportView(txtarea);
						//txtarea.setLineWrap(true);
						//txtarea.setWrapStyleWord(true);
						txtarea.setContentType("text/html");
						txtarea.setAutoscrolls(true);
						scTxtArea.setOpaque(false);
						txtarea.setText(UserInfo.Announcement);	
						//txtarea.setBackground(new Color(0,0,0,0));
						txtarea.setEditable(UserInfo.ADMIN);
						txtarea.setMargin(new Insets(0, 0, 0, 50));
					}*/
					
					txtarea = new JXEditorPane();
					pnlTextArea.add(txtarea, BorderLayout.CENTER);
					txtarea.setEditable(false);
					txtarea.setContentType("text/html");
					txtarea.setText(UserInfo.Announcement);	
					txtarea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
					txtarea.setMargin(new Insets(0, 0, 0, 50));
					txtarea.setEditable(UserInfo.ADMIN);

					{
						scTxtArea = new JScrollPane(txtarea);
						pnlTextArea.add(scTxtArea, BorderLayout.CENTER);
					}

				}
				JXPanel pnlS = new JXPanel(new BorderLayout(5,5));
				pnlTextArea.add(pnlS, BorderLayout.SOUTH);
				pnlS.setPreferredSize(new Dimension(0, 25));
				pnlS.setOpaque(false);
				{

					JXPanel pnlButton = new JXPanel(new GridLayout(1,2,3,3));
					pnlS.add(pnlButton, BorderLayout.CENTER);
					pnlButton.setPreferredSize(new Dimension(0, 25));
					pnlButton.setOpaque(false);
					{
						{
							JXPanel pnlButtonWest = new  JXPanel(new GridLayout(1, 4, 3, 3));
							pnlButton.add(pnlButtonWest,BorderLayout.EAST);
							pnlButtonWest.setPreferredSize(new Dimension(400, 0));
							pnlButtonWest.setOpaque(false);
							{

								btnRefresh = new JButton_Home(new ImageIcon(FncLookAndFeel.refreshbutton));
								pnlButtonWest.add(btnRefresh);
								btnRefresh.setActionCommand("Refresh");
								btnRefresh.setOpaque(false);
								btnRefresh.setContentAreaFilled(false);
								btnRefresh.setBorderPainted(false);
								btnRefresh.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										refresh();
										
									}
								});
							}
							{
								JButton_Home btnSave = new JButton_Home(new ImageIcon(FncLookAndFeel.savebutton));
								pnlButtonWest.add(btnSave);
								btnSave.setActionCommand("Save");
								btnSave.setVisible(UserInfo.ADMIN);
								btnSave.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										
										pgUpdate db = new pgUpdate();
										pgSelect dbs = new pgSelect();
										
										dbs.select("select case when announcement = '"+txtarea.getText()+"' then true else false end from rf_announcement where announcement = '"+txtarea.getText().replaceAll("'", "\\'")+"' ");
										
										
										if (dbs.isNotNull()) {
											Boolean ifexisting = (Boolean)dbs.Result[0][0];
											if (ifexisting) {
												
													JOptionPane.showMessageDialog(null, "Already Existing","Save",JOptionPane.WARNING_MESSAGE);
													return ;
													
											}
										}
										
										
										
										
										
										int response = JOptionPane.showConfirmDialog(FncGlobal.homeMDI,"Are you sure you want to save this announcement  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
										if (response == JOptionPane.YES_OPTION) {
											db.executeUpdate("INSERT INTO rf_announcement(\n" + 
													"            rec_id, announcement, annoucement_type, created_by, date_created, \n" + 
													"            status_id)\n" + 
													"    VALUES ((select MAX(rec_id) +1 from rf_announcement), '"+txtarea.getText().replaceAll("'", "\\'")+"', null, '"+UserInfo.EmployeeCode+"', current_date, 'A');;", false, true);
											
											refresh();
											
										}

										
									}
								});
							}
							{
								JLabel lbl = new JLabel("");
								lbl.setVisible(false);

							}

							{
								JLabel lbl = new JLabel("");
								pnlButtonWest.add(lbl);
								lbl.setVisible(false);
							}
						}

						JXPanel pnlButtonEast = new JXPanel(new GridLayout(1,4,3,3));
						pnlButton.add(pnlButtonEast);
						pnlButtonEast.setOpaque(false);
						pnlButtonEast.setVisible(true);
						{

							{
								JLabel lbl = new JLabel("");
								pnlButtonEast.add(lbl);
								lbl.setVisible(false);

							}

							{
								JLabel lbl = new JLabel("");
								pnlButtonEast.add(lbl);
								lbl.setVisible(false);
							}
							{

								btnLeft = new JButton_Home(new ImageIcon(FncLookAndFeel.lefthbutton));
								pnlButtonEast.add(btnLeft);
								btnLeft.setActionCommand("Left");
								btnLeft.setOpaque(false);
								btnLeft.setContentAreaFilled(false);
								btnLeft.setBorderPainted(false);
								btnLeft.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										pgSelect db = new pgSelect();

										db.select("select * from get_navigation('"+UserInfo.rec_id+"','prev');");
										if (db.isNotNull()) {
																				
											
											Integer prev = (Integer) db.Result[0][1];
																			
											
											
											if (!(prev == null)) {
												UserInfo.rec_id = (Integer) db.Result[0][3];
												txtarea.setText(db.Result[0][0].toString());
												
											}
											txtarea.repaint();
										}



									}
								});

							}
							{
								btnRight = new JButton_Home(new ImageIcon(FncLookAndFeel.rightbutton));
								pnlButtonEast.add(btnRight);
								btnRight.setActionCommand("Right");
								btnRight.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										pgSelect db = new pgSelect();


										db.select("select * from get_navigation('"+UserInfo.rec_id+"','next');");
										if (db.isNotNull()) {
											Integer next = (Integer) db.Result[0][2];
											
											if (!(next == null)) {
												UserInfo.rec_id = (Integer) db.Result[0][3];
												txtarea.setText(db.Result[0][0].toString());
												
											}

											txtarea.repaint();
										}
									}
								});
							}
						}
					}
				}

			}
		
			
		}
	}
	
	private void refresh(){
		
		pgSelect db = new pgSelect();


		db.select("select announcement from rf_announcement order by rec_id desc limit 1;");

		if (db.isNotNull()) {

			txtarea.setText((String) db.Result[0][0]);
			txtarea.repaint();
		}
	}
}

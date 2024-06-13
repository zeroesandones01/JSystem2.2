/**
 * 
 */
package Dialogs;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Buyers.ClientServicing.pnlClientInformation;
import FormattedTextField.NumericPlainDocument;
import components._JDialog;

/**
 * @author JLF
 */
public class dlg_AddContactInfo extends _JDialog implements ActionListener, _GUI {

	private static final long serialVersionUID = 5135230500405400534L;

	private Dimension SIZE = new Dimension(200, 400);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JLabel lblAddTo;
	private JPanel pnlNorthCenter;
	private JComboBox cmbAddTo;
	private JButton btnAdd;

	private JScrollPane scrollCenter;
	private JPanel pnlCenter;
	private GroupLayout layout;
	private GroupLayout.ParallelGroup parallel;
	private GroupLayout.SequentialGroup sequential;

	private JPanel pnlSouth;
	private JButton btnOK;
	private JButton btnCANCEL;

	private ImageIcon ADD_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/add-icon.png"));
	private ImageIcon CLOSE_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/closeTabButton.png"));

	private Integer addCount = 0;
	private Map<String, String>mapAdd = new HashMap<String, String>();
	private pnlClientInformation pnlCI;
	
	private Integer max_length;

	public dlg_AddContactInfo() {
		initGUI();
	}

	public dlg_AddContactInfo(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_AddContactInfo(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_AddContactInfo(Window owner, String entity_name, String entity_id,
			Integer pbl_id, Integer seq_no, List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_AddContactInfo(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_AddContactInfo(Frame owner, String title, pnlClientInformation pnlCI) {
		super(owner, title);
		this.pnlCI = pnlCI;
		initGUI();
	}

	public dlg_AddContactInfo(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_AddContactInfo(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_AddContactInfo(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_AddContactInfo(Window owner, String title, pnlClientInformation pnlCI) {
		super(owner, title);
		this.pnlCI = pnlCI;
		initGUI();
	}

	public dlg_AddContactInfo(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_AddContactInfo(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_AddContactInfo(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_AddContactInfo(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_AddContactInfo(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_AddContactInfo(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(SIZE);

		this.getRootPane().registerKeyboardAction(this, "Add", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(180, 40));
				pnlNorth.setLayout(new BorderLayout(5, 5));
				{
					lblAddTo = new JLabel("Add To");
					pnlNorth.add(lblAddTo, BorderLayout.NORTH);
				}
				{
					pnlNorthCenter = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						cmbAddTo = new JComboBox(new String[] {"Telephone No.", "Cellphone No.", "Fax No.", "Email", "Facebook Acct"}); 
						pnlNorthCenter.add(cmbAddTo, BorderLayout.CENTER);
						//cmbAddTo.setSelectedIndex(0);
						cmbAddTo.setSelectedItem(null);
						cmbAddTo.addItemListener(new ItemListener() {

							@Override
							public void itemStateChanged(ItemEvent e) {
								int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
								displayContactNo(pnlCI.setContactNo(selected_index), selected_index);
								
							}
						});
					}
					{
						btnAdd = new JButton();
						pnlNorthCenter.add(btnAdd, BorderLayout.EAST);
						btnAdd.setActionCommand("Add");
						btnAdd.setBorderPainted(false); 
						btnAdd.setContentAreaFilled(false); 
						btnAdd.setOpaque(false);
						btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

						btnAdd.setRolloverIcon(ADD_ICON);
						btnAdd.setRolloverEnabled(true);
						btnAdd.setIcon(ADD_ICON);

						btnAdd.setBorder(null);
						btnAdd.addActionListener(this);
					}
				}
			}
			{
				scrollCenter = new JScrollPane();
				pnlMain.add(scrollCenter, BorderLayout.CENTER);

				pnlCenter = new JPanel();
				scrollCenter.setViewportView(pnlCenter);

				layout = new GroupLayout(pnlCenter);
				pnlCenter.setLayout(layout);

				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);

				parallel = layout.createParallelGroup();
				layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(parallel));

				sequential = layout.createSequentialGroup();
				layout.setVerticalGroup(sequential);

				pnlCenter.setBorder(lineBorder);

			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(180, 30));
				{
					btnOK = new JButton("OK");
					pnlSouth.add(btnOK);
					btnOK.setActionCommand("OK");
					btnOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btnOK.addActionListener(this);
				}
				{
					btnCANCEL = new JButton("CANCEL");
					pnlSouth.add(btnCANCEL);
					btnCANCEL.setActionCommand("CANCEL");
					btnCANCEL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					btnCANCEL.addActionListener(this);
				}
			}
		}
		//displayContactNo(pnlCI.setContactNo(cmbAddTo.getSelectedIndex()));
		this.pack();
	}//XXX END OF INIT GUI


	private void addTo(JTextField fields, JButton btnClose){
		if(pnlCenter.getComponentCount() == 0){
			layout = new GroupLayout(pnlCenter);
			pnlCenter.setLayout(layout);

			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);

			parallel = layout.createParallelGroup();
			layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(parallel));

			sequential = layout.createSequentialGroup();
			layout.setVerticalGroup(sequential);
		}

		SequentialGroup sGroup = layout.createSequentialGroup();
		sGroup.addComponent(fields).addComponent(btnClose);

		ParallelGroup pGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pGroup.addComponent(fields).addComponent(btnClose, GroupLayout.Alignment.CENTER);

		/*ParallelGroup pGroup2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pGroup2.addGroup(pGroup);*/

		parallel.addGroup(GroupLayout.Alignment.CENTER, sGroup);
		sequential.addGroup(true, pGroup);

		layout.linkSize(SwingConstants.HORIZONTAL, btnClose, btnClose);

		fields.requestFocus();
		//fields.setText(text);
	}

	//FOR LOADING
	private void setAddTo(JTextField fields, JButton btnClose, String text){

		if(pnlCenter.getComponentCount() == 0){
			layout = new GroupLayout(pnlCenter);
			pnlCenter.setLayout(layout);

			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);

			parallel = layout.createParallelGroup();
			layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(parallel));

			sequential = layout.createSequentialGroup();
			layout.setVerticalGroup(sequential);
		}

		SequentialGroup sGroup = layout.createSequentialGroup();
		sGroup.addComponent(fields).addComponent(btnClose);

		ParallelGroup pGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pGroup.addComponent(fields).addComponent(btnClose, GroupLayout.Alignment.CENTER);

		/*ParallelGroup pGroup2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pGroup2.addGroup(pGroup);*/

		parallel.addGroup(GroupLayout.Alignment.CENTER, sGroup);
		sequential.addGroup(true, pGroup);

		layout.linkSize(SwingConstants.HORIZONTAL, btnClose, btnClose);

		fields.requestFocus();
		fields.setText(text.trim());

	}

	public void displayContactNo(ArrayList<String> listContactInfo, Integer selected_index){

		if(pnlCenter.getComponentCount() > 0){
			pnlCenter.removeAll();
			pnlCenter.repaint();
		}

		int y = 0;
		
		for(int x= 0; x<listContactInfo.size(); x++){

			y++;
			
			String contact_no = listContactInfo.get(x);
			
			final JTextField fields = new JTextField();

			fields.setName(Integer.toString(y));
			
			/*if(selected_index == 0 || selected_index == 1){
				fields.setDocument(new NumericPlainDocument(new DecimalFormat("#")));
			}*/
			
			fields.addKeyListener(new KeyAdapter() {
				
				public void keyTyped(KeyEvent e){
					String contact_info = ((JTextField) e.getSource()).getText();
					
					if(cmbAddTo.getSelectedIndex() == 0){
						if(contact_info.length() == 20){
							e.consume();
						}
					}
					if(cmbAddTo.getSelectedIndex() == 1){
						/*if(contact_info.length() == 11){
							e.consume();
						}*/
					}
					if(cmbAddTo.getSelectedIndex() == 2){
						if(contact_info.length() == 50){
							e.consume();
						}
					}
					if(cmbAddTo.getSelectedIndex() == 3){
						if(contact_info.length() == 70){
							e.consume();
						}
					}
					if(cmbAddTo.getSelectedIndex() == 4){
						if(contact_info.length() == 50){
							e.consume();
						}
					}
				}
			});
			
			
			fields.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					/*JFormattedTextField field = (JFormattedTextField) e.getSource();
						field.select(0, 0);*/

					JTextField field = (JTextField) e.getSource();
					field.select(0, 0);
				}
				public void focusGained(FocusEvent e) {
					/*JFormattedTextField field = (JFormattedTextField) e.getSource();
						field.selectAll();*/

					JTextField field = (JTextField) e.getSource();
					field.selectAll();
				}
			});

			JButton btnClose = new JButton();
			btnClose.setName(Integer.toString(y));
			btnClose.setFocusable(false);

			btnClose.setBorderPainted(false); 
			btnClose.setContentAreaFilled(false); 
			btnClose.setOpaque(false);
			btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			btnClose.setRolloverIcon(CLOSE_ICON);
			btnClose.setRolloverEnabled(true);
			btnClose.setIcon(CLOSE_ICON);

			btnClose.setBorder(null);

			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					JButton button = (JButton) e.getSource();

					Map<Integer, List<Component>> mapComponents = new HashMap<Integer, List<Component>>();
					for(Component comp : pnlCenter.getComponents()){
						Integer key = Integer.parseInt(comp.getName());

						if(!mapComponents.containsKey(key)){
							List list = new ArrayList<Component>();
							list.add(comp);
							mapComponents.put(key, list);
						}else{
							List list = mapComponents.get(key);
							list.add(comp);
							mapComponents.put(key, list);
						}

						pnlCenter.remove(comp);
					}

					pnlCenter.repaint();
					pnlCenter.revalidate();

					for(Entry<Integer, List<Component>> entry : mapComponents.entrySet()){
						if(entry.getKey() != Integer.parseInt(button.getName())){
							List list = entry.getValue();
							JTextField fields = (JTextField) list.get(0);
							JButton btnClose = (JButton) list.get(1);


							setAddTo(fields, btnClose, fields.getText());
						}
					}
				}
			});
			setAddTo(fields, btnClose, contact_no);
		}
	}

	public String exceptBy(){

		return mapAdd.get(cmbAddTo.getSelectedItem().toString());

	}

	public List<String> getContactNo() {
		List<String> list = new ArrayList<String>();
		for(Component comp : pnlCenter.getComponents()){
			if(comp instanceof JTextField){
				JTextField field = (JTextField) comp;
				if(list.contains(field.getText()) == false){
					list.add(""+field.getText()+"");
				}
			}
		}
		
		System.out.printf("Display ArrayList for Contact No: %s%n", list);
		return list;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		int selected_item = cmbAddTo.getSelectedIndex();

		if(actionCommand.equals("Add")){// Add
			if(cmbAddTo.getSelectedItem() != null){
				addCount++;

				//JFormattedTextField fields = new JFormattedTextField(/*NumberFormat.getIntegerInstance()*/);
				//JFormattedTextField fields = new JFormattedTextField(NumberRenderer.getIntegerRenderer());
				//fields.setValue(excCount);
				final JTextField fields = new JTextField();

				fields.setName(Integer.toString(addCount));
				
				/*if(selected_item == 0 || selected_item == 1){
					fields.setDocument(new NumericPlainDocument(new DecimalFormat("#")));
				}*/
				
				fields.addKeyListener(new KeyAdapter() {
					
					public void keyTyped(KeyEvent e){
						String contact_info = ((JTextField) e.getSource()).getText();
						
						if(cmbAddTo.getSelectedIndex() == 0){
							if(contact_info.length() == 20){
								e.consume();
							}
						}
						
						if(cmbAddTo.getSelectedIndex() == 1){
						/*	if(contact_info.length() == 11){
								e.consume();
							}*/
						}
						
						if(cmbAddTo.getSelectedIndex() == 2){
							if(contact_info.length() == 50){
								e.consume();
							}
						}
						
						if(cmbAddTo.getSelectedIndex() == 3){
							if(contact_info.length() == 50){
								e.consume();
							}
						}
						
						if(cmbAddTo.getSelectedIndex() == 4){
							if(contact_info.length() == 50){
								e.consume();
							}
						}
					}
				});
				
				fields.addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {
						/*JFormattedTextField field = (JFormattedTextField) e.getSource();
						field.select(0, 0);*/

						JTextField field = (JTextField) e.getSource();
						field.select(0, 0);
						
						if(field.getText().length()< 11 && cmbAddTo.getSelectedIndex() == 1){
							JOptionPane.showMessageDialog(null, "Insuficient No. of Characters", cmbAddTo.getSelectedItem().toString(), JOptionPane.WARNING_MESSAGE);
						}
					}
					public void focusGained(FocusEvent e) {
						/*JFormattedTextField field = (JFormattedTextField) e.getSource();
						field.selectAll();*/

						JTextField field = (JTextField) e.getSource();
						field.selectAll();
					}
				});

				JButton btnClose = new JButton();
				btnClose.setName(Integer.toString(addCount));
				btnClose.setFocusable(false);

				btnClose.setBorderPainted(false); 
				btnClose.setContentAreaFilled(false); 
				btnClose.setOpaque(false);
				btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				btnClose.setRolloverIcon(CLOSE_ICON);
				btnClose.setRolloverEnabled(true);
				btnClose.setIcon(CLOSE_ICON);

				btnClose.setBorder(null);

				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						JButton button = (JButton) e.getSource();

						Map<Integer, List<Component>> mapComponents = new HashMap<Integer, List<Component>>();
						for(Component comp : pnlCenter.getComponents()){
							Integer key = Integer.parseInt(comp.getName());

							if(!mapComponents.containsKey(key)){
								List list = new ArrayList<Component>();
								list.add(comp);
								mapComponents.put(key, list);
							}else{
								List list = mapComponents.get(key);
								list.add(comp);
								mapComponents.put(key, list);
							}

							pnlCenter.remove(comp);
						}

						pnlCenter.repaint();
						pnlCenter.revalidate();

						for(Entry<Integer, List<Component>> entry : mapComponents.entrySet()){
							if(entry.getKey() != Integer.parseInt(button.getName())){
								List list = entry.getValue();
								JTextField fields = (JTextField) list.get(0);
								JButton btnClose = (JButton) list.get(1);

								addTo(fields, btnClose);
							}
						}
					}
				});
				addTo(fields, btnClose);
			}else{
				JOptionPane.showConfirmDialog(dlg_AddContactInfo.this, "Select Item to Add entry", "Add", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("OK")){
			if(selected_item == 0){
				pnlCI.setAddedContactInfo(getContactNo(), selected_item);
				dispose();
			}
			if(selected_item == 1){
				pnlCI.setAddedContactInfo(getContactNo(), selected_item);
				dispose();
			}
			if(selected_item == 2){
				pnlCI.setAddedContactInfo(getContactNo(), selected_item);
				dispose();
			}
			if(selected_item == 3){
				pnlCI.setAddedContactInfo(getContactNo(), selected_item);
				dispose();
			}
			if(selected_item == 4){
				pnlCI.setAddedContactInfo(getContactNo(), selected_item);
				dispose();
			}
		}

		if(actionCommand.equals("CANCEL")){
			pnlCenter.removeAll();
			pnlCenter.repaint();
			dispose();
		}
	}
}

package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Accounting.FixedAssets.panelAssetInformation;
import interfaces._GUI;

public class dlg_todo_assetmonitoring extends JDialog implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public static Dimension size = new Dimension(500, 300);
	public static String title ="Select to do";
	private JPanel pnlmain;
	private JRadioButton rbnewItem;
	private JRadioButton rbreplacement;
	private JRadioButton rbdummy;
	private JPanel pnlcenter;
	private JPanel pnlsouth;
	private JPanel pnloption;
	
	public static JButton btnok;
	public static  panelAssetInformation  panelAssetInformation;
	public static String selected="";

	public dlg_todo_assetmonitoring() {
		initGUI();
	}

	public dlg_todo_assetmonitoring(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_todo_assetmonitoring(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}
	public dlg_todo_assetmonitoring(Frame jpanel, panelAssetInformation pnl, String title) {
		super(jpanel, title);
		
		initGUI();
	}
	
	@Override
	public void initGUI() {
		{
			
			pnlmain= new JPanel(new BorderLayout(5,5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(_EMPTY_BORDER);
			this.setTitle(title);
			{
				pnlcenter= new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				{
					pnloption= new JPanel(new GridLayout(3, 1, 3, 3));
					pnlcenter.add(pnloption);
					pnloption.setBorder(_LINE_BORDER);
					{
						rbnewItem = new JRadioButton("New Item");
						pnloption.add(rbnewItem);
						rbnewItem.setToolTipText("For newly acquired assets.");
						rbnewItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								if(rbnewItem.isSelected()){
									panelAssetInformation.cmbremarks.setSelectedIndex(0);
									selected="New Item";
									rbreplacement.setSelected(false);
									rbdummy.setSelected(false);
								}
							}
						});
					}
					{
						rbreplacement= new JRadioButton("Replacement");
						pnloption.add(rbreplacement);
						rbreplacement.setToolTipText("For old assets replacement.");
						rbreplacement.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								if(rbreplacement.isSelected()){
									panelAssetInformation.cmbremarks.setEditable(true);
									panelAssetInformation.cmbremarks.setSelectedItem(null);
									selected="Replacement";
									rbnewItem.setSelected(false);
									rbdummy.setSelected(false);
								}
							}
						});
					}
					{
						rbdummy= new JRadioButton("Dummy");
						pnloption.add(rbdummy);
						rbdummy.setToolTipText("Use as dummy asset.");
						rbdummy.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								if(rbdummy.isSelected()){
									panelAssetInformation.cmbremarks.setSelectedIndex(3);
									selected="Dummy";
									rbnewItem.setSelected(false);
									rbreplacement.setSelected(false);
								}
							}
						});
					}
				}
			}
			{
				pnlsouth = new JPanel();
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				
				{
					btnok = new JButton("OK");
					pnlsouth.add(btnok);
					//btnok.setPreferredSize(new Dimension(30, 0));
					btnok.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(selected == ""){
								
								JOptionPane.showMessageDialog(null, "Please select to do process.");
								
							}else if(selected== "Replacement"){
								
								//JOptionPane.showMessageDialog(null, "Please select Replacement at remark2  \n"+" and select the asset to be replace.");
								//JOptionPane.showMessageDialog(null, "Please select asset to be replaced.");
								panelAssetInformation.cmbremarks.setSelectedIndex(1);
								//panelAssetInformation.cmbremarks.setEnabled(true);
								//panelAssetInformation.cmbremarks.setEditable(true);
								dispose();
								
							}else{
								
								dispose();
							}
						}
					});
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}

	

}

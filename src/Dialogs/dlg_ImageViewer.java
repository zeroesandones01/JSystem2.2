/**
 * 
 */
package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Buyers.ClientServicing.pnlClientInformation;
import Functions.FncImageFileChooser;
import components._JDialog;
import interfaces._GUI;

/**
 * @author John Lester Fatallo
 */
public class dlg_ImageViewer extends _JDialog implements _GUI {
	
	private static final long serialVersionUID = 6803380974114813796L;
	
	private Dimension SIZE = new Dimension(500, 500);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	
	private JPanel pnlCenter;
	private byte[] image = null;
	private FncImageFileChooser lblImage;
	private JScrollPane scrollImage;
	private Integer width;
	private Integer height;
	
	public dlg_ImageViewer(byte[] image) {
		this.image = image;
		initGUI();
	}
	
	public dlg_ImageViewer() {
		initGUI();
	}

	public dlg_ImageViewer(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_ImageViewer(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_ImageViewer(Window owner, String entity_name, String entity_id,
			Integer pbl_id, Integer seq_no, List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_ImageViewer(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ImageViewer(Frame owner, String title, byte[] image, Dimension size) {
		super(owner, title);
		this.image = image;
		this.SIZE = size;
		this.width = (int) SIZE.getWidth();
		this.height = (int) SIZE.getHeight();
		/*this.setPreferredSize(size);
		this.setMinimumSize(size);*/
		initGUI();
	}

	public dlg_ImageViewer(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ImageViewer(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_ImageViewer(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_ImageViewer(Window owner, String title, pnlClientInformation pnlCI) {
		super(owner, title);
		initGUI();
	}

	public dlg_ImageViewer(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_ImageViewer(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_ImageViewer(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_ImageViewer(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_ImageViewer(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_ImageViewer(Window owner, String title,
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
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				scrollImage = new JScrollPane();
				pnlCenter.add(scrollImage, BorderLayout.CENTER);
				{
					lblImage = new FncImageFileChooser(width, height);
					scrollImage.setViewportView(lblImage);
					lblImage.setByteImageIcon(image);
					lblImage.addMouseWheelListener(new MouseWheelListener() {
						
						public void mouseWheelMoved(MouseWheelEvent e) {
							if(e.getWheelRotation()<0){
								//lblImage.setZoomFactor(1.1*((Object) pnlCenter).getZoomFactor());
								
								System.out.println("Zoom In");
								lblImage.repaint();
					        }
					        //Zoom out
					        if(e.getWheelRotation()>0){
					        	//lblImage.setZoomFactor(lblImage.getZoomFactor()/1.1);
					        	System.out.println("Zoom Out");
					        	lblImage.repaint();
					        }
						}
					});
				}
			}
		}
		this.pack();
	}//XXX END OF INIT GUI
	
}

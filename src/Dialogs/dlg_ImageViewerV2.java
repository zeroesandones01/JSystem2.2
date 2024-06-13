package Dialogs;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//https://ateraimemo.com/Swing/ZoomAndPanPanel.html
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Buyers.ClientServicing.pnlClientInformation;
//import Dialogs.ZoomAndPanePanel.ZoomHandler;
import Functions.FncImageFileChooser;
import components._JDialog;
import interfaces._GUI;

public class dlg_ImageViewerV2 extends _JDialog implements _GUI {

	private JPanel pnlCenter;
	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private String seq_no;

	private byte[] image = null;
	private FncImageFileChooser lblImage;
	private JScrollPane scrollImage;
	private Integer width;
	private Integer height;
	protected final AffineTransform zoomTransform = new AffineTransform();
	protected transient Image img;
	protected Rectangle imgrect;
	//protected transient ZoomHandler handler;
	//protected transient DragScrollListener listener;
	private static final double ZOOM_MULTIPLICATION_FACTOR = 1.2;
	private static final int MIN_ZOOM = -10;
	private static final int MAX_ZOOM = 10;
	private static final int EXTENT = 1;
	private final BoundedRangeModel zoomRange = new DefaultBoundedRangeModel(0, EXTENT, MIN_ZOOM, MAX_ZOOM + EXTENT);
	private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	private final Point pp = new Point();

	private Dimension SIZE = new Dimension(500, 500);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	public dlg_ImageViewerV2(byte[] image) {
		this.image = image;
		initGUI();
	}

	public dlg_ImageViewerV2(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_ImageViewerV2(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_ImageViewerV2(Window owner, String entity_name, String entity_id,
			Integer pbl_id, Integer seq_no, List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_ImageViewerV2(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ImageViewerV2(Frame owner, String title, byte[] image, Dimension size) {
		super(owner, title);
		this.image = image;
		this.SIZE = size;
		this.width = (int) SIZE.getWidth();
		this.height = (int) SIZE.getHeight();
		/*this.setPreferredSize(size);
		this.setMinimumSize(size);*/
		initGUI();
	}

	public dlg_ImageViewerV2(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_ImageViewerV2(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_ImageViewerV2(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_ImageViewerV2(Window owner, String title, pnlClientInformation pnlCI) {
		super(owner, title);
		initGUI();
	}

	public dlg_ImageViewerV2(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_ImageViewerV2(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_ImageViewerV2(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_ImageViewerV2(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_ImageViewerV2(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_ImageViewerV2(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle("File Image");

		/*removeMouseListener(listener);
        removeMouseMotionListener(listener);
        removeMouseWheelListener(handler);
        listener = new DragScrollListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        handler = new ZoomHandler();
        addMouseWheelListener(handler);*/

		pnlCenter = new JPanel(new BorderLayout(3, 3));
		this.add(pnlCenter, BorderLayout.CENTER);
		this.addMouseListener(new MouseAdapter() {

			@Override 
			public void mouseWheelMoved(MouseWheelEvent e) {
				int dir = e.getWheelRotation();
				int z = zoomRange.getValue();
				zoomRange.setValue(z + EXTENT * (dir > 0 ? -1 : 1));
				if (z != zoomRange.getValue()) {
					Component c = e.getComponent();
					Container p = SwingUtilities.getAncestorOfClass(JViewport.class, c);
					if (p instanceof JViewport) {
						JViewport vport = (JViewport) p;
						Rectangle ovr = vport.getViewRect();

						double s = dir > 0 ? 1d / ZOOM_MULTIPLICATION_FACTOR : ZOOM_MULTIPLICATION_FACTOR;
						zoomTransform.scale(s, s);
						//double s = 1d + zoomRange.getValue() * .1;
						//zoomTransform.setToScale(s, s);
						Rectangle nvr = AffineTransform.getScaleInstance(s, s).createTransformedShape(ovr).getBounds();
						Point vp = nvr.getLocation();
						vp.translate((nvr.width - ovr.width) / 2, (nvr.height - ovr.height) / 2);
						//vp.translate((ovr.width) / 2, (ovr.height) / 2);
						vport.setViewPosition(vp);
						c.revalidate();
						c.repaint();
					}
				}
			}

			@Override 
			public void mouseDragged(MouseEvent e) {
				Component c = e.getComponent();
				Container p = SwingUtilities.getUnwrappedParent(c);
				if (p instanceof JViewport) {
					JViewport vport = (JViewport) p;
					Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
					Point vp = vport.getViewPosition();
					vp.translate(pp.x - cp.x, pp.y - cp.y);
					((JComponent) c).scrollRectToVisible(new Rectangle(vp, vport.getSize()));
					pp.setLocation(cp);
				}
			}

			@Override public void mousePressed(MouseEvent e) {
				Component c = e.getComponent();
				c.setCursor(hndCursor);
				Container p = SwingUtilities.getUnwrappedParent(c);
				if (p instanceof JViewport) {
					JViewport vport = (JViewport) p;
					Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
					pp.setLocation(cp);
				}
			}

			@Override public void mouseReleased(MouseEvent e) {
				e.getComponent().setCursor(defCursor);
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override 
			public void mouseDragged(MouseEvent e) {
				Component c = e.getComponent();
				Container p = SwingUtilities.getUnwrappedParent(c);
				if (p instanceof JViewport) {
					JViewport vport = (JViewport) p;
					Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
					Point vp = vport.getViewPosition();
					vp.translate(pp.x - cp.x, pp.y - cp.y);
					((JComponent) c).scrollRectToVisible(new Rectangle(vp, vport.getSize()));
					pp.setLocation(cp);
					System.out.println("Dumaan dito sa mouse dragged");
				}
			}

			@Override public void mousePressed(MouseEvent e) {
				Component c = e.getComponent();
				c.setCursor(hndCursor);
				Container p = SwingUtilities.getUnwrappedParent(c);
				if (p instanceof JViewport) {
					JViewport vport = (JViewport) p;
					Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
					pp.setLocation(cp);
				}
			}

			@Override public void mouseReleased(MouseEvent e) {
				e.getComponent().setCursor(defCursor);
			}
		});
		this.addMouseWheelListener(new MouseAdapter() {
			@Override 
			public void mouseWheelMoved(MouseWheelEvent e) {
				int dir = e.getWheelRotation();
				int z = zoomRange.getValue();
				zoomRange.setValue(z + EXTENT * (dir > 0 ? -1 : 1));
				if (z != zoomRange.getValue()) {
					Component c = e.getComponent();
					Container p = SwingUtilities.getAncestorOfClass(JViewport.class, c);
					if (p instanceof JViewport) {
						JViewport vport = (JViewport) p;
						Rectangle ovr = vport.getViewRect();

						double s = dir > 0 ? 1d / ZOOM_MULTIPLICATION_FACTOR : ZOOM_MULTIPLICATION_FACTOR;
						zoomTransform.scale(s, s);
						//double s = 1d + zoomRange.getValue() * .1;
						//zoomTransform.setToScale(s, s);
						Rectangle nvr = AffineTransform.getScaleInstance(s, s).createTransformedShape(ovr).getBounds();
						Point vp = nvr.getLocation();
						vp.translate((nvr.width - ovr.width) / 2, (nvr.height - ovr.height) / 2);
						//vp.translate((ovr.width) / 2, (ovr.height) / 2);
						vport.setViewPosition(vp);
						c.revalidate();
						c.repaint();
						System.out.println("Dumaan dito sa zoom in");
					}
				}
			}
		});
		this.pack();
		//displayDocument(entity_id, proj_id, pbl_id, seq_no);
	}//XXX END OF INIT GUI

	public void displayDocument(String entity_id, String proj_id, String pbl_id, String seq_no){

		byte bt[]=null;

		String strSQL = "SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND status_id = 'A'";

		try{

			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				bt = Functions.FncSelectRecords.rs.getBytes(1);

				if(bt!=null) {
					//label.setByteImageIcon(bt);
				}else{
					//label.setDefaultFileImage();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		Functions.FncSelectRecords.disconnect();
		
		try {

			InputStream in = new ByteArrayInputStream(image);
			BufferedImage bImageFromConvert = ImageIO.read(in);

			scrollImage = new JScrollPane();
			//scrollImage.setViewportView(new ZoomAndPanePanel(bImageFromConvert));
			/*this.img = bImageFromConvert;
			this.imgrect = new Rectangle(img.getWidth(this), img.getHeight(this));*/
			pnlCenter.add(new JScrollPane(new ZoomAndPanePanel(bImageFromConvert)));
			//pnlCenter.add(new JScrollPane(pnlIma), BorderLayout.CENTER);

		} catch (IOException ex) {

		}
	}

	public void paintComponent(Graphics g) {
		System.out.println("Dumaan dito sa paint components");
		//paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new Color(0x55FFFFFF, true));
		//Rectangle r = new Rectangle(500, 140, 150, 150);
		Rectangle r = new Rectangle(1000, 1000, 3000, 500);

		g2.drawImage(img ,zoomTransform, this); //or: g2.drawRenderedImage((RenderedImage) img, zoomTransform);
		//g2.drawImage(img, img.getHeight(this), img.getWidth(this), img.getWidth(this), img.getHeight(this), this);
		g2.fill(zoomTransform.createTransformedShape(imgrect).getBounds());
		g2.dispose();
	}

	class ZoomAndPanePanel extends JPanel {
		protected final AffineTransform zoomTransform = new AffineTransform();
		protected final transient Image img;
		protected final Rectangle imgrect;
		/*protected transient ZoomHandler handler;
    protected transient DragScrollListener listener;*/

		protected ZoomAndPanePanel(Image img) {
			super();
			this.img = img;
			this.imgrect = new Rectangle(img.getWidth(this), img.getHeight(this));
		}

		@Override 
		protected void paintComponent(Graphics g) {
			System.out.println("Dumaan dito sa paint components 2");
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setPaint(new Color(0x55FFFFFF, true));
			//Rectangle r = new Rectangle(500, 140, 150, 150);
			Rectangle r = new Rectangle(1000, 1000, 3000, 500);

			g2.drawImage(img ,zoomTransform, this); //or: g2.drawRenderedImage((RenderedImage) img, zoomTransform);
			//g2.drawImage(img, img.getHeight(this), img.getWidth(this), img.getWidth(this), img.getHeight(this), this);

			//g2.fill(zoomTransform.createTransformedShape(r));
			g2.fill(zoomTransform.createTransformedShape(imgrect).getBounds());
			g2.dispose();
		}
	}
}
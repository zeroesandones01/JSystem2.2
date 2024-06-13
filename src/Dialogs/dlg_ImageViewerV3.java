package Dialogs;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//https://ateraimemo.com/Swing/ZoomAndPanPanel.html
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.commons.collections.BidiMap;

import Functions.FncImageFileChooser;

public class dlg_ImageViewerV3 extends JPanel {
	
	public static byte[] displayScannedDocument() throws IOException{
		byte bt[]=null;
		
		String strSQL = "SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '8113655084' AND proj_id = '015' AND pbl_id = '71' AND seq_no = 1 AND status_id = 'A'";
		//String strSQL = "SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '1304528359' AND proj_id = '015' AND pbl_id = '81' AND seq_no = 1 AND status_id = 'A'";
		//label.setDefaultFileImage();
		System.out.printf("Value of SQL: %s%n", strSQL);
		
		try{
			
			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				//System.out.println("While Loop");
				bt = Functions.FncSelectRecords.rs.getBytes(1);

				if(bt!=null) {
					//label.setByteImageIcon(bt);
				}else{
					//label.setDefaultFileImage();
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();
		
		return bt;
	}
	
    private dlg_ImageViewerV3(){
        super(new BorderLayout(5, 5)); 
        try {
            InputStream in = new ByteArrayInputStream(displayScannedDocument());
            BufferedImage bImageFromConvert = ImageIO.read(in);
            
            JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
            this.add(pnlCenter, BorderLayout.CENTER);
            
            JScrollPane scrollImage = new JScrollPane();
            //scrollImage.setViewportView(new ZoomAndPanePanel(bImageFromConvert));
            scrollImage.add(new ZoomAndPanePanel(bImageFromConvert));
            //pnlCenter.add(scrollImage);
            //scrollImage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            pnlCenter.add(new JScrollPane(new ZoomAndPanePanel(bImageFromConvert)), BorderLayout.CENTER);
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //setPreferredSize(new Dimension(320, 240));
        setPreferredSize(new Dimension(1500, 1500));
    }
    
    private void displayDocument(String entity_id, String proj_id, String pbl_id, String seq_no){
    	
    	byte bt[]=null;
    	String strSQL = "SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND status_id = 'A'";
    	
    	try{
			
			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				//System.out.println("While Loop");
				bt = Functions.FncSelectRecords.rs.getBytes(1);

				if(bt!=null) {
					//label.setByteImageIcon(bt);
				}else{
					//label.setDefaultFileImage();
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();
    }
    
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("ZoomAndPanPanel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new dlg_ImageViewerV3(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        /*JPanel pnlImage = new JPanel(new BorderLayout(5, 5));
        pnlImage.add(new dlg_ImageViewerV3(), BorderLayout.CENTER);*/
        
    }
}

class ZoomAndPanePanel extends JPanel {
    protected final AffineTransform zoomTransform = new AffineTransform();
    protected final transient Image img;
    protected final Rectangle imgrect;
    protected transient ZoomHandler handler;
    protected transient DragScrollListener listener;

    protected ZoomAndPanePanel(Image img) {
        super();
        this.img = img;
        this.imgrect = new Rectangle(img.getWidth(this), img.getHeight(this));
    }
    
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new Color(0x55FFFFFF, true));
        //Rectangle r = new Rectangle(500, 140, 150, 150);
        Rectangle r = new Rectangle(1000, 1000, 3000, 500);
        //Rectangle r = imgrect;

        // use: AffineTransform#concatenate(...) and Graphics2D#setTransform(...)
        // https://docs.oracle.com/javase/8/docs/api/java/awt/geom/AffineTransform.html#concatenate-java.awt.geom.AffineTransform-
        // AffineTransform at = g2.getTransform();
        // at.concatenate(zoomTransform);
        // g2.setTransform(at);
        // g2.drawImage(img, 0, 0, this);
        // g2.fill(r);

        //or use: Graphics2D#drawImage(Image, AffineTransform, ImageObserver)
        //https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html#drawImage-java.awt.Image-java.awt.geom.AffineTransform-java.awt.image.ImageObserver-
        
        g2.drawImage(img ,zoomTransform, this); //or: g2.drawRenderedImage((RenderedImage) img, zoomTransform);
        //g2.drawImage(img, img.getHeight(this), img.getWidth(this), img.getWidth(this), img.getHeight(this), this);
        
        //g2.fill(zoomTransform.createTransformedShape(r));
        g2.fill(zoomTransform.createTransformedShape(r).getBounds());
        g2.dispose();
        
    }
    
    @Override public Dimension getPreferredSize() {
        Rectangle r = zoomTransform.createTransformedShape(imgrect).getBounds();
        return new Dimension(r.width, r.height);
    }
    
    @Override public void updateUI() {
        removeMouseListener(listener);
        removeMouseMotionListener(listener);
        removeMouseWheelListener(handler);
        super.updateUI();
        listener = new DragScrollListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        handler = new ZoomHandler();
        addMouseWheelListener(handler);
    }

    protected class ZoomHandler extends MouseAdapter {
        private static final double ZOOM_MULTIPLICATION_FACTOR = 1.2;
        private static final int MIN_ZOOM = -10;
        private static final int MAX_ZOOM = 10;
        private static final int EXTENT = 1;
        private final BoundedRangeModel zoomRange = new DefaultBoundedRangeModel(0, EXTENT, MIN_ZOOM, MAX_ZOOM + EXTENT);
        @Override public void mouseWheelMoved(MouseWheelEvent e) {
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
    }
}

class DragScrollListener extends MouseAdapter {
    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private final Point pp = new Point();
    
    @Override public void mouseDragged(MouseEvent e) {
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
    
}
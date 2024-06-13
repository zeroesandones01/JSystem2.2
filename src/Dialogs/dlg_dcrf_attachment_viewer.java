package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mortennobel.imagescaling.ResampleOp;

import Functions.FncGlobal;
import interfaces._GUI;


public class dlg_dcrf_attachment_viewer extends JDialog implements _GUI {

	/*	Author: Emmanuel D. Apostol	*/
	
	private static final long serialVersionUID = 7221126184121408726L;
	
	private String strDCRF;
	private JPanel panDisplay; 
	
	private Integer intAttCount; 
	private Integer intCount = 1;
	
	private JLabel lblDisplay; 
	private JLabel lblPrev; 
	private JLabel lblNext;
	private JLabel lblRatio1;
	private JLabel lblRatio2;
	
	private double zoom = 1; 
	
	private BufferedImage image; 
			
	private Font scrFont = new Font(this.getFont().getName(), this.getFont().getStyle(), 20); 
	private Color scrFocus = new Color(14, 14, 14); 

	public dlg_dcrf_attachment_viewer() {
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner) {
		super(owner);
		
	}

	public dlg_dcrf_attachment_viewer(Dialog owner) {
		super(owner);
		
	}

	public dlg_dcrf_attachment_viewer(Window owner) {
		super(owner);
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner, boolean modal) {
		super(owner, modal);
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner, String title) {
		super(owner, title);
		
	}

	public dlg_dcrf_attachment_viewer(Dialog owner, boolean modal) {
		super(owner, modal);
		
	}

	public dlg_dcrf_attachment_viewer(Dialog owner, String title) {
		super(owner, title);
		
	}

	public dlg_dcrf_attachment_viewer(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		
	}

	public dlg_dcrf_attachment_viewer(Window owner, String title) {
		super(owner, title);
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
	}

	public dlg_dcrf_attachment_viewer(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner, String title, boolean modal, String dcrf) {
		super(owner, title, modal);
		this.strDCRF = dcrf; 
		initGUI(); 
	}
	
	public dlg_dcrf_attachment_viewer(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		
	}

	public dlg_dcrf_attachment_viewer(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		
	}

	public dlg_dcrf_attachment_viewer(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		
	}

	public dlg_dcrf_attachment_viewer(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		
	}

	public void initGUI() {
		setLayout(new BorderLayout(0, 0));
		intAttCount = FncGlobal.GetInteger("select count(*)::int from rf_dcrf_attachments where dcrf_no = '"+strDCRF+"' and status_id = 'A'");

		JPanel panViewer = new JPanel(new BorderLayout(0, 0)); 
		panViewer.setBackground(Color.BLACK);
		{
			{
				lblRatio1 = new JLabel(intCount+" of "+intAttCount); 
				panViewer.add(lblRatio1, BorderLayout.PAGE_START); 
				lblRatio1.setPreferredSize(new Dimension(0, 20));
				lblRatio1.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), 11));
				lblRatio1.setBackground(Color.BLACK);
				lblRatio1.setForeground(Color.WHITE);
				lblRatio1.setOpaque(true);
				lblRatio1.setHorizontalAlignment(JLabel.CENTER);
			}
			{
				lblPrev = new JLabel("←"); 
				panViewer.add(lblPrev, BorderLayout.LINE_START); 
				lblPrev.setHorizontalAlignment(JLabel.CENTER);
				lblPrev.addMouseListener(mouseListener);
				lblPrev.setEnabled(false);
				lblPrev.setPreferredSize(new Dimension(30, 0));
				lblPrev.setName("Previous");
				lblPrev.setFont(scrFont);
				lblPrev.setForeground(Color.WHITE);
				lblPrev.setOpaque(false);
			}
			{
				panDisplay = new JPanel(new BorderLayout(0, 0)); 
				panViewer.add(panDisplay, BorderLayout.CENTER); 
				panDisplay.setOpaque(false);
				{

				}
			}
			{
				lblNext = new JLabel("→"); 
				panViewer.add(lblNext, BorderLayout.LINE_END); 
				lblNext.setHorizontalAlignment(JLabel.CENTER);
				lblNext.addMouseListener(mouseListener);
				lblNext.setPreferredSize(new Dimension(30, 0));
				lblNext.setName("Next");
				lblNext.setFont(scrFont);
				lblNext.setForeground(Color.WHITE);
				lblNext.setOpaque(false);
			}
			{
				lblRatio2 = new JLabel(intCount+" of "+intAttCount); 
				panViewer.add(lblRatio2, BorderLayout.PAGE_END); 
				lblRatio2.setPreferredSize(new Dimension(0, 20));
				lblRatio2.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), 11));
				lblRatio2.setBackground(Color.BLACK);
				lblRatio2.setForeground(Color.WHITE);
				lblRatio2.setOpaque(true);
				lblRatio2.setHorizontalAlignment(JLabel.CENTER);
			}
		}
		
		add(panViewer); 

		try {
			getImage(intCount);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	private void getImage(Integer intImg) throws SQLException, IOException {
		ResultSet rs = null;
		PreparedStatement ps = null; 
		
		try {
			ps = FncGlobal.connection.prepareStatement("select attachment_img \n" + 
					"from rf_dcrf_attachments \n" + 
					"where dcrf_no = '"+strDCRF+"' and attachment_no = "+intImg+" and status_id = 'A'");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
       while (rs.next()) {
            try (InputStream is = rs.getBinaryStream(1)) {
            	image = ImageIO.read(is);
            	redoDisplay(new ImageIcon(image));
            }
        }
		
		rs.close();
		ps.close();
	}
	
	private MouseListener mouseListener = new MouseListener() {
		public void mouseReleased(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			
		}
		
		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setOpaque(false);
			((JLabel) e.getSource()).setBackground(Color.BLACK);
		}
		
		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setOpaque(true);
			((JLabel) e.getSource()).setBackground(scrFocus);
		}

		public void mouseClicked(MouseEvent e) {
			
			if (((JLabel) e.getSource()).getText().equals("←")) {
				intCount--; 
				
				if (intCount<=1) {
					lblPrev.setEnabled(false);
					lblNext.setEnabled(true);
					intCount = 1; 
				} else {
					lblPrev.setEnabled(true);
					lblNext.setEnabled(true);
				}
			} else {
				intCount++; 
				
				if (intCount>=intAttCount) {
					lblNext.setEnabled(false);
					lblPrev.setEnabled(true);
					intCount=intAttCount; 
				} else {
					lblPrev.setEnabled(true);
					lblNext.setEnabled(true);
					
				}				
			}
			
			try {
				getImage(intCount);
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			} 
						
			lblRatio1.setText(intCount+" of "+intAttCount);
			lblRatio1.repaint();
			lblRatio1.revalidate();
			
			lblRatio2.setText(intCount+" of "+intAttCount);
			lblRatio2.repaint();
			lblRatio2.revalidate();
			
			lblDisplay.requestFocus();
		}
	};
	
	private MouseWheelListener mouseWheelListener = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			int notches = e.getWheelRotation();
			double temp = zoom - (notches * 0.2);
			
		    if (temp != zoom) {
		        zoom = temp;
		        resizeImage();
		    }
		}
	};
	
    public void resizeImage() {
    	
        ResampleOp resampleOp = new ResampleOp((int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom));
        BufferedImage resizedIcon = resampleOp.filter(image, null);
        Icon imageIcon = new ImageIcon(resizedIcon);
        redoDisplay(imageIcon); 
        
	}
    
    private void redoDisplay(Icon imgIcon) {
    	
		lblDisplay = new JLabel(imgIcon);
		lblDisplay.setOpaque(false);
        lblDisplay.setHorizontalAlignment(JLabel.CENTER);
		lblDisplay.addMouseWheelListener(mouseWheelListener);
		lblDisplay.setPreferredSize(new Dimension(imgIcon.getIconWidth(), imgIcon.getIconHeight()));
		
		JScrollPane scr = new JScrollPane(lblDisplay); 
		scr.setBorder(BorderFactory.createEmptyBorder());
		
		panDisplay.removeAll();
		panDisplay.add(scr, BorderLayout.CENTER); 

        lblDisplay.repaint();
        lblDisplay.revalidate();

		panDisplay.repaint();
		panDisplay.revalidate();
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		
		System.out.println("Screen Dimension: "+screenSize); 
		System.out.println("Image Dimension: "+new Dimension(imgIcon.getIconWidth(), imgIcon.getIconHeight())); 
		System.out.println("Scaled Dimension: "+getScaledDimension(new Dimension(imgIcon.getIconWidth(), imgIcon.getIconHeight()), screenSize));
    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}

package Functions;

	import java.awt.Color;
	import java.awt.Cursor;
	import java.awt.Graphics2D;
	import java.awt.Image;
	import java.awt.RenderingHints;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.awt.image.BufferedImage;
	import java.io.ByteArrayInputStream;
	import java.io.ByteArrayOutputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStream;

	import javax.imageio.ImageIO;
	import javax.swing.BorderFactory;
	import javax.swing.ImageIcon;
	import javax.swing.JFileChooser;
	import javax.swing.JLabel;
	import javax.swing.filechooser.FileNameExtensionFilter;

	public class FncImageFileChooser extends JLabel {
		private static final long serialVersionUID = -6939999596562915719L;
		protected String fileName = "";
		protected byte[] imgByte = null;
		protected JFileChooser picChooser = new JFileChooser();
		protected Boolean isOk;
		protected Boolean isClickable = false;

		private void openFile(){
			if(picChooser == null) picChooser = new JFileChooser();
			// Set file filter to allow only PICTURE files
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Image (jpeg, gif, png, pdf)", 
					"jpg", "jpeg", "gif", "png", "pdf");
			picChooser.setFileFilter(filter);
			picChooser.setAccessory(new FncImagePreview(picChooser));
			picChooser.setMultiSelectionEnabled(true);
			picChooser.setAcceptAllFileFilterUsed(true);
			picChooser.setSelectedFile(new File(""));
			int returnVal = picChooser.showOpenDialog(FncGlobal.parentFrame); // TODO parent

			if(returnVal == JFileChooser.APPROVE_OPTION) {
				java.awt.Image img = (new ImageIcon( String.valueOf( picChooser.getSelectedFile().getAbsolutePath() ) )).getImage();
				img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

				this.setIcon( new ImageIcon(img) );
				this.setFileName( picChooser.getSelectedFile().getAbsolutePath() );

				//System.out.println(this.getFileName());

				FileInputStream fileIS;
				byte[] bytes = null;
				try {
					fileIS = new FileInputStream(new File(this.getFileName()));
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for (int readNum; (readNum = fileIS.read(buf)) != -1;) {
						bos.write(buf, 0, readNum);      
					}
					bytes = bos.toByteArray();

					this.setImageByte(bytes);
				} catch (FileNotFoundException e) {
					this.setImageByte(null);
				} catch (IOException ex) {
					this.setImageByte(null);
				}

				isOk = true;
				//System.out.println("Image Byte: "+this.getImageByte());
			}else{
				isOk = false;
			}
		} // openFile


		public FncImageFileChooser(int width, int height){
			super();
			this.setPreferredSize( new java.awt.Dimension(width, height) );
			this.setSize(width, height);
			this.setDefaultImage();
			this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0,0,0)));// new java.awt.Color(28,150,28)));
			this.setBackground(Color.WHITE);
			this.setOpaque(true);

			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					if ((evt.getClickCount()>=2) && FncImageFileChooser.this.isClickable())
						openFile();
				} // mouseClicked
				public void mouseEntered(MouseEvent evt) {
					if (FncImageFileChooser.this.isClickable()) {
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						FncImageFileChooser.this.setToolTipText( "Double-click to upload picture." );
					}else{
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						FncImageFileChooser.this.setToolTipText(null);
					}

				} // mouseEntered		
			});
		} // constructor

		public static Image getScaledImage(Object obj, String strImg, int w, int h){
			//     	Image img = (new ImageIcon(getClass().getClassLoader().getResource(strImg))).getImage();
			Image img; 
			try{
				img = (new ImageIcon(obj.getClass().getClassLoader().getResource(strImg))).getImage();
			}catch (NullPointerException npe){
				img = (Image)obj;	
			} // trycatch

			BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(img, 0, 0, w, h, null);
			g2.dispose();
			return resizedImg;
		} // getScaledImage

		// SETTERS and GETTERS
		public void setFileName(String fname){
			this.fileName = fname;		
		}
		public String getFileName(){
			return this.fileName;
		}

		public void setImageByte(byte[] imgByte){
			BufferedImage bImageFromConvert = null;
			this.imgByte = imgByte;

			try {
				InputStream in = new ByteArrayInputStream(imgByte);
				bImageFromConvert = ImageIO.read(in);
				this.setIcon(new ImageIcon(getScaledImage(bImageFromConvert,"X",this.getWidth(),this.getHeight())));
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		public byte[] getImageByte(){
			return this.imgByte;
		}

		// FUNCTIONS
		public void setByteImageIcon( byte[] imgByte ){
			// COnvert byte[] to image then set as this JLabel's ImageIcon	
			BufferedImage bImageAddr = null;
			try {
				//bImageAddr = ImageIO.read(new ByteArrayInputStream( this.getImgByte() ));
				bImageAddr = ImageIO.read(new ByteArrayInputStream( imgByte ));

				this.setIcon( new javax.swing.ImageIcon( bImageAddr.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT) ) );
			} catch (IOException e) {	
				setDefaultImage();
				//System.out.println("ioexc");
			} catch (NullPointerException npe){
				//			this.setIcon( null );
				setDefaultImage();
				//System.out.println("nullexc");
			} // try-catch
			this.setImageByte(imgByte);
		} // setByteImageIcon

		public void setDefaultImage(){
			// Set Default Image 
			java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/Question_Mark-300x236.jpg" ) ))).getImage() ;
			img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

			this.setIcon((new ImageIcon(img)));
			this.imgByte = null;
		} // setDefaultImage
		
		public void setDefaultClientImage(){
			// Set Default Image 
			//java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/Question_Mark-300x236.jpg" ) ))).getImage() ;
			java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/user.jpg" ) ))).getImage() ;
			img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

			this.setIcon((new ImageIcon(img)));
			this.imgByte = null;
		}
		
		public void setDefaultClientSignature(){
			java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/signature.jpg" ) ))).getImage() ;
			img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

			this.setIcon((new ImageIcon(img)));
			this.imgByte = null;
		}
		
		public void setDefaultFileImage(){
			java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/no_file_selected.jpg" ) ))).getImage() ;
			img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

			this.setIcon((new ImageIcon(img)));
			this.imgByte = null;
		}
		
		public void setDefaultPreviewPDF(){
			java.awt.Image img = new ImageIcon(( this.getClass().getClassLoader().getResource( String.valueOf( "Images/previewpdf.jpg" ) ))).getImage() ;
			img = img.getScaledInstance( this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT );

			this.setIcon((new ImageIcon(img)));
			this.imgByte = null;
		}

		public Boolean getIsOk() {
			return isOk;
		}


		public Boolean isClickable() {
			return isClickable;
		}


		public void setClickable(Boolean isClickable) {
			this.isClickable = isClickable;
		}
}

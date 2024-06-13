package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.PBEParameterSpec;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JDateChooser;

import Accounting.FixedAssets.panelAssetInformation;
import Database.pgSelect;
import Functions.FncSystem;
import components.JTBorderFactory;
import interfaces._GUI;

public class dlg_accesories_v2 extends JDialog implements ActionListener, _GUI {
	
	//private Dimension size = new Dimension(650,400);
	private Border lineBorder= BorderFactory.createLineBorder(Color.gray);
	private JPanel pnlMain;
	private JPanel pnlname;
	private JLabel lblitem_name;
	private JPanel pnlbrand_name;
	private JLabel txtbrand_name;
	private JPanel pnlMain0;
	private JLabel lblitemname;
	private JLabel lblbrand;
	private JLabel lblserialmodel;
	private JPanel pnlMain1;
	private JPanel pnlMain1_1;
	private JCheckBox chkboxcpu;
	private JLabel lblcpu;
	private static JTextField txtcpubrand;
	private JPanel pnlMain1_2;
	private static JTextField txtcpuserial;
	private JPanel pnlMain2;
	private JPanel pnlMain2_1;
	private JLabel lblmonitor;
	private static JTextField txtmonitorbrand;
	private JPanel pnlMain2_2;
	private static JTextField txtmonitorserial;
	private JPanel pnlMain3;
	private JPanel pnlMain3_1;
	private JLabel lblkeyboard;
	private static JTextField txtkeyboard;
	private JPanel pnlMain3_2;
	private static JTextField txtkeyboardserial;
	private JPanel pnlMain4;
	private JPanel pnlMain4_1;
	private JCheckBox chckboxmouse;
	private JLabel lblmouse;
	private static JTextField txtmouse;
	private JPanel pnlMain4_2;
	private static JTextField txtmouseserial;
	private JPanel pnlMain5;
	private JPanel pnlMain5_1;
	private JLabel lblhardisk;
	private static JTextField txthardisk;
	private JPanel pnlMain5_2;
	private static JTextField txthardiskserial;
	private JPanel pnlMain6;
	private JPanel pnlMain6_1;
	private JLabel lblram;
	private static JTextField txtram;
	private JPanel pnlMain6_2;
	private static JTextField txtramserial;
	private JPanel pnlMain7;
	private JPanel pnlMain7_1;
	private JLabel lblmotherboard;
	private static JTextField txtmotherboard;
	private JPanel pnlMain7_2;
	private static JTextField txtmotherboardserial;
	private JPanel pnlMain8;
	private JPanel pnlMain8_1;
	private JLabel lblups;
	private static JTextField txtups;
	private JPanel pnlMain8_2;
	private static JTextField txtupsserial;
	private JPanel pnlMain9;
	private JPanel pnlMain9_1;
	private JLabel lblavr;
	private static JTextField txtavr;
	private JPanel pnlMain9_2;
	private static JTextField txtavrserial;
	private JPanel pnlMain10;
	private JPanel pnlMain10_1;
	private JLabel lbldvdwriter;
	private static JTextField txtdvdwriter;
	private JPanel pnlMain10_2;
	private static JTextField txtdvdwriterserial;
	private static JTextField txtcpustatus;
	private static JTextField txtcpudispose;
	private JPanel pnlMain0_1;
	private JPanel pnlMain0_2;
	private JLabel lblitemstatus;
	private JLabel lblitemdispose;
	private static JTextField txtmonitorstatus;
	private static JTextField txtmonitordispose;
	private static JTextField txtkeyboardstatus;
	private static JTextField txtkeyboardispose;
	private static JTextField txtmousestatus;
	private static JTextField txtmousedispose;
	private static JTextField txthardiskstatus;
	private static JTextField txthardiskdispose;
	private static JTextField txtramdispose;
	private static JTextField txtramstatus;
	private static JTextField txtmotherboardstatus;
	private static JTextField txtmotherboarddispose;
	private static JTextField txtupsstatus;
	private static JTextField txtupsdispose;
	private static JTextField txtavrstatus;
	private static JTextField txtavrdispose;
	private static JTextField txtdvdwriterstatus;
	private static JTextField txtdvdwriterdispose;
	private JPanel pnlmain11;
	private JPanel pnlmain11_1;
	private JPanel pnlmain11_2;
	private JLabel lblmemory;
	private static JTextField txtmemory;
	private static JTextField txtmemoryserial;
	private static JTextField txtmemorystatus;
	private static JTextField txtmemorydispose;
	private JPanel pnlmain12;
	private JPanel pnlmain13;
	private JPanel pnlmain14;
	private JPanel pnlmain12_1;
	private JPanel pnlmain12_2;
	private JPanel pnlmain13_1;
	private JPanel pnlmain13_2;
	private JPanel pnlmain14_1;
	private JPanel pnlmain14_2;
	private JLabel lblcasing;
	private static JTextField txtcasing;
	private static JTextField txtcasingserial;
	private static JTextField txtcasingstatus;
	private static JTextField txtcasingdispose;
	private JLabel lblprocessor;
	private static JTextField txtprocessor;
	private static JTextField txtprocessorserial;
	private static JTextField txtprocessstatus;
	private static JTextField txtprocesssortatus;
	private static JTextField txtprocessordispose;
	private JLabel lblos;
	private static JTextField txtos;
	private static JTextField txtosserial;
	private static JTextField txtosstatus;
	private static JTextField txtosdispose;
	private JPanel pnlmain15;
	private JButton btnok;
	private JButton btncancel;
	private JLabel lblitem;
	private JLabel lblserial;
	private JLabel lblstatus;
	private JLabel lbldispose;
	private JXPanel pnlNorth;
	private JXPanel pnlCenter;
	private JXPanel panLabel;
	private JLabel lblsubassetno;
	private JXPanel pnlSouth;
	private JXPanel panelitemname;
	
	/*	Mann2x	*/
	private static JLabel[] label; 
	private static JTextField[] txtBrand;
	private static JTextField[] txtSerial; 
	private static JTextField[] txtstatus;
	private static JDateChooser[] txtdispose;
	private static JTextField[] txtitemname;
	private static JTextField[] subassetno;
	
	
	
	public dlg_accesories_v2() {
		initGUI();
	}

	public dlg_accesories_v2(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_accesories_v2(Dialog owner) {
		super(owner);
		initGUI();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8267313166657874118L;

	@Override
	public void initGUI() {
		
		pnlMain=new JPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain);
		this.setTitle("Computer Accesories");
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panheader=new JXPanel(new BorderLayout(5,5));
				pnlMain.add(panheader,BorderLayout.NORTH);
				panheader.setPreferredSize(new Dimension(0, 20));
				panheader.setBorder(lineBorder);
				{
					pnlNorth=new JXPanel();
					panheader.add(pnlNorth,BorderLayout.WEST);
					pnlNorth.setPreferredSize(new Dimension(50, 0));
					{
						lblitem=new JLabel("ITEM");
						pnlNorth.add(lblitem,BorderLayout.CENTER);
						//lblitem.setBorder(lineBorder);
					}
				}
				{
					pnlCenter=new JXPanel();
					panheader.add(pnlCenter,BorderLayout.CENTER);
					{
						lblitem_name=new JLabel("ITEM NAME");
						pnlCenter.add(lblitem_name,BorderLayout.CENTER);
						//lblitem_name.setBorder(lineBorder);
						
					}
				}
				{
					pnlSouth=new JXPanel(new GridLayout(1, 5, 5, 5));
					panheader.add(pnlSouth,BorderLayout.EAST);
					pnlSouth.setPreferredSize(new Dimension(500, 0));
					
					{
						lblsubassetno=new JLabel("  Sub Asset No.");
						pnlSouth.add(lblsubassetno);
						//lblsubassetno.setBorder(lineBorder);
					}
					{
						lblbrand=new JLabel("       BRAND");
						pnlSouth.add(lblbrand);
						//lblbrand.setBorder(lineBorder);
					}
					{
						lblserial=new JLabel("    SERIAL NO.");
						pnlSouth.add(lblserial);
						//lblserial.setBorder(lineBorder);
					}
					{
						lblstatus=new JLabel("       STATUS");
						pnlSouth.add(lblstatus);
						//lblstatus.setBorder(lineBorder);
					}
					{
						lbldispose=new JLabel("   Dispose Date");
						pnlSouth.add(lbldispose);
						//lbldispose.setBorder(lineBorder);
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				pnlMain.add(panCenter, BorderLayout.CENTER);
				//panCenter.setBorder(lineBorder);
				
				{
					
					pgSelect db= new pgSelect();					
					db.select("select *,split_part(sub_asset_no,'-',2)::int as subassetno from tbl_accesories where asset_no='"+panelAssetInformation.txtAssetNo.getText()+"' order by subassetno ");
					
					{
						 panLabel = new JXPanel(new GridLayout(db.getRowCount(), 1, 5, 5)); 
						panCenter.add(panLabel, BorderLayout.LINE_START);
						panLabel.setPreferredSize(new Dimension(50, 0));
						
						{
							label = new JLabel[db.getRowCount()]; 
							
							for (int x=0; x<db.getRowCount(); x++) {
								label[x] = new JLabel(db.getResult()[x][14].toString()); 
								panLabel.add(label[x]);
								label[x].setHorizontalAlignment(JLabel.CENTER);
							}
						}
					}
					{
						JXPanel panelitemname=new JXPanel(new BorderLayout(5,5));
						panCenter.add(panelitemname,BorderLayout.CENTER);
						{
							JXPanel panitemname=new JXPanel(new GridLayout(db.getRowCount(),1,5,5));
							panelitemname.add(panitemname);
							panitemname.setPreferredSize(new Dimension(150, 0));
							{
								txtitemname=new JTextField[db.getRowCount()];
								
								for(int x=0; x<db.getRowCount(); x++){
									txtitemname[x]=new JTextField(db.getResult()[x][2].toString());
									//txtitemname[x].setHighlighter(h);
									panitemname.add(txtitemname[x]);
									txtitemname[x].setHorizontalAlignment(JTextField.CENTER);
								}
							}
						}
					}
					{
						JXPanel pantxtfield = new JXPanel(new GridLayout(1, 5, 5, 5));
						panCenter.add(pantxtfield, BorderLayout.LINE_END);
						pantxtfield.setPreferredSize(new Dimension(500, 0));
						{
							
							{
								JXPanel pansubassetno=new JXPanel(new GridLayout(db.getRowCount(),1,5,5));
								pantxtfield.add(pansubassetno);
								{
									subassetno=new JTextField[db.getRowCount()];
									
									for(int x=0;x<db.getRowCount();x++){
										subassetno[x]=new JTextField(db.getResult()[x][1].toString());
										pansubassetno.add(subassetno[x]);
										subassetno[x].setHorizontalAlignment(JTextField.CENTER);
									}
								}
							}
							{
								JXPanel panBrand = new JXPanel(new GridLayout(db.getRowCount(), 1, 5, 5));
								pantxtfield.add(panBrand); 
								{
									txtBrand = new JTextField[db.getRowCount()];
									
									for (int x=0; x<db.getRowCount(); x++) {
										txtBrand[x] = new JTextField(db.getResult()[x][3].toString());
										panBrand.add(txtBrand[x]); 
										txtBrand[x].setHorizontalAlignment(JTextField.CENTER);
									}
								}
							}
							{
								JXPanel panSerial = new JXPanel(new GridLayout(db.getRowCount(), 1, 5, 5));
								pantxtfield.add(panSerial); 
								{
									txtSerial = new JTextField[db.getRowCount()];
									
									for (int x=0; x<db.getRowCount(); x++) {
										txtSerial[x]=new JTextField(db.getResult()[x][4].toString());
										panSerial.add(txtSerial[x]);
										txtSerial[x].setHorizontalAlignment(JTextField.CENTER);
									}
									
								}
							}
							{
								JXPanel panstatus=new JXPanel(new GridLayout(db.getRowCount(), 1, 5, 5));
								pantxtfield.add(panstatus);
								panstatus.setPreferredSize(new Dimension(75,0));
								
								{
									txtstatus=new JTextField[db.getRowCount()];
									
									for (int x=0; x<db.getRowCount(); x++) {
										txtstatus[x]=new JTextField(db.getResult()[x][5].toString());
										panstatus.add(txtstatus[x]);
										txtstatus[x].setHorizontalAlignment(JTextField.CENTER);
									}
								}
							}
							{
								JXPanel pandispose=new JXPanel(new GridLayout(db.getRowCount(), 1, 5, 5));
								pantxtfield.add(pandispose);
								{
									
									txtdispose=new JDateChooser[db.getRowCount()];
									
									for (int x=0; x<db.getRowCount(); x++) {
										txtdispose[x]=new JDateChooser();
										pandispose.add(txtdispose[x]);
										txtdispose[x].getCalendarButton().setVisible(false);

										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
										try {
											txtdispose[x].setDate(sdf.parse(db.getResult()[x][12].toString()));
										} catch (Exception e) {
											txtdispose[x].setDate(null);
										}

									}
								}
							}
						}
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(); 
				pnlMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(70, 30));
				//panEnd.setBorder(lineBorder);p
				{
					btnok=new JButton("OK");
					panEnd.add(btnok);
					btnok.setActionCommand("OK");
					//btnok.setPreferredSize(new Dimension(30, 0));
					btnok.addActionListener(this);
				}
				/*{
					btncancel=new JButton("CANCEL");
					panEnd.add(btncancel);
					btncancel.setActionCommand("CANCEL");
					btncancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
				}*/
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("OK")){
			
			int to_exit=JOptionPane.showConfirmDialog(null, "Do you want to exit?", "EXIT", JOptionPane.YES_NO_OPTION);
			
			if(to_exit==JOptionPane.YES_OPTION){
				dispose();
			}
		}
	}
	/*public static void displayaccesories (String asset_no){
		pgSelect db= new pgSelect();

		db.select("select * from tbl_accesories where asset_no='"+asset_no+"' order by sub_asset_no ");
		
		
		
		String[] noref = new String[]{"1","2","3","4","5","6"};
		
		for (int i = 0; i < 6; i++) {
            //JTextField txt = new JTextField(noref[i]);
           // txt.setName("txt"+i);
            System.out.println("txt"+i);
            
            if(i==1){
            	txtcpubrand.setText((String) db.getResult()[i][3]);//brand
    			txtcpuserial.setText((String) db.getResult()[0][4]);//serial_model_no
    			txtcpustatus.setText((String) db.getResult()[0][5]);//status_id
    			txtcpudispose.setText((String) db.getResult()[0][12]);
            }
            
            
		}
		if(db.isNotNull()){
			
			txtcpubrand.setText((String) db.getResult()[0][3]);//brand
			txtcpuserial.setText((String) db.getResult()[0][4]);//serial_model_no
			txtcpustatus.setText((String) db.getResult()[0][5]);//status_id
			txtcpudispose.setText((String) db.getResult()[0][12]);//date_dispose
			
			txtmonitorbrand.setText((String) db.getResult()[1][3]);
			txtmonitorserial.setText((String) db.getResult()[1][4]);
			txtmonitorstatus.setText((String) db.getResult()[1][5]);
			txtmonitordispose.setText((String) db.getResult()[1][12]);
			
			txtkeyboard.setText((String) db.getResult()[2][3]);
			txtkeyboardserial.setText((String) db.getResult()[2][4]);
			txtkeyboardstatus.setText((String) db.getResult()[2][5]);
			txtkeyboardispose.setText((String) db.getResult()[2][12]);
			
			txtmouse.setText((String) db.getResult()[3][3]);
			txtmouseserial.setText((String) db.getResult()[3][4]);
			txtmousestatus.setText((String) db.getResult()[3][5]);
			txtmousedispose.setText((String) db.getResult()[3][12]);
			
			txthardisk.setText((String) db.getResult()[4][3]);
			txthardiskserial.setText((String) db.getResult()[4][4]);
			txthardiskstatus.setText((String) db.getResult()[4][5]);
			txthardiskdispose.setText((String) db.getResult()[4][12]);
			
			txtmemory.setText((String) db.getResult()[5][3]);
			txtmemoryserial.setText((String) db.getResult()[5][4]);
			txtmemorystatus.setText((String) db.getResult()[5][5]);
			txtmemorydispose.setText((String) db.getResult()[5][12]);					
			
			txtmotherboard.setText((String) db.getResult()[6][3]);
			txtmotherboardserial.setText((String) db.getResult()[6][4]);
			txtmotherboardstatus.setText((String) db.getResult()[6][5]);
			txtmotherboarddispose.setText((String) db.getResult()[6][12]);	
			
			txtups.setText((String) db.getResult()[7][3]);
			txtupsserial.setText((String) db.getResult()[7][4]);
			txtupsstatus.setText((String) db.getResult()[7][5]);
			txtupsdispose.setText((String) db.getResult()[7][12]);
			
			txtdvdwriter .setText((String) db.getResult()[8][3]);
			txtdvdwriterserial .setText((String) db.getResult()[8][4]);
			txtdvdwriterstatus .setText((String) db.getResult()[8][5]);
			txtdvdwriterdispose.setText((String) db.getResult()[8][12]);
			
			
			
			System.out.println("txtcpubrand "+ db.getResult()[0][3]);
			System.out.println("txtcpuserial "+ db.getResult()[0][4]);
			System.out.println("txtcpustatus "+ db.getResult()[0][5]);
			System.out.println("txtcpudispose "+ db.getResult()[0][12]);
			
		}
		
	}*/
	public static String generatesubasset(String asset_no) {
		pgSelect db = new pgSelect();
		
		String strsql="select '"+asset_no+"' || '-' || (select count (*)+1 from tbl_accesories   where asset_no='"+asset_no+"')";
		
		db.select(strsql);
		return db.getResult()[0][0].toString();
		
	}
	

}

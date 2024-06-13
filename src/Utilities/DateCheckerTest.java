package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Database.pgSelect;
import Functions.FncGlobal;
import components._JInternalFrame;
import interfaces._GUI;
import net.sf.jasperreports.engine.export.Grid;

public class DateCheckerTest extends _JInternalFrame implements _GUI {
	private static final long serialVersionUID = -80997572757673160L;
	static String title = "Date Checker";
	static Dimension size = new Dimension (400,200);


public 	DateCheckerTest() {
	super(title, false, true, false, true);
	initGUI();
}
public 	DateCheckerTest(String Title) {
	super(Title);
	initGUI();
}


	
	public void initGUI() {
		this.setSize(size);
		this.setPreferredSize(size);
		this.setTitle(title);
		
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain,BorderLayout.CENTER);
			
			{
				JPanel pnlMainCenter = new JPanel(new GridLayout(3, 0,3,3));
				pnlMain.add(pnlMainCenter);
				
				{
					JButton btnNow = new JButton("Sql Now");
					pnlMainCenter.add(btnNow);
					btnNow.addActionListener(new ActionListener() {
						
						
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(getContentPane(),"Date Today:\n"+NowValue() , "Date",JOptionPane.INFORMATION_MESSAGE);
						}
					});
				}
				{
					JButton btnCurrentDate = new JButton("Sql Current Date");
					pnlMainCenter.add(btnCurrentDate);
					btnCurrentDate.addActionListener(new ActionListener() {
						
						
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(getContentPane(),"Date Today:\n"+CurrenDate() , "Date",JOptionPane.INFORMATION_MESSAGE);

						}
					});
				}
				{
					JButton btnJaveGetDate = new JButton("Jave Get Date Today");
					pnlMainCenter.add(btnJaveGetDate);
					btnJaveGetDate.addActionListener(new ActionListener() {
						
						
						public void actionPerformed(ActionEvent e) {
							   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
							   LocalDateTime now = LocalDateTime.now(); 
							JOptionPane.showMessageDialog(getContentPane(),"Date Today:\n"+dtf.format(now), "Date",JOptionPane.INFORMATION_MESSAGE);

						}
					});

				}
			}
		}

	}
	public static String NowValue() {
		pgSelect db = new pgSelect();
		String query = "SELECT to_char(now(),'yyyy-MM-dd')";
		
		db.select(query);
		String sqlNow = (String) db.getResult()[0][0];
		
		return sqlNow;
		}
	public static String CurrenDate() {
		pgSelect db = new pgSelect();
		String query = "SELECT to_char(CURRENT_DATE,'yyyy-MM-dd')";
		
		db.select(query);
		String sqlCuurent = (String) db.getResult()[0][0];
		
		return sqlCuurent;
		}
	}



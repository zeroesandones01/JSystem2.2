package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class TemporaryCheckVoucher extends _JInternalFrame implements _GUI, ActionListener  {

	private static final long serialVersionUID = 1L;
	static String title = "Temporary Check Voucher"; 
	Dimension frameSize = new Dimension(400, 200);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00");
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JTextField txtPayee;
	private JButton btnPreview;
	private _JXFormattedTextField txtAmount;

	public TemporaryCheckVoucher() {
		super(title, false, true, false, true); 
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			{
				JPanel pnlDetails = new JPanel(new BorderLayout(5, 5)); 
				pnlMain.add(pnlDetails, BorderLayout.CENTER); 
				pnlDetails.setBorder(JTBorderFactory.createTitleBorder("Check Details"));
				{
					JPanel pnlLabels = new JPanel(new GridLayout(2, 1, 5, 5)); 
					pnlDetails.add(pnlLabels, BorderLayout.WEST); 
					pnlDetails.setPreferredSize(new Dimension(50, 0));
					{
						JLabel lblPayee = new JLabel("Payee:"); 
						pnlLabels.add(lblPayee); 
					}
					{
						JLabel lblAmount = new JLabel("Amount:");
						pnlLabels.add(lblAmount); 
					}
				}
				{
					JPanel pnlDetailFields = new JPanel(new GridLayout(2, 1, 5, 5)); 
					pnlDetails.add(pnlDetailFields, BorderLayout.CENTER); 
					{
						txtPayee = new JTextField(); 	
						txtPayee.setHorizontalAlignment(JTextField.CENTER);
						pnlDetailFields.add(txtPayee);
					}
					{
						txtAmount = new _JXFormattedTextField("0");
						txtAmount.setHorizontalAlignment(JTextField.CENTER);
						txtAmount.setEnabled(true);
						pnlDetailFields.add(txtAmount);
						txtAmount.setFont(new Font("Segoe UI", Font.BOLD, 12));
						txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					}
					
				}
			}
			{
				JPanel pnlPreview = new JPanel(new BorderLayout(5, 5)); 
				pnlMain.add(pnlPreview, BorderLayout.SOUTH); 
				pnlPreview.setPreferredSize(new Dimension(0, 50));
				{
					JPanel flowPanel = new JPanel(new FlowLayout());
					pnlPreview.add(flowPanel, BorderLayout.CENTER); 
					btnPreview = new JButton("Preview"); 
					flowPanel.add(btnPreview);
					btnPreview.setPreferredSize(new Dimension(70, 40));
					btnPreview.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							String payee = txtPayee.getText().toUpperCase(); 
							BigDecimal cv_amt = (BigDecimal) txtAmount.getValued(); 
							
							// INSERT INTO temporary table 
							pgUpdate db = new pgUpdate();
							String sql = "INSERT INTO public.tmp_initial_printed_cv\n"
										+ " (payee_name, cv_amount, created_by)\n"
										+ "	VALUES ('"+payee.trim()+"', "+cv_amt+", '"+UserInfo.EmployeeCode+"');";
							
							db.executeUpdate(sql, false);
							db.commit();

							FncSystem.out("Insert Printed CV:", sql);
							
							Map<String, Object> mapParameters = new HashMap<String, Object>();
							
							mapParameters.put("payee", payee.trim().toUpperCase()); 
							mapParameters.put("cv_amt", nf.format(Double.valueOf(cv_amt.toString())));
							mapParameters.put("cv_date", dateFormat.format(FncGlobal.getDateToday())); 
							
							System.out.println("Payee: "+ payee);
							System.out.println("Amount: "+ cv_amt);

							FncReport.generateReport("/Reports/rptCV_checkAUB_Temp.jasper", "Check Voucher", mapParameters);
							
							txtAmount.setEditable(true);
							btnPreview.setEnabled(true); 
						}
					});
				}
			}
		}
		
	}

}

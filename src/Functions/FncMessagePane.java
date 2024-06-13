package Functions;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lowagie.text.Font;

public class FncMessagePane {

	public FncMessagePane() {
		// TODO Auto-generated constructor stub
	}

	public static void showException(Exception e) {
		StringBuilder sb = new StringBuilder("");
		sb.append(e.getMessage());
		sb.append("\n");

		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(ste.toString());
			sb.append("\n");
		}

		JTextArea jta = new JTextArea(sb.toString());
		jta.setEditable(false);

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(500, 250));

		JLabel lblMessage = new JLabel(e.getLocalizedMessage());
		lblMessage.setFont(lblMessage.getFont().deriveFont(Font.BOLD));

		JPanel pnlMain = new JPanel(new BorderLayout(0, 5));
		pnlMain.add(lblMessage, BorderLayout.NORTH);
		pnlMain.add(jsp, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(FncGlobal.homeMDI, pnlMain, e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
	}

	public static void showSQLException(Exception e) {
		StringBuilder sb = new StringBuilder("");
		sb.append(e.getMessage());
		sb.append("\n");

		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(ste.toString());
			sb.append("\n");
		}

		JTextArea jta = new JTextArea(sb.toString());
		jta.setEditable(false);

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(500, 250));
		
		String title = String.format("%s on %s.", e.getLocalizedMessage().replaceAll("\\.", ""), FncGlobal.getURLIPAdress());

		JLabel lblMessage = new JLabel(title);
		lblMessage.setFont(lblMessage.getFont().deriveFont(Font.BOLD));

		JPanel pnlMain = new JPanel(new BorderLayout(0, 5));
		pnlMain.add(lblMessage, BorderLayout.NORTH);
		pnlMain.add(jsp, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(FncGlobal.homeMDI, pnlMain, title, JOptionPane.ERROR_MESSAGE);
	}

}

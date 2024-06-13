package Functions;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Dialogz {

	public static void showException(Exception e, int messageType) {
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

		JOptionPane.showMessageDialog(FncGlobal.homeMDI, jsp, e.getLocalizedMessage(), messageType);
	}

	public static void showException(Component parentComponent, Exception e, int messageType) {
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

		JOptionPane.showMessageDialog(parentComponent, jsp, e.getLocalizedMessage(), messageType);
	}
}

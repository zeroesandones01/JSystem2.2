package interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DateChooser._JDateChooser;
import Lookup._JLookup;
import Lookup.lookupMethods;

public class GUIBuilder {

	public static JPanel createLabelLookupText(int width, String labelText, int alignment, final _JLookup lookup, String SQL,
			final JTextField text) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5));
				panel.add(panLabel, BorderLayout.LINE_START);
				panLabel.setPreferredSize(new Dimension(width, 0));
				{
					{
						JLabel label = new JLabel(labelText);
						panLabel.add(label);
						label.setHorizontalAlignment(alignment);
					}
					{
						panLabel.add(lookup);
						lookup.setReturnColumn(0);
						lookup.setLookupSQL(SQL);
						lookup.setValue("");
						lookup.addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent key) {

								if (key.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookup.setValue("");
									text.setText("");
								}
							}
							
							@Override
							public void keyReleased(KeyEvent key) {
								
							}
							
							@Override
							public void keyPressed(KeyEvent key) {
								
							}
						});
					}
				}
			}
			{
				panel.add(text, BorderLayout.CENTER);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setEditable(false);
			}
		}

		return panel;
	}

	public static JPanel createLabelLookupText2(String labelText, int alignment, _JLookup lookup, String SQL,
			JTextField text) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5));
				panel.add(panLabel, BorderLayout.LINE_START);
				panLabel.setPreferredSize(new Dimension(200, 0));
				{
					{
						JLabel label = new JLabel(labelText);
						panLabel.add(label);
						label.setHorizontalAlignment(alignment);
					}
					{
						panLabel.add(lookup);
						lookup.setReturnColumn(0);
						lookup.setLookupSQL(SQL);
						lookup.setValue("");
					}
				}
			}
			{
				text = new JTextField("");
				panel.add(text, BorderLayout.CENTER);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setEditable(false);
			}
		}

		return panel;
	}

	public static JPanel createLabelLookup(String labelText, int alignment, _JLookup lookup, String SQL) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JLabel label = new JLabel(labelText);
				panel.add(label);
				label.setHorizontalAlignment(alignment);
				label.setPreferredSize(new Dimension(200, 0));
			}
			{
				lookup = new _JLookup("");
				panel.add(lookup, BorderLayout.CENTER);
				lookup.setReturnColumn(0);
				lookup.setLookupSQL(lookupMethods.getCompany());
				lookup.setValue("");
			}
		}

		return panel;
	}

	public static JPanel createLabelText(String labelText, int alignment, JTextField text) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JLabel label = new JLabel(labelText);
				panel.add(label, BorderLayout.LINE_START);
				label.setHorizontalAlignment(alignment);
				label.setPreferredSize(new Dimension(200, 0));
			}
			{
				panel.add(text, BorderLayout.CENTER);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setEditable(false);
			}
		}

		return panel;
	}

	public static JPanel createLabelText(int width, String labelText, int alignment, JTextField text) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JLabel label = new JLabel(labelText);
				panel.add(label, BorderLayout.LINE_START);
				label.setHorizontalAlignment(alignment);
				label.setPreferredSize(new Dimension(width, 0));
			}
			{
				panel.add(text, BorderLayout.CENTER);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setEditable(true);
			}
		}

		return panel;
	}
	
	public static JPanel createLabelCombo(int width, String labelText, int alignment, JComboBox<String> combo) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JLabel label = new JLabel(labelText);
				panel.add(label, BorderLayout.LINE_START);
				label.setHorizontalAlignment(alignment);
				label.setPreferredSize(new Dimension(width, 0));
			}
			{
				panel.add(combo, BorderLayout.CENTER); 
				combo.setSelectedIndex(0);	
			}
		}

		return panel;
	}
	
	public static JPanel createLabelFromToDate(int width, int alignment, _JDateChooser from, _JDateChooser to) {
		JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
		{
			{
				JPanel panFrom = new JPanel(new BorderLayout(5, 5));
				panel.add(panFrom); 
				{
					{
						JLabel label = new JLabel("From");
						panFrom.add(label, BorderLayout.LINE_START);
						label.setHorizontalAlignment(alignment);
						label.setPreferredSize(new Dimension(width, 0));
					}
					{
						
						panFrom.add(from, BorderLayout.CENTER);
						from.getCalendarButton().setVisible(true);
						from.setDate(null);
					}		
				}
			}
			{
				JPanel panTo = new JPanel(new BorderLayout(5, 5));
				panel.add(panTo); 
				{
					{
						JLabel label = new JLabel("To");
						panTo.add(label, BorderLayout.LINE_START);
						label.setHorizontalAlignment(JLabel.CENTER);
						label.setPreferredSize(new Dimension(width, 0));
					}
					{
						panTo.add(to, BorderLayout.CENTER);
						to.getCalendarButton().setVisible(true);
						to.setDate(null);
					}
				}

			}
		}

		return panel;
	}
	
	public static JPanel createLookupText(int width, final _JLookup lookup, String SQL, final JTextField text) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		{
			{
				JPanel panLabel = new JPanel(new BorderLayout(5, 5));
				panel.add(panLabel, BorderLayout.LINE_START);
				panLabel.setPreferredSize(new Dimension(width, 0));
				{
					{
						panLabel.add(lookup, BorderLayout.CENTER);
						lookup.setReturnColumn(0);
						lookup.setLookupSQL(SQL);
						lookup.setValue("");
						lookup.addKeyListener(new KeyListener() {
							
							@Override
							public void keyTyped(KeyEvent key) {

								if (key.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									lookup.setValue("");
									text.setText("");
								}
							}
							
							@Override
							public void keyReleased(KeyEvent key) {
								
							}
							
							@Override
							public void keyPressed(KeyEvent key) {
								
							}
						});
					}
				}
			}
			{
				panel.add(text, BorderLayout.CENTER);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setEditable(false);
			}
		}

		return panel;
	}
}

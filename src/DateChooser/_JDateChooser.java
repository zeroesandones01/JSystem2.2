package DateChooser;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class _JDateChooser extends JDateChooser implements DateDispatcher {

	private static final long serialVersionUID = -3900194174049853616L;
	private JLabel label = new JLabel();

	private List<DateListener> listeners;

	String name;

	public _JDateChooser() {
		super();
		initThis();
	}

	public _JDateChooser(IDateEditor dateEditor) {
		super(dateEditor);
		initThis();
	}

	public _JDateChooser(hasSelect selectHas) {
		super(selectHas);
		initThis();
	}

	public _JDateChooser(Date date) {
		super(date);
		initThis();
	}

	public _JDateChooser(Date date, String dateFormatString) {
		super(date, dateFormatString);
		initThis();
	}

	public _JDateChooser(Date date, String dateFormatString, IDateEditor dateEditor) {
		super(date, dateFormatString, dateEditor);
		initThis();
	}

	public _JDateChooser(String datePattern, String maskPattern, char placeholder) {
		super(datePattern, maskPattern, placeholder);
		initThis();
	}

	public _JDateChooser(Date date, String datePattern, String maskPattern, char placeholder) {
		super(date, datePattern, maskPattern, placeholder);
		initThis();
	}

	public _JDateChooser(JCalendar jcal, Date date, String dateFormatString, IDateEditor dateEditor, hasSelect asddsd) {
		super(jcal, date, dateFormatString, dateEditor, asddsd);
		initThis();
	}

	protected void initThis() {
		listeners = new ArrayList<DateListener>();

		((JTextField)getDateEditor()).setHorizontalAlignment(SwingConstants.CENTER);

		addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				doClick();
				setRequired(false);
			}
		});

		getCalendarButton().addPropertyChangeListener("enabled", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent arg0) {
				if(getCalendarButton().isEnabled()){
					setRequired(getDate() == null);
				}else{
					setRequired(false);
				}
			}
		});

		((JTextField)getDateEditor()).addPropertyChangeListener("editable", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent arg0) {
				if(new Boolean(arg0.getNewValue().toString())){
					setBackground(UIManager.getColor("FormattedTextField.background"));
				}else{
					setBackground(new Color(102, 205, 132));
				}
			}
		});

		((JTextField)getDateEditor()).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(getDate() == null && ((JTextField)getDateEditor()).isEditable()){
					setRequired(true);
					JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(_JDateChooser.this), "Invalid Date.", "Date", JOptionPane.WARNING_MESSAGE);
				}else{
					doClick();
					setRequired(false);
				}
			}
		});

		((JTextField)getDateEditor()).addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {
				if(isEditable()){
					if(getDate() == null){
						setRequired(true);
					}else{
						setRequired(false);
					}
				}
			}
			public void keyReleased(KeyEvent arg0) {
				if(isEditable()){
					if(getDate() == null){
						setRequired(true);
					}else{
						setRequired(false);
					}	
				}
			}
			public void keyTyped(KeyEvent arg0) {

			}
		});
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setEditable(boolean b){
		((JTextField)getDateEditor()).setEditable(b);
	}

	public Boolean isEditable() {
		return ((JTextField)getDateEditor()).isEditable();
	}

	public void setHorizontalAlignment(int alignment) {
		((JTextField)getDateEditor()).setHorizontalAlignment(alignment);
	}

	public int getHorizontalAlignment() {
		return ((JTextField)getDateEditor()).getHorizontalAlignment();
	}

	public void setText(String t) {
		((JTextField)getDateEditor()).setText(t);
	}

	public String getText() {
		return ((JTextField)getDateEditor()).getText();
	}

	public void setRequired(Boolean isRequired) {
		if(isRequired){
			label.setFont(new Font("DIALOG", Font.ITALIC, 12));
			label.setForeground(Color.RED);
			label.repaint();
		}else{
			label.setFont(new Font("DIALOG", Font.PLAIN, 12));
			label.setForeground(Color.BLACK);
			label.repaint();
		}
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public Boolean isRequired() {
		return (label.getFont().equals(new Font("DIALOG", Font.ITALIC, 12)) || label.getForeground().equals(Color.RED));
	}

	public String getDateString(){
		return new SimpleDateFormat("yyyy-MM-dd").format(getDate());
	}

	public Timestamp getTimestamp() {
		if(getDate() != null){
			return new Timestamp(getDate().getTime());
		}
		return null;
	}

	@Override
	public void addDateListener(DateListener listener) {
		if(listeners == null){
			listeners = new ArrayList<DateListener>();
		}
		listeners.add(listener);
	}

	@Override
	public void doClick() {
		dispatchEvent();
	}

	private void dispatchEvent() {
		final DateEvent event = new DateEvent(this);
		for (DateListener l : listeners) {
			dispatchRunnableOnEventQueue(l, event);
		}
	}

	private void dispatchRunnableOnEventQueue(final DateListener listener, final DateEvent event) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				listener.datePerformed(event);
			}
		});
	}
}

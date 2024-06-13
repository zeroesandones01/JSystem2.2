package FormattedTextField;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;

import org.jdesktop.swingx.JXFormattedTextField;

public class _JXFormattedTextField extends JXFormattedTextField implements KeyListener, FocusListener, InputDispatcher {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5851412486446566535L;
	
	private static DecimalFormatSymbols dfs;
	private static DecimalFormat dFormat;

	public static AbstractFormatterFactory DECIMAL = new DefaultFormatterFactory(getDecimalFormatter());
	public static AbstractFormatterFactory DECIMAL2 = new DefaultFormatterFactory(getDecimalFormatter2());
	public static AbstractFormatterFactory INTEGER = new DefaultFormatterFactory(getIntegerFormatter());
	public static AbstractFormatterFactory PERCENT = new DefaultFormatterFactory(getPercentFormatter());
	public static AbstractFormatterFactory FLOAT = new DefaultFormatterFactory(getFloatFormatter());

	public static AbstractFormatterFactory DECIMAL3 = new DefaultFormatterFactory(getDecimalFormatter3());
	public static AbstractFormatterFactory DECIMAL4 = new DefaultFormatterFactory(getDecimalFormatter4());

	//added by Alvin Gonzales (2015-06-29) due to unable to fire ActionListener
	private List<InputListener> inputListeners = new ArrayList<InputListener>();

	public _JXFormattedTextField() {
		initThis(JXFormattedTextField.LEFT);
	}

	public _JXFormattedTextField(int alignment) {
		initThis(alignment);
	}

	public _JXFormattedTextField(String promptText) {
		super(promptText);
		initThis(JXFormattedTextField.LEFT);
	}

	public _JXFormattedTextField(String promptText, int alignment) {
		super(promptText);
		initThis(alignment);
	}

	public _JXFormattedTextField(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
		initThis(JXFormattedTextField.LEFT);
	}

	public _JXFormattedTextField(String promptText, Color promptForeground, int alignment) {
		super(promptText, promptForeground);
		initThis(alignment);
	}

	public _JXFormattedTextField(String promptText, Color promptForeground, Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
		initThis(JXFormattedTextField.LEFT);
	}

	public _JXFormattedTextField(String promptText, Color promptForeground, Color promptBackground, int alignment) {
		super(promptText, promptForeground, promptBackground);
		initThis(alignment);
	}
	
	@Override
	public Document getDocument() {
		// TODO Auto-generated method stub
		return super.getDocument();
	}

	private void initThis(int alignment) {
		setHorizontalAlignment(alignment);

		/**
		 * Added 2014-11-06 by Alvin Gonzales
		 */
		addPropertyChangeListener("enabled", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(new Boolean(evt.getNewValue().toString())){
					
					if(isEditable()){
						setBackground(UIManager.getColor("FormattedTextField.background"));
					}else{
						setBackground(new Color(102, 205, 132));
					}
				}else{
					setBackground(new Color(212, 212, 210));
				}
			}
		});

		/**
		 * Added 2014-11-06 by Alvin Gonzales
		 */
		addPropertyChangeListener("editable", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(new Boolean(evt.getNewValue().toString())){
					setBackground(UIManager.getColor("FormattedTextField.background"));
				}else{
					setBackground(new Color(102, 205, 132));
				}
			}
		});

		addKeyListener(this);
		addFocusListener(this);

		/*this.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				if (input instanceof JXFormattedTextField) {
					JXFormattedTextField ftf = (JXFormattedTextField)input;
		             AbstractFormatter formatter = ftf.getFormatter();
		             if (formatter != null) {
		                 String text = ftf.getText();
		                 try {
		                      formatter.stringToValue(text);
		                      ftf.selectAll();
		                      return true;
		                  } catch (ParseException pe) {
		                      return false;
		                  }
		              }
		          }
		          return true;
			}
			public boolean shouldYieldFocus(JComponent input) {
		          return verify(input);
		      }
		});*/
	}

	public Object getValued() {
		if(getFormatterFactory() == _JXFormattedTextField.DECIMAL){
			//System.out.printf("Display value of getValue: %s%n", getValue());
			
			if(getValue() instanceof Long){
				/*System.out.println("Dumaan dito sa Long");
				System.out.printf("Display value of Long: %s%n", new BigDecimal((Long) getValue()));*/
				return new BigDecimal((Long) getValue());
			}else if(getValue() instanceof Double) {
				/*System.out.println("Dumaan dito sa Double");
				System.out.printf("Display value of Double: %s%n", new BigDecimal((Double) getValue()));*/
				
				return new BigDecimal((Double) getValue());
			}else{
				/*System.out.println("Dumaan dito sa BigDecimal");
				System.out.printf("Display value of BigDecimal: %s%n", (BigDecimal) getValue());
				System.out.printf("Display value of BigDecimal2: %s%n", getValue());*/
				
				return (BigDecimal) getValue();
			}
		}else{
			return getValue();
		}
	}
	
	public Object getValued2() {
		if(getFormatterFactory() == _JXFormattedTextField.DECIMAL){
			//System.out.printf("Display value of getValue: %s%n", getValue());
			
			if(getValue() instanceof Long){
				/*System.out.println("Dumaan dito sa Long");
				System.out.printf("Display value of Long: %s%n", new BigDecimal((Long) getValue()));*/
				//return new BigDecimal((Long) getValue());
				return BigDecimal.valueOf((Long) getValue());
			}else if(getValue() instanceof Double) {
				/*System.out.println("Dumaan dito sa Double");
				System.out.printf("Display value of Double: %s%n", new BigDecimal((Double) getValue()));*/
				
				//return new BigDecimal((Double) getValue());
				return BigDecimal.valueOf((Double) getValue());
			}else{
				return (BigDecimal) getValue();
			}
		}else{
			return getValue();
		}
	}

	public Integer getIntegerValue() {
		if(getFormatterFactory() == _JXFormattedTextField.INTEGER){
			System.out.printf("Value: %s%n", getValue().getClass());
			if(getValue() instanceof Long){
				return ((Long) getValue()).intValue();
			}else if(getValue() instanceof Double) {
				return ((Double) getValue()).intValue();
			}else{
				return (Integer) getValue();
			}
		}else{
			return null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			this.selectAll();
		}

		/*if(e.getKeyCode() == KeyEvent.VK_DELETE){
			if(isEditable()){
				this.setValue(null);
			}
		}*/
	}

	@Override
	public void keyTyped(KeyEvent e) { // EDIT CHARD (2015-04-29)
		/*char c = e.getKeyChar();
		if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD)) {
			e.consume();
		}*/
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		this.selectAll();
	}

	@Override
	public void focusLost(FocusEvent arg0) { }

	public static NumberFormatter getDecimalFormatter3() {//XXX
		NumberFormatter nf = new NumberFormatter(new NumericPlainDocument().getFormat());
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}
	
	public static NumberFormatter getDecimalFormatter4(){
		dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.'); //separator for the decimals
		//dfs.setGroupingSeparator(','); //separator for the thousands
		
		dFormat = new DecimalFormat ("#,##0.00");
		//NumberFormatter nf = new NumberFormatter(new NumericPlainDocument().getFormat());
		NumberFormatter nf = new NumberFormatter(dFormat);
		
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		nf.setOverwriteMode(true);
		return nf;
	}
	
	public static NumberFormatter getDecimalFormatter() {
		NumberFormatter nf = new NumberFormatter(new DecimalFormat("#,##0.00"));
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}
	
	public static NumberFormatter getDecimalFormatter2() {
		NumberFormatter nf = new NumberFormatter(new DecimalFormat("#,##0.0000"));
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}

	public static NumberFormatter getIntegerFormatter() {
		NumberFormatter nf = new NumberFormatter(new DecimalFormat("#"));
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}

	public static NumberFormatter getPercentFormatter() {
		NumberFormatter nf = new NumberFormatter(new DecimalFormat("##0.00"));
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}

	public static NumberFormatter getFloatFormatter() {
		NumberFormatter nf = new NumberFormatter(new DecimalFormat("###.#######"));
		nf.setAllowsInvalid(false);
		nf.setCommitsOnValidEdit(true);
		return nf;
	}

	/**
	 * 
	 * added by Alvin Gonzales (2015-06-29) due to unable to fire ActionListener
	 * 
	 */
	public void addInputListener(InputListener listener) {
		inputListeners.add(listener);
	}

	public void doClick() {
		dispatchLookupEvent();
	}

	private void dispatchLookupEvent() {
		final InputEvent event = new InputEvent(this/*, 1000, getActionCommand()*/);
		for (InputListener l : inputListeners) {
			dispatchRunnableOnLookupEventQueue(l, event);
		}
	}

	private void dispatchRunnableOnLookupEventQueue(final InputListener listener, final InputEvent event) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				listener.textfieldPerformed(event);
			}
		});
	}

}

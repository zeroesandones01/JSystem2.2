package Renderer;

import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import DateChooser._JDateChooser;
import Functions.FncLookAndFeel;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import components._JTableMain;

public class DateRenderer2 extends _JDateChooser implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3957123222541584752L;

	_JTableMain table;

	public DateRenderer2() {
		super("MM/dd/yyyy", "##/##/####", '_');
		initThis();
	}

	public DateRenderer2(IDateEditor dateEditor) {
		super(dateEditor);
		initThis();
	}

	public DateRenderer2(hasSelect selectHas) {
		super(selectHas);
		initThis();
	}

	public DateRenderer2(Date date) {
		super(date);
		initThis();
	}

	public DateRenderer2(Date date, String dateFormatString) {
		super(date, dateFormatString);
		initThis();
	}

	public DateRenderer2(Date date, String dateFormatString, IDateEditor dateEditor) {
		super(date, dateFormatString, dateEditor);
		initThis();
	}

	public DateRenderer2(String datePattern, String maskPattern, char placeholder) {
		super(datePattern, maskPattern, placeholder);
		initThis();
	}

	public DateRenderer2(Date date, String datePattern, String maskPattern, char placeholder) {
		super(date, datePattern, maskPattern, placeholder);
		initThis();
	}

	public DateRenderer2(JCalendar jcal, Date date, String dateFormatString, IDateEditor dateEditor, hasSelect asddsd) {
		super(jcal, date, dateFormatString, dateEditor, asddsd);
		initThis();
	}

	protected void initThis() {
		((JTextField)this.getDateEditor()).setHorizontalAlignment(SwingConstants.CENTER);
		((JTextField)this.getDateEditor()).setFont(FncLookAndFeel.systemFont_Plain);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.table = (_JTableMain) table;
		this.setDate((Date) value);
		//this.getCalendarButton().setEnabled(this.table.isCellEditable(row, column));
		this.getCalendarButton().setVisible(false);

		return this;
	}

}

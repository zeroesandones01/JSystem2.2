package components;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class _JTextAreaEditor extends DefaultCellEditor {
	
   protected JScrollPane scrollpane;
   protected JTextArea textarea;
  
   public _JTextAreaEditor() {
      super(new JCheckBox());
      scrollpane = new JScrollPane();
      textarea = new JTextArea(); 
      textarea.setLineWrap(true);
      textarea.setWrapStyleWord(true);
      textarea.setEnabled(true);
      textarea.setEditable(false);
      //textarea.setBorder(new TitledBorder("This is a JTextArea"));
      scrollpane.getViewport().add(textarea);
      //scrollpane.setViewportView(textarea);
      
      //textarea.setEnabled(true);
   }
  
   public Component getTableCellEditorComponent(JTable table, Object value,
                                   boolean isSelected, int row, int column) {
      textarea.setText((String) value);
      //scrollpane.setEnabled(true);
      return scrollpane;
   }
  
   public Object getCellEditorValue() {
      return textarea.getText();
   }
}
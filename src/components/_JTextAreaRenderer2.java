package components;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

public class _JTextAreaRenderer2 extends JScrollPane implements TableCellRenderer{
   private JTextArea textarea;
  
   public _JTextAreaRenderer2() {
      textarea = new JTextArea();
      textarea.setLineWrap(true);
      textarea.setWrapStyleWord(true);
      textarea.setEnabled(true);
      textarea.setEditable(false);
      //textarea.setEditable(false);
      //textarea.setBorder(new TitledBorder("This is a JTextArea"));
      getViewport().add(textarea);
      
   }
  
   public Component getTableCellRendererComponent(JTable table, Object value,
                                  boolean isSelected, boolean hasFocus,
                                  int row, int column){
      if (isSelected) {
    	 System.out.println("Dumaan dito sa selected");
         setForeground(table.getSelectionForeground());
         setBackground(table.getSelectionBackground());
         textarea.setForeground(table.getSelectionForeground());
         textarea.setBackground(table.getSelectionBackground());
      } else {
         setForeground(table.getForeground());
         setBackground(table.getBackground());
         textarea.setForeground(table.getForeground());
         textarea.setBackground(table.getBackground());
      }
  
      textarea.setText((String) value);
      textarea.setCaretPosition(0);
      //textarea.setCr
      return this;
   }
}
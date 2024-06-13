package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class model_deposit_assignment extends DefaultTableModel {

          private static final long serialVersionUID = 3899473330894424021L;
          private boolean editable = false;
          boolean[] COLUMNS_EDITABLE;   
          
          public model_deposit_assignment() {
                    InitGUI();
          }

          public model_deposit_assignment(int arg0, int arg1) {
                    super(arg0, arg1);

          }

          public model_deposit_assignment(Vector arg0, int arg1) {
                    super(arg0, arg1);

          }

          public model_deposit_assignment(Object[] arg0, int arg1) {
                    super(arg0, arg1);

          }

          public model_deposit_assignment(Vector arg0, Vector arg1) {
                    super(arg0, arg1);

          }

          public model_deposit_assignment(Object[][] arg0, Object[] arg1) {
                    super(arg0, arg1);

          }

          private void InitGUI() {
                    setColumnIdentifiers(COLUMNS);

                    COLUMNS_EDITABLE = new boolean[] {
                              false,
                              false,
                              false,
                              false,
                              false
                    };
          }
          
          public String [] COLUMNS = new String[] {
                    "Part ID",
                    "Particular",
                    "Amount",   
                    "Due Type", 
                    "Rec ID"
          };
          
          public Class [] CLASS_TYPES = new Class[] {
                    String.class,
                    Object.class,
                    BigDecimal.class, 
                    String.class, 
                    Integer.class
          };
          
          public Class getColumnClass(int columnIndex) {
                    return CLASS_TYPES[columnIndex];
          }

          public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return COLUMNS_EDITABLE[columnIndex];
          }
          
          public boolean isCellEditable(int columnIndex) {
                    return COLUMNS_EDITABLE[columnIndex];
          }
          
          public void clear() {
                    FncTables.clearTable(this);
          }
          
          public boolean isEditable(){
                    return editable;
          }
          
          public void setEditable(boolean editable){
                    this.editable = editable;
          }
}

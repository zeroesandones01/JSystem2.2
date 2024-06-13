package Accounting.FixedAssets;

import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
//import clientServicing.classes.SelectClass;
//import functions.FncUDIRecords;
//import functions.FncUDIRecords;

public class _AddItem {
	
	public static void updateItem(String item_id, String item_name, String category_id){
		pgUpdate db= new pgUpdate();
		String strSQL =
			"UPDATE tbl_item \n" +
			"SET \n" +
			"item_name='"+item_name+"', \n" +
			"category_id="+category_id+" \n" +
			"WHERE item_id="+item_id+" \n";
		
		db.executeUpdate(strSQL, false);
		db.commit();

		/*try {
			FncUDIRecords.udirec(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/

		/*try {
			FncUDIRecords.commit();
			JOptionPane.showMessageDialog(null, "Item has been updated.", "Save", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	
	public static String getNewItemID(){
		//SelectClass db = new SelectClass();
				pgSelect db= new pgSelect();
				String strSQL="select to_char(max(item_id) + 1,'FM000000') from tbl_item";
				db.select(strSQL);
				return db.Result[0][0].toString();
	}
	public static String getCategory(){
		return "select category_id as \"Category ID\", category_name as \"Category Name\" from tbl_category order by category_name";
	}
	public static  void displayAssetDetail(String item_id){
		pgSelect db= new pgSelect();
		String strSQL="select a.item_id,a.item_name,b.category_name,b.category_id\n" + 
				"from tbl_item a\n" + 
				"left join tbl_category  b on a.category_id=b.category_id\n" + 
				"where item_id=\"+item_id+\"";
		db.select(strSQL);
		if(db.isNotNull()){
			
		}
		
	}
	public static Boolean checkItemIfExist(String item_name){
		pgSelect db= new pgSelect();
		//SelectClass db = new SelectClass();
		//db.setQuerySql("select item_id from tbl_item where item_name ~* '"+item_name+"' ");
		String strSQL="select item_id from tbl_item where item_name ~* '"+item_name+"' ";
		
		db.select(strSQL);
		
		return db.isNull() ? false:true;
	}
	public static void addItem(String item_id, String item_name, String category_id){
		pgUpdate db= new pgUpdate();
		String strSQL =
			"INSERT INTO tbl_item( \n" +
			"item_id, \n" +
			"item_name, \n" +
			"category_id) \n" +
			"VALUES (" +
			""+item_id+", \n" +//item_id
			"'"+item_name+"', \n" +//item_name
			""+category_id+") \n";//category_id
		db.executeUpdate(strSQL,false);
		db.commit();

		/*try {
			FncUDIRecords.udirec(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/

		/*try {
			FncUDIRecords.commit();
			JOptionPane.showMessageDialog(null, "Item has been added.", "Save", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
}
	
	

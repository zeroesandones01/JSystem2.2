package Home;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;

public class _Home_JSystem {

	static Map<String, ArrayList<String>> mapDbParent = new HashMap<String, ArrayList<String>>();
	static Map<String, String> mapDbModule = new HashMap<String, String>();

	public static void dbParents() {
		String SQL = "";
		
		/*if(UserInfo.Branch.trim().equals("10")){
			System.out.println("Dumaan dito sa openhouse");
			SQL = "SELECT module, privileges, format('''%s''', array_to_string(parent, ''', ''')::VARCHAR) FROM public.mf_openhouse_privileges WHERE emp_code = '"+ UserInfo.EmployeeCode +"' ORDER BY privileges;";
		}else{*/
			SQL = "SELECT module, privileges, format('''%s''', array_to_string(parent, ''', ''')::VARCHAR) FROM public.mf_privileges WHERE emp_code = '"+ UserInfo.EmployeeCode +"' AND status_id = 'A' ORDER BY privileges;";
		//}
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display SQL for privileges", SQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String module = (String) db.getResult()[x][0];
				String privileges = (String) db.getResult()[x][1];
				ArrayList<String> parent = new ArrayList<String>(Arrays.asList(((String) db.getResult()[x][2]).split(", ")));

				mapDbParent.put(privileges, parent);
				mapDbModule.put(privileges, module);
			}
		}
	}

	public static void menuAccess(JMenuBar menuBar) {
		dbParents();

		List<String> listException = Arrays.asList(new String[]{"File", "Window", "Help"});
		Map<String, ArrayList<String>> mapMenu = FncGlobal.mapMenu;

		for(int x=0; x < menuBar.getMenuCount(); x++){
			try {
				JMenu menu = menuBar.getMenu(x);
				String module = menu.getText().replace("'", "''");
				//System.out.printf("Display module name: %s%n", module);

				//System.out.printf("%s%n", menuBar.getMenu(x).getText());
				if(!mapMenu.containsKey(module)){
					mapMenu.put(module, new ArrayList<String>());
				}

				if(!listException.contains(module)){
					if(mapDbModule.containsValue(module)){
						menoLooper(mapMenu, module, menu);
					}else{
						//menu.setVisible(false);
						if(UserInfo.ADMIN) {
							//System.out.println("I am admin");
							menu.setEnabled(true);
						}else {
							menu.setEnabled(false);
						}
					}
				}
			} catch (NullPointerException e) {
				//e.printStackTrace();
			}
		}
	}

	public static void menoLooper(Map<String, ArrayList<String>> mapMenu, String menuText, JMenu menu) {
		for(Component comp : menu.getMenuComponents()){
			String simpleName = comp.getClass().getSimpleName();

			if(simpleName.equals("JMenu")){
				JMenu menuParent = (JMenu) comp;
				//String parent = String.format("'%s'", menuParent.getText());

				ArrayList<String> mapParents = new ArrayList<String>();
				for(Entry entries : mapDbParent.entrySet()){
					ArrayList<String> value = (ArrayList<String>) entries.getValue();
					for(String val : value){
						mapParents.add(val);
					}
				}

				//System.out.printf("  %s%n", parent);
				
				//commented this part by Alvin Gonzales (2015-12-29)
				//if(mapParents.contains(parent)){
					menoLooper(mapMenu, menuText, menuParent);
				//}else{
					//menuParent.setVisible(false);
				//}
			}else if(simpleName.equals("JMenuItem")){

				JMenuItem menuItem = ((JMenuItem) comp);
				String menuitemText = menuItem.getText();

				ArrayList<String> listParent = new ArrayList<String>();
				loopParent(listParent, menuItem);

				ArrayList<String> listMenu = mapMenu.get(menuText);
				listMenu.add(menuitemText);

				mapMenu.put(menuText, listMenu);

				String privileges = ((JMenuItem) comp).getText();
				
				//System.out.printf("Value of privileges: %s%n", privileges);
				addSystemModule(privileges);

				if(mapDbParent.containsKey(privileges)){
					ArrayList<String> listDbParent = mapDbParent.get(privileges);
					if(listDbParent.equals(listParent)){
						//System.out.printf("Privileges: %s%n", privileges);
					}else{
						//System.out.printf("Privileges: %s%n", privileges);
						//menuItem.setVisible(false);
						menuItem.setEnabled(false);
					}
				}else{
					//menuItem.setVisible(false);
					menuItem.setEnabled(false);
					if(UserInfo.ADMIN) {
						menuItem.setEnabled(true);
					}
				}

				//System.out.printf("     %s: %s%n", privileges, listParent.toString());
				FncGlobal.mapParent.put(privileges, listParent);
			}else{
				//System.out.printf("     Other: %s%n", simpleName);
			}
		}
	}
	
	private static void addSystemModule(String module) {
		
		String module_name = module.replace("'", "''");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_jsystem_module('"+module_name+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
	}

	public static void loopParent(ArrayList<String> listParent, Component menu) {
		try {
			JPopupMenu menuPopup = (JPopupMenu) menu.getParent();
			JMenu actMenu = (JMenu) menuPopup.getInvoker();

			//System.out.printf("                              %-30s%n", actMenu.getText());
			listParent.add(String.format("'%s'", actMenu.getText()));

			loopParent(listParent, actMenu);
		} catch (ClassCastException e) {
			//e.printStackTrace();
		}
	}

}

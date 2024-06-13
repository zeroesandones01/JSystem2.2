package Functions;

import javax.swing.JComponent;

public class FncActionMap {

	public static void print(JComponent comp) {

		FncSystem.title(comp.getClass().getSimpleName());
		for(Object key : comp.getActionMap().allKeys()) {
			System.out.printf("%-35s %s%n", key, comp.getActionMap().get(key));
		}
	}

	public static void printInputMap(JComponent comp) {

		FncSystem.title(comp.getClass().getSimpleName());
		for(Object key : comp.getInputMap().allKeys()) {
			System.out.printf("%-35s %s%n", key, comp.getActionMap().get(key));
		}
	}
}

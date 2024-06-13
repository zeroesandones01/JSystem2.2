package Functions;

public class FncSystem {

	public static void title(String title) {
		System.out.printf("%n********************%s!", title);
	}

	public static void out(String title, String strSQL) {
		System.out.printf("%n********************%s!%n%s%n%n", title, strSQL);
	}
}
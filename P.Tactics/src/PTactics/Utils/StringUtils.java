package PTactics.Utils;

public class StringUtils {
	/*
	 * This class is heavily inspired in a class from the project of TP1 although it is written by us in its totality.
	 * It's purpose is to provide support for common string manipulations and in doing so facilitating the display of information.
	*/
	public static String repeat(String s, int n) {
		String rs = "";
		
		for (int i = 0; i < n; i++) {
			rs += s;
		}
		
		return rs;
	}
}

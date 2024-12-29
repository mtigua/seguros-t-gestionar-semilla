package seguros.ti.gestionar.semilla.utils;

import java.io.UnsupportedEncodingException;

public class Utils {

	public static String encodeString(String string) {
		try {
			string = new String(string.getBytes("iso-8859-1"), "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
	
}

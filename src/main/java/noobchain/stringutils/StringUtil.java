package noobchain.stringutils;

import java.security.*;
import java.util.Base64;

public class StringUtil {
	

	private StringUtil(){
		super();
	}

	//Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"
	public static String getDifficultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}
	
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	

}

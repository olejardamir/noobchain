package noobchain.stringutils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ApplySha256 {

    //Applies Sha256 to a string and returns the result.
    public String applySha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //Applies sha256 to our input,
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(); // This will contain hash as hexadecimal
        iterateHashBytes(hash, hexString);
        return hexString.toString();
    }

    private void iterateHashBytes(byte[] hash, StringBuilder hexString) {
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
    }

}

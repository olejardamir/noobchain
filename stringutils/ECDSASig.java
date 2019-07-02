package noobchain.stringutils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class ECDSASig {

    //Applies ECDSA Signature and returns the result ( as bytes ).
    public byte[] applyECDSASig(PrivateKey privateKey, String input) throws Exception {
        Signature dsa;
        byte[] output;
        dsa = Signature.getInstance("ECDSA", "BC");
        dsa.initSign(privateKey);
        byte[] strByte = input.getBytes();
        dsa.update(strByte);
        output = dsa.sign();

        return output;
    }

    //Verifies a String signature
    public boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) throws Exception {
        Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(data.getBytes());
        return ecdsaVerify.verify(signature);
    }

}

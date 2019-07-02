package noobchain.transaction.transactionoutput;

import java.security.PublicKey;

public class Header {

    private String id;
    private PublicKey reciepient; //also known as the new owner of these coins.
    private float value; //the amount of coins they own

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public PublicKey getReciepient() {
        return reciepient;
    }

    void setReciepient(PublicKey reciepient) {
        this.reciepient = reciepient;
    }

    public float getValue() {
        return value;
    }

    void setValue(float value) {
        this.value = value;
    }
}

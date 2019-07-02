package noobchain.transaction.transactionoutput;

import java.security.PublicKey;

public class Header {

    private String id;
    private PublicKey recipient; //also known as the new owner of these coins.
    private float value; //the amount of coins they own

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    void setRecipient(PublicKey recipient) {
        this.recipient = recipient;
    }

    public float getValue() {
        return value;
    }

    void setValue(float value) {
        this.value = value;
    }
}

package noobchain.transaction;

import noobchain.transaction.transactioninput.TransactionInput;
import noobchain.transaction.transactionoutput.TransactionOutput;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Header {

    private String transactionId; //Contains a hash of transaction*
    private PublicKey sender = null; //Senders address/public key.
    private PublicKey reciepient; //Recipients address/public key.
    private float value; //Contains the amount we wish to send to the recipient.
    private byte[] signature; //This is to prevent anybody else from spending funds in our wallet.




    List<TransactionInput> inputs = new ArrayList<>();
    final List<TransactionOutput> outputs = new ArrayList<>();

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    void setSender(PublicKey sender) {
        this.sender = sender;
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

    byte[] getSignature() {
        return signature;
    }

    void setSignature(byte[] signature) {
        this.signature = signature;
    }

    void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }


    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

}

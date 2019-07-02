package noobchain.transaction.transactioninput;

import noobchain.transaction.transactionoutput.TransactionOutput;

public class Header {
    private String transactionOutputId; //Reference to TransactionOutputs -> transactionId
    private TransactionOutput utxo; //Contains the Unspent transaction output

    public String getTransactionOutputId() {
        return transactionOutputId;
    }

    void setTransactionOutputId(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public TransactionOutput getUtxo() {
        return utxo;
    }

    public void setUxto(TransactionOutput transactionOutput) {
        this.utxo = transactionOutput;
    }

}

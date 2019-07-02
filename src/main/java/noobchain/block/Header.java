package noobchain.block;

import noobchain.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private String hash;
    private String previousHash;
    private final ArrayList<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.
    private String merkleRoot;
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    void setHash(String hash) {
        this.hash = hash;
    }

    void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    String getMerkleRoot() {
        return merkleRoot;
    }

    void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    long getTimeStamp() {
        return timeStamp;
    }

    void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    int getNonce() {
        return nonce;
    }

    void setNonce(int nonce) {
        this.nonce = nonce;
    }

    void addTransactionToArray(Transaction transaction) {
        this.transactions.add(transaction);
    }
}

package noobchain.stringutils;

import noobchain.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MerkleRoot {

    public String getMerkleRoot(List<Transaction> transactions) throws Exception {
        int count = transactions.size();
        List<String> previousTreeLayer = new ArrayList<>();
        for(Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.getTransactionId());
        }
        List<String> treeLayer = previousTreeLayer;
        treeLayer = itterateTreeLayer(count, previousTreeLayer, treeLayer);
        return (treeLayer.size() == 1) ? treeLayer.get(0) : "";
    }

    private List<String> itterateTreeLayer(int count, List<String> previousTreeLayer, List<String> treeLayer) throws Exception {
        while(count > 1) {
            treeLayer = new ArrayList<>();
            for(int i=1; i < previousTreeLayer.size(); i+=2) {
                treeLayer.add(new ApplySha256().applySha256(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }
        return treeLayer;
    }

}

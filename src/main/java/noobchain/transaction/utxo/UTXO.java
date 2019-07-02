package noobchain.transaction.utxo;

import noobchain.transaction.transactionoutput.TransactionOutput;

import java.util.HashMap;
import java.util.Map;

public class UTXO {

    private static final HashMap<String, TransactionOutput> UTXO = new HashMap<>();

    public static Map<String, TransactionOutput> getUTXO() {
        return UTXO;
    }

    public void put(String key, TransactionOutput value) {
        UTXO.put(key, value);
    }

    public TransactionOutput get(String key) {
        return UTXO.get(key);
    }

    public void remove(String key) {
        UTXO.remove(key);
    }
}

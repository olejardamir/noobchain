package noobchain.transaction.utxo;

import noobchain.transaction.transactionoutput.TransactionOutput;

import java.util.HashMap;
import java.util.Map;

public class UTXO {

    private static HashMap<String, TransactionOutput> UTXO = new HashMap<>();

    public UTXO() throws Exception {
    }

    public static void put(String key, TransactionOutput value) {

        UTXO.put(key, value);
    }

    public static TransactionOutput get(String key) {
        return UTXO.get(key);
    }

    public static void remove(String key) {
        UTXO.remove(key);
    }

    public static Iterable<? extends Map.Entry<String, TransactionOutput>> entrySet() {
        return UTXO.entrySet();
    }
}

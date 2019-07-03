package noobchain.wallet;
import noobchain.NoobChain;
import noobchain.transaction.Transaction;
import noobchain.transaction.transactioninput.TransactionInput;
import noobchain.transaction.transactionoutput.TransactionOutput;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private final HashMap<String, TransactionOutput> utxos = new HashMap<>();
	private String message = "";

	public Wallet() throws Exception {
		generateKeyPair();
	}

	private void generateKeyPair() throws Exception {

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
		// Initialize the key generator and generate a KeyPair
		keyGen.initialize(ecSpec, random); //256
		KeyPair keyPair = keyGen.generateKeyPair();
		// Set the public and private keys from the keyPair
		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();


	}

	public double getBalance() {
		double total = 0;
		total = myCoinsToUnspentTransactions(total);
		return total;
	}

	private double myCoinsToUnspentTransactions(double total) {
		for (Map.Entry<String, TransactionOutput> item: NoobChain.entrySet()){
			TransactionOutput utxo = item.getValue();
			if(utxo.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
				utxos.put(utxo.getId(),utxo); //add it to our list of unspent transactions.
				total += utxo.getValue();
			}
		}
		return total;
	}

	public Transaction sendFunds(PublicKey recipient, double value ) throws Exception {
		if(getBalance() < value) {
			message = "#Not Enough funds to send transaction. transaction Discarded.";
			return null;
		}
		List<TransactionInput> inputs = new ArrayList<>();
		double total = 0;
		iterateFunds(value, inputs, total);
		Transaction newTransaction = new Transaction(publicKey, recipient , value, inputs);
		newTransaction.generateSignature(privateKey);
		removeFunds(inputs);
		return newTransaction;
	}

	private void removeFunds(List<TransactionInput> inputs) {
		for(TransactionInput input: inputs){
			utxos.remove(input.getTransactionOutputId());
		}
	}

	private void iterateFunds(double value, List<TransactionInput> inputs, double total) {
		for (Map.Entry<String, TransactionOutput> item: utxos.entrySet()){
			TransactionOutput utxo = item.getValue();
			total += utxo.getValue();
			inputs.add(new TransactionInput(utxo.getId()));
			if(total > value) break;
		}
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
}



package noobchain;
import noobchain.block.Block;
import noobchain.transaction.Transaction;
import noobchain.transaction.transactionoutput.TransactionOutput;
import noobchain.wallet.Wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NoobChain {

	private final ArrayList<Block> blockchain = new ArrayList<>();
	private static final HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
	private final int difficulty = 3;
	private static final float minimumTransaction = 0.1f;
	private Transaction genesisTransaction;



	void initiateGenesisTransaction(Wallet walletA, Wallet coinbase) throws Exception {
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f, null);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 //manually sign the genesis transaction
		genesisTransaction.setTransactionId("0"); //manually set the transaction id
		genesisTransaction.getOutputs().add(new TransactionOutput(genesisTransaction.getReciepient(), genesisTransaction.getValue(), genesisTransaction.getTransactionId())); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.getOutputs().get(0).getId(), genesisTransaction.getOutputs().get(0)); //its important to store our first transaction in the UTXOs list.
	}


	void addBlock(Block newBlock) throws Exception {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	public static Map<String, TransactionOutput> getUTXOs() {
		return UTXOs;
	}

	public static float getMinimumTransaction() {
		return minimumTransaction;
	}

	public Transaction getGenesisTransaction() {
		return this.genesisTransaction;
	}

	public int getDifficulty() {
		return this.difficulty;
	}


	public ArrayList<Block> getBlockchain() {
		return blockchain;
	}
}



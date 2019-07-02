package noobchain;
import noobchain.block.Block;
import noobchain.chain.Verify;
import noobchain.transaction.Transaction;
import noobchain.transaction.transactionoutput.TransactionOutput;
import noobchain.wallet.Wallet;

import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NoobChain {
	
	private static final ArrayList<Block> blockchain = new ArrayList<>();
	private static final HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
	private static final int difficulty = 3;
	private static final float minimumTransaction = 0.1f;
	private static Transaction genesisTransaction;

	public static void main(String[] args) throws Exception {
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncy castle as a Security Provider

		//Create wallets:
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		Wallet coinbase = new Wallet();

		//create genesis transaction, which sends 100 NoobCoin to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f, null);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 //manually sign the genesis transaction
		genesisTransaction.setTransactionId("0"); //manually set the transaction id
		genesisTransaction.getOutputs().add(new TransactionOutput(genesisTransaction.getReciepient(), genesisTransaction.getValue(), genesisTransaction.getTransactionId())); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.getOutputs().get(0).getId(), genesisTransaction.getOutputs().get(0)); //its important to store our first transaction in the UTXOs list.

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);

		//testing
		Block block1 = new Block(genesis.getHash());
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block2 = new Block(block1.getHash());
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block3 = new Block(block2.getHash());
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds( walletA.getPublicKey(), 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Verify v = new Verify();
		boolean isValid =  v.isChainValid(difficulty,genesisTransaction,blockchain);
		System.out.println(v.getMessage());

	}



	private static void addBlock(Block newBlock) throws Exception {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	public static Map<String, TransactionOutput> getUTXOs() {
		return UTXOs;
	}

	public static float getMinimumTransaction() {
		return minimumTransaction;
	}
}



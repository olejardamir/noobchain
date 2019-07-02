package noobchain.block;

import noobchain.stringutils.ApplySha256;
import noobchain.stringutils.MerkleRoot;
import noobchain.stringutils.StringUtil;
import noobchain.transaction.Transaction;

import java.util.Date;

public class Block extends Header {


	//block Constructor.
	public Block(String previousHash) throws Exception {
		super.setPreviousHash(previousHash);
		super.setTimeStamp(new Date().getTime());
		super.setHash(calculateHash()); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() throws Exception {
		return new ApplySha256().applySha256( super.getPreviousHash() + super.getTimeStamp() + super.getNonce() + super.getMerkleRoot() );
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) throws Exception {
		super.setMerkleRoot(new MerkleRoot().getMerkleRoot(super.getTransactions()));
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		itterateHashDifficulty(difficulty, target);
		System.out.println("block Mined!!! : " + super.getHash());
	}

	private void itterateHashDifficulty(int difficulty, String target) throws Exception {
		while(!super.getHash().substring( 0, difficulty).equals(target)) {
			super.setNonce(super.getNonce()+1);
			super.setHash(calculateHash());
		}
	}

	//Add transactions to this block
	public void addTransaction(Transaction transaction) throws Exception {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return;

		if((!super.getPreviousHash().equals("0")) && !transaction.processTransaction()) {
				System.out.println("transaction failed to process. Discarded.");
				return;
		}

		super.addTransactionToArray(transaction);
		System.out.println("transaction Successfully added to block");
	}


}

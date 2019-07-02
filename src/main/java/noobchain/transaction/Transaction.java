package noobchain.transaction;
import noobchain.NoobChain;
import noobchain.transaction.transactioninput.TransactionInput;
import noobchain.transaction.transactionoutput.TransactionOutput;
import noobchain.stringutils.ApplySha256;
import noobchain.stringutils.ECDSASig;
import noobchain.stringutils.StringUtil;

import java.security.*;
import java.util.List;

public class Transaction extends Header {

	private int sequence = 0; //A rough count of how many transactions have been generated
	
	// Constructor: 
	public Transaction(PublicKey from, PublicKey to, float value,  List<TransactionInput> inputs) {
		super.setSender(from);
		super.setReciepient(to);
		super.setValue(value);
		super.setInputs(inputs);
	}
	
	public boolean processTransaction() throws Exception {
		
		if(verifySignature()) {
			System.out.println("#transaction Signature failed to verify");
			return false;
		}
				
		//Gathers transaction inputs (Making sure they are unspent):
		for(TransactionInput i : inputs) {
			i.setUxto(NoobChain.getUTXOs().get(i.getTransactionOutputId()));
		}

		//Checks if transaction is valid:
		if(getInputsValue() < NoobChain.getMinimumTransaction()) {
			System.out.println("transaction Inputs too small: " + getInputsValue());
			System.out.println("Please enter the amount greater than " + NoobChain.getMinimumTransaction());
			return false;
		}
		
		//Generate transaction outputs:
		float leftOver = getInputsValue() - super.getValue(); //get value of inputs then the left over change:
		super.setTransactionId(calculateHash());
		outputs.add(new TransactionOutput( super.getReciepient(), super.getValue(), super.getTransactionId())); //send value to recipient
		outputs.add(new TransactionOutput( super.getSender(), leftOver, super.getTransactionId())); //send the left over 'change' back to sender
				
		//Add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			NoobChain.getUTXOs().put(o.getId() , o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.getUtxo() == null) continue; //if transaction can't be found skip it
			NoobChain.getUTXOs().remove(i.getUtxo().getId());
		}
		
		return true;
	}
	
	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.getUtxo() == null) continue; //if transaction can't be found skip it, This behavior may not be optimal.
			total += i.getUtxo().getValue();
		}
		return total;
	}
	
	public void generateSignature(PrivateKey privateKey) throws Exception {
		String data = StringUtil.getStringFromKey(super.getSender()) + StringUtil.getStringFromKey(super.getReciepient()) + super.getValue();
		super.setSignature(new ECDSASig().applyECDSASig(privateKey,data));
	}
	
	public boolean verifySignature() throws Exception {
		String data = StringUtil.getStringFromKey(super.getSender()) + StringUtil.getStringFromKey(super.getReciepient()) + super.getValue();
		return !new ECDSASig().verifyECDSASig(super.getSender(), data, super.getSignature());
	}
	
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.getValue();
		}
		return total;
	}
	
	private String calculateHash() throws Exception {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return new ApplySha256().applySha256(
				StringUtil.getStringFromKey(super.getSender()) +
				StringUtil.getStringFromKey(super.getReciepient()) +
						super.getValue() + sequence
				);
	}


}

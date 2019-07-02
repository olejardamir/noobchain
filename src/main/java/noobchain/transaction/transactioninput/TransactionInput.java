package noobchain.transaction.transactioninput;

public class TransactionInput extends Header{

	//Constructor
	public TransactionInput(String transactionOutputId) {
		super.setTransactionOutputId(transactionOutputId);
	}

}

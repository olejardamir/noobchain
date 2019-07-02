package noobchain.transaction.transactionoutput;


import noobchain.stringutils.ApplySha256;
import noobchain.stringutils.StringUtil;

import java.security.PublicKey;

public class TransactionOutput extends Header{


	//Constructor
	public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) throws Exception {
		super.setReciepient(reciepient);
		super.setValue(value);
		//the id of the transaction this output was created in
		super.setId(new ApplySha256().applySha256(StringUtil.getStringFromKey(reciepient)+ value +parentTransactionId));
	}
	
	//Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == super.getReciepient());
	}

}

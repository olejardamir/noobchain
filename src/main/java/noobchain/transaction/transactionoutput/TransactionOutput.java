package noobchain.transaction.transactionoutput;


import noobchain.stringutils.ApplySha256;
import noobchain.stringutils.StringUtil;

import java.security.PublicKey;

public class TransactionOutput extends Header{


	//Constructor
	public TransactionOutput(PublicKey  recipient, float value, String parentTransactionId) throws Exception {
		super.setRecipient(recipient);
		super.setValue(value);
		//the id of the transaction this output was created in
		super.setId(new ApplySha256().applySha256(StringUtil.getStringFromKey(recipient)+ value +parentTransactionId));
	}



    //Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == super.getRecipient());
	}

}

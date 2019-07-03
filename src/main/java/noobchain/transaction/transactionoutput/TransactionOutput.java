package noobchain.transaction.transactionoutput;


import noobchain.stringutils.ApplySha256;
import noobchain.stringutils.StringUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.PublicKey;

public class TransactionOutput extends Header implements Externalizable {

	//Constructor
	public TransactionOutput(PublicKey  recipient, double value, String parentTransactionId) throws Exception {
		super.setRecipient(recipient);
		super.setValue(value);
		//the id of the transaction this output was created in
		super.setId(new ApplySha256().applySha256(StringUtil.getStringFromKey(recipient)+ value +parentTransactionId));
	}

	public TransactionOutput() {

	}


	//Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == super.getRecipient());
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		//TODO ??
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
		//TODO ??
	}
}

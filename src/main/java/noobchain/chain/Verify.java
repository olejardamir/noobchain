package noobchain.chain;

import noobchain.block.Block;
import noobchain.transaction.Transaction;
import noobchain.transaction.transactioninput.TransactionInput;
import noobchain.transaction.transactionoutput.TransactionOutput;
import noobchain.transaction.utxo.UTXO;

import java.util.ArrayList;
import java.util.HashMap;

public class Verify {
    private String message = "";

    public boolean isChainvalid(int difficulty, Transaction genesisTransaction, ArrayList<Block> blockchain) throws Exception {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        UTXO tempUTXOs = new UTXO(); //a temporary working list of unspent transactions at a given block state.
        tempUTXOs.put(genesisTransaction.getOutputs().get(0).getId(), genesisTransaction.getOutputs().get(0));

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                message = "#Current Hashes not equal";
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                message = "#Previous Hashes not equal";
                return false;
            }
            //check if hash is solved
            if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
                message = "#This block hasn't been mined";
                return false;
            }

            //loop through blockchain transactions:
            TransactionOutput tempOutput;
            for(int t=0; t <currentBlock.getTransactions().size(); t++) {
                Transaction currentTransaction = currentBlock.getTransactions().get(t);

                if(currentTransaction.verifySignature()) {
                    message = "#Signature on transaction(" + t + ") is invalid";
                    return false;
                }
                if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    message = "#Inputs are note equal to outputs on transaction(" + t + ")";
                    return false;
                }

                for(TransactionInput input: currentTransaction.getInputs()) {
                    tempOutput = tempUTXOs.get(input.getTransactionOutputId());

                    if(tempOutput == null) {
                        message = "#Referenced input on transaction(" + t + ") is missing";
                        return false;
                    }

                    if(input.getUtxo().getValue() != tempOutput.getValue()) {
                        message = "#Referenced input transaction(" + t + ") value is invalid";
                        return false;
                    }

                    tempUTXOs.remove(input.getTransactionOutputId());
                }

                for(TransactionOutput output: currentTransaction.getOutputs()) {
                    tempUTXOs.put(output.getId(), output);
                }

                if( currentTransaction.getOutputs().get(0).getRecipient() != currentTransaction.getReciepient()) {
                    message = "#transaction(" + t + ") wrong output recipient";
                    return false;
                }
                if( currentTransaction.getOutputs().get(1).getRecipient() != currentTransaction.getSender()) {
                    message = "#transaction(" + t + ") output 'change' is not a sender.";
                    return false;
                }

            }

        }
        message = "Blockchain is valid";
        return true;
    }

    public String getMessage() {
    return message;
    }

}

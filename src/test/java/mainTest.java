import noobchain.NoobChain;
import noobchain.block.Block;
import noobchain.chain.Verify;
import noobchain.wallet.Wallet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.Security;

public class mainTest {

    private NoobChain noobChain = new NoobChain();
    private Block genesis;

    public mainTest() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncy castle as a Security Provider
        genesis = new Block("0");
        genesis.addTransaction(noobChain.getGenesisTransaction());
        noobChain.addBlock(genesis);
    }

    @Test
    public void testBalances() throws Exception {
        Wallet walletA = new Wallet();
        Wallet coinbase = new Wallet();
        noobChain.initiateGenesisTransaction(walletA, coinbase,100.00);

        Block block1 = new Block(genesis.getHash());
        Assert.assertTrue(walletA.getBalance() == 100.0);
     }

     @Test
    public void testSending() throws Exception {
         Wallet walletA = new Wallet();
         Wallet walletB = new Wallet();
         Wallet coinbase = new Wallet();
         noobChain.initiateGenesisTransaction(walletA, coinbase,100.00);

         Block block1 = new Block(genesis.getHash());

         block1.addTransaction(
                 walletA.sendFunds(walletB.getPublicKey(), 40.0)
         );
         noobChain.addBlock(block1);

         Assert.assertTrue(walletA.getBalance() == 60.0);
         Assert.assertTrue(walletB.getBalance() == 40.0);
     }


    @Test
    public void testSendingBetweenAccounts() throws Exception {
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        Wallet coinbase = new Wallet();
        noobChain.initiateGenesisTransaction(walletA, coinbase,100.00);

        Block block1 = new Block(genesis.getHash());

        block1.addTransaction(
                walletA.sendFunds(walletB.getPublicKey(), 40.0)
        );


        block1.addTransaction(
                walletB.sendFunds(walletA.getPublicKey(), 20.0)
        );

        noobChain.addBlock(block1);

        Assert.assertTrue(walletA.getBalance() == 80.0);
        Assert.assertTrue(walletB.getBalance() == 20.0);

    }

     @Test
    public void overMaxTest() throws Exception {
         Wallet walletA = new Wallet();
         Wallet walletB = new Wallet();
         Wallet coinbase = new Wallet();
         noobChain.initiateGenesisTransaction(walletA, coinbase,100.00);

         Block block1 = new Block(genesis.getHash());

         //WalletA Attempting to send more funds (1000) than it has...
         block1.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 1000f));
         noobChain.addBlock(block1);

         Assert.assertTrue(walletA.getBalance() == 100.0);
         Assert.assertTrue(walletB.getBalance() == 0.0);
     }

     @Test
    public void verifyChain() throws Exception {
         Wallet walletA = new Wallet();
         Wallet walletB = new Wallet();
         Wallet coinbase = new Wallet();
         noobChain.initiateGenesisTransaction(walletA, coinbase,100.00);

         Block block1 = new Block(genesis.getHash());

         block1.addTransaction(
                 walletA.sendFunds(walletB.getPublicKey(), 40.0*Math.random())
         );


         block1.addTransaction(
                 walletB.sendFunds(walletA.getPublicKey(), 20.0*Math.random())
         );

         noobChain.addBlock(block1);
         Verify v = new Verify();
         boolean isValid =  v.isChainValid(noobChain.getDifficulty(),noobChain.getGenesisTransaction(),noobChain.getBlockchain());
         Assert.assertTrue(isValid);
     }


}

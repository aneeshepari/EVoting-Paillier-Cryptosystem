package jpaillierTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import jpaillier.KeyPair;
import jpaillier.KeyPairBuilder;
import jpaillier.PublicKey;

public class JPaillierTest {

    private KeyPair keyPair;
    PublicKey publicKey;

    @Before
    public void init() {
        KeyPairBuilder keygen = new KeyPairBuilder();
        keyPair = keygen.generateKeyPair();
        publicKey = keyPair.getPublicKey();
    }

    @Test
    public void testEncryption() {
        BigInteger plainData = BigInteger.valueOf(10);

        BigInteger encryptedData = publicKey.encrypt(plainData);

        assertNotEquals(plainData, encryptedData);
    }

    @Test
    public void testDecyption() {
        BigInteger plainData = BigInteger.valueOf(10);

        BigInteger encryptedData = publicKey.encrypt(plainData);
        BigInteger decryptedData = keyPair.decrypt(encryptedData);

        assertEquals(plainData, decryptedData);
    }
}

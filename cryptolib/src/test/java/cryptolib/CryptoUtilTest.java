package cryptolib;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoUtilTest {

    private final String MESSAGE = "MESSAGE TO ENCRYPT";

    @BeforeAll
    static void beforeAll() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    void test() throws Exception {

        SecretKey secretKey = CryptoUtil.generateSecretKey(16);

        assertEquals(16, secretKey.getEncoded().length);

        byte[] iv = CryptoUtil.generateInitializationVector(12);

        assertEquals(12, iv.length);

        byte[] encoded = CryptoUtil.encryptAesGcm(MESSAGE.getBytes(StandardCharsets.UTF_8), secretKey, iv);

        byte[] decoded = CryptoUtil.decryptAesGcm(encoded, secretKey);

        assertEquals(MESSAGE, new String(decoded, StandardCharsets.UTF_8));

    }

}
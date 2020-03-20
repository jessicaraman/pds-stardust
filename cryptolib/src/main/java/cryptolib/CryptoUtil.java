package cryptolib;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class CryptoUtil {

    private static SecureRandom secureRandom;
    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String AES_ALGORITHM = "AES";

    private static final String AES_CBC_PKCS7_PADDING = "AES/CBC/PKCS7Padding";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";

    private static final int AES_GCM_AUTH_TAG_LENGTH = 128;
    private static final int AES_GCM_MIN_IV_LENGTH = 12;
    private static final int AES_GCM_MAX_IV_LENGTH = 16;
    private static final int AES_CBC_BLOCK_SIZE = 16;


    private static final String INVALID_IV_LENGTH_EXCEPTION = "Invalid IV length";

    static {
        secureRandom = new SecureRandom();
    }

    public static byte[] encryptAesGcm(byte[] message, SecretKey secretKey, byte[] iv) throws Exception {

        final Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, BOUNCY_CASTLE_PROVIDER);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(AES_GCM_AUTH_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = cipher.doFinal(message);

        return buildCipherText(iv, cipherText);
    }

    public static byte[] decryptAesGcm(byte[] cipherMessage, SecretKey key) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
        int ivLength = byteBuffer.getInt();

        if (ivLength < AES_GCM_MIN_IV_LENGTH || ivLength >= AES_GCM_MAX_IV_LENGTH) {
            throw new IllegalArgumentException(INVALID_IV_LENGTH_EXCEPTION);
        }

        byte[] iv = new byte[ivLength];
        byteBuffer.get(iv);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        final Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, BOUNCY_CASTLE_PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), AES_ALGORITHM), new GCMParameterSpec(AES_GCM_AUTH_TAG_LENGTH, iv));

        return cipher.doFinal(cipherText);
    }

    public static byte[] encryptAesCbc(byte[] message, SecretKey secretKey, byte[] iv) throws Exception {

        final Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7_PADDING, BOUNCY_CASTLE_PROVIDER);

        IvParameterSpec parameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = cipher.doFinal(message);

        return buildCipherText(iv, cipherText);
    }

    public static byte[] decryptAesCbc(byte[] cipherMessage, SecretKey key) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
        int ivLength = byteBuffer.getInt();

        if (ivLength != AES_CBC_BLOCK_SIZE) {
            throw new IllegalArgumentException(INVALID_IV_LENGTH_EXCEPTION);
        }

        byte[] iv = new byte[ivLength];
        byteBuffer.get(iv);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        final Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7_PADDING, BOUNCY_CASTLE_PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), AES_ALGORITHM), new IvParameterSpec(iv));

        return cipher.doFinal(cipherText);
    }

    private static byte[] buildCipherText(byte[] iv, byte[] cipherText) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);

        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);

        return byteBuffer.array();
    }

    public static byte[] generateInitializationVector(int size) {
        return getRandomBytesPrimitive(size);
    }

    public static SecretKey generateSecretKey(int size) {
        byte[] key = getRandomBytesPrimitive(size);
        //Arrays.fill(key,(byte) 0);
        return new SecretKeySpec(key, AES_ALGORITHM);
    }

    private static byte[] getRandomBytesPrimitive(int size) {
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

}

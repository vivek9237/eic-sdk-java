package io.github.vivek9237.eic.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EicEncryptionUtils {
    public static String encrypt(String plaintext, String secretKeyString) throws Exception {
        // Create a Cipher instance
        Cipher cipher = Cipher.getInstance("AES");
        // Initialize the Cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, EicEncryptionUtils.decodeKeyFromString(secretKeyString));
        // Perform the encryption
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        // Encode the encrypted bytes to Base64 for easy display
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, String secretKeyString) throws Exception {
        // Create a Cipher instance
        Cipher cipher = Cipher.getInstance("AES");
        // Initialize the Cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, EicEncryptionUtils.decodeKeyFromString(secretKeyString));
        // Decode the Base64 encoded string to bytes
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        // Perform the decryption
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        // Convert the decrypted bytes to plaintext
        return new String(decryptedBytes);
    }

    public static SecretKey generateSecretKey() throws Exception {
        return generateSecretKey(256);
    }

    public static SecretKey generateSecretKey(int keyLength) throws Exception {
        // Generate a symmetric key using AES algorithm
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keyLength); // key size
        return keyGenerator.generateKey();
    }

    public static String encodeKeyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey decodeKeyFromString(String secretKeyString) {
        // Decode the Base64 encoded string to get the key bytes
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
        // Create a SecretKeySpec from the key bytes
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        return secretKey;
    }
}

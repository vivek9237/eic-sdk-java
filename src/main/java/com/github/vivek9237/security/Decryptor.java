package com.github.vivek9237.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

public class Decryptor {
    public static String decryptString(String encryptedString) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Read the key file
        byte[] keyBytes = Files.readAllBytes(Paths.get("keyfile.key"));
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        // Decode the base64 encoded string
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
        // Initialize the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        // Decrypt the string
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        // Convert decrypted bytes to string
        String decryptedString = new String(decryptedBytes);
        return decryptedString;
    }
}

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

public class Encryptor {
    public static String encryptString(String plainString,String filePath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        // Read the key file
        byte[] keyBytes = Files.readAllBytes(Paths.get("keyfile.key"));
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        // Initialize the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Encrypt the string
        byte[] encryptedBytes = cipher.doFinal(plainString.getBytes());
        // Encode the encrypted bytes to base64
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedString;
    }
}
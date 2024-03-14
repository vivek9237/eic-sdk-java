package com.github.vivek9237.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

public class EicEncryption {
    public static String decrypt(String encryptedBase64String, String absolutePemFileName) throws Exception{
        PrivateKey privateKey = EicEncryptionUtils.loadPrivateKeyFromFile(absolutePemFileName);
        byte[] encryptedBytes1 = Base64.getDecoder().decode(encryptedBase64String);
        String decryptedString = EicEncryptionUtils.decryptString(encryptedBytes1, privateKey);
        return decryptedString;
    }
    public static String encrypt(String plainText, String absolutePemFileName) throws Exception{
        KeyPair keyPair = EicEncryptionUtils.generateKeyPair();
        byte[] encryptedBytes = EicEncryptionUtils.encryptString(plainText, keyPair.getPublic());
        EicEncryptionUtils.savePrivateKeyToFile(keyPair.getPrivate(), absolutePemFileName);
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedBase64;
    }
}

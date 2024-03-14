package com.github.vivek9237;

import com.github.vivek9237.security.EicEncryption;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java Main [function]");
            System.out.println("Available functions:");
            System.out.println("1. encrypt - Encrypt a string");
            System.out.println("2. decrypt - Decrypt an encrypted string");
            return;
        }
        String function = args[0].toLowerCase();
        switch (function) {
            case "encrypt":
                if (args.length < 2) {
                    System.out.println("Usage: java Main encrypt [inputString]");
                    return;
                }
                String inputString = args[1];
                String generatedEncryptedString = EicEncryption.encrypt(inputString, "eic.pem");
                System.out.println("Encrypted String: " + generatedEncryptedString);
                break;
            case "decrypt":
                if (args.length < 2) {
                    System.out.println("Usage: java Main decrypt [encryptedString]");
                    return;
                }
                String encryptedString = args[1];
                String decryptedString = EicEncryption.decrypt(encryptedString, "eic.pem");
                System.out.println("Decrypted: " + decryptedString);
                break;
            default:
                System.out.println("Invalid function: " + function);
                System.out.println("Available functions:");
                System.out.println("1. encrypt - Encrypt a string");
                System.out.println("2. decrypt - Decrypt an encrypted string");
        }
    }
}

package com.github.vivek9237;

import com.github.vivek9237.security.EicEncryptionUtils;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: `java jar eic-sdk-java.jar [function]`");
            System.out.println("Available functions:");
            System.out.println("1. `generatekey` - Generates a secret key");
            System.out.println("2. `encrypt` - Encrypt a string");
            System.out.println("3. `decrypt` - Decrypt an encrypted string");
            return;
        }
        String function = args[0].toLowerCase();
        switch (function) {
            case "generatekey":
                if (args.length == 1) {
                    try {
                        String secretKey = EicEncryptionUtils.encodeKeyToString(EicEncryptionUtils.generateSecretKey());
                        System.out.println(secretKey);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else if (args.length == 2) {
                    try {
                        int keyLength = Integer.parseInt(args[1]);
                        String secretKey = EicEncryptionUtils
                                .encodeKeyToString(EicEncryptionUtils.generateSecretKey(keyLength));
                        System.out.println(secretKey);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                break;
            case "encrypt":
                if (args.length < 3) {
                    System.out.println("Usage: `java jar eic-sdk-java.jar encrypt [inputString] [secretKey]`");
                    return;
                }
                try {
                    String plainTextString = args[1];
                    String secretKey = args[2];
                    String generatedEncryptedString = EicEncryptionUtils.encrypt(plainTextString, secretKey);
                    System.out.println(generatedEncryptedString);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
            case "decrypt":
                if (args.length < 3) {
                    System.out.println("Usage: `java jar eic-sdk-java.jar decrypt [encryptedString] [secretKey]`");
                    return;
                }
                try {
                    String encryptedString = args[1];
                    String secretKey = args[2];
                    String decryptedString = EicEncryptionUtils.decrypt(encryptedString, secretKey);
                    System.out.println(decryptedString);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid function: " + function);
                System.out.println("Available functions:");
                System.out.println("1. `generatekey` - Generates a secret key");
                System.out.println("2. `encrypt` - Encrypt a string");
                System.out.println("3. `decrypt` - Decrypt an encrypted string");
        }
    }
}

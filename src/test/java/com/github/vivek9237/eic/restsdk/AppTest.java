package com.github.vivek9237.eic.restsdk;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.github.vivek9237.security.EicEncryptionUtils;

/**
 * Unit test for simple App.
 */
public class AppTest {
    Map<String, String> envVariables = System.getenv();

    /**
     * Rigorous Test :-)
     * 
     * @throws Exception
     */
    @Test
    public void testGetAccessToken() throws Exception {
        EicClient eicClient = new EicClient(envVariables.get("EIC_TENANT"), envVariables.get("EIC_USERNAME"),
                envVariables.get("EIC_PASSWORD"));
        assertTrue(eicClient.getAccessToken().length() > 0);
    }
    @Test
    public void testGetAccessToken1() throws Exception {
        EicClient eicClient = new EicClient();
        assertTrue(eicClient.getAccessToken().length() > 0);
    }
    @Test
    public void testGetUserByUsername() throws Exception {
        EicClient eicClient = new EicClient(envVariables.get("EIC_TENANT"), envVariables.get("EIC_USERNAME"),
                envVariables.get("EIC_PASSWORD"));
        assertTrue(eicClient.getUserByUsername("admin").get("username").equals("admin"));
    }

    @Test
    public void testGetDatasetValues() throws Exception {
        EicClient eicClient = new EicClient(envVariables.get("EIC_TENANT"), envVariables.get("EIC_USERNAME"),
                envVariables.get("EIC_PASSWORD"));
        assertTrue(eicClient.getDatasetValues("REPLACE_MAPPING").size() > 0);
    }
    @Test
    public void testEncryptionAndDecryption() throws Exception{
        String secret = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String generatedSecret = EicEncryptionUtils.encodeKeyToString(EicEncryptionUtils.generateSecretKey());
        String encryptedString = EicEncryptionUtils.encrypt(secret, generatedSecret);
        String decryptedString = EicEncryptionUtils.decrypt(encryptedString, generatedSecret);
        assertTrue(secret.equals(decryptedString));
    }
}

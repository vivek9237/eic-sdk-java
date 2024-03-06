package com.github.vivek9237.eic.restsdk;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.github.vivek9237.eic.restsdk.utils.EicClientUtils;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws Exception 
     */
    @Test
    public void fetchingAccessToken() throws Exception
    {
        Map<String,Object> creds  = EicClientUtils.parseYamlFile("/credentials.yml");
            EicClient eicClient = new EicClient(creds.get("environment").toString(),creds.get("username").toString(),creds.get("password").toString());
        assertTrue( eicClient.getAccessToken().length()>0 );
    }
    @Test
    public void fetchingUser() throws Exception
    {
        Map<String,Object> creds  = EicClientUtils.parseYamlFile("/credentials.yml");
            EicClient eicClient = new EicClient(creds.get("environment").toString(),creds.get("username").toString(),creds.get("password").toString());
        assertTrue( eicClient.getUserByUsername("admin").get("username").equals("admin") );
    }
}

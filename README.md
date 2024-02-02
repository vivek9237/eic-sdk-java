# eic-rest-sdk

## About
This Java-based DSK easily connects with Saviynt's EIC, using EIC v5 APIs for smooth integration. It's user-friendly, with simple steps for connectivity, clear instructions, and a focus on security. Developers can make efficient and powerful solutions by directly using EIC v5 API features.
## Sample SDK Usage
Sample java program -

App.java
```Java
import java.io.IOException;
import javax.naming.AuthenticationException;

import com.vivek9237.eic.restsdk.EicClient;

public class App {
    public static void main( String[] args ) throws AuthenticationException, Exception
    {
        EicClient eicClient = new EicClient("company-tenant","admin","strongestPassword");
        try {
            System.out.println(eicClient.getUserByUsername("admin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

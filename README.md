# eic-rest-sdk

## About
This Java-based SDK easily connects with Saviynt's EIC, using EIC v5 APIs for smooth integration. It's user-friendly, with simple steps for connectivity, clear instructions, and a focus on security. Developers can make efficient and powerful solutions by directly using EIC v5 API features.

## Javadoc
https://vivek9237.github.io/eic-rest-sdk/javadoc/

## Environment Variables
To run this project, you will need to add the following environment variables to your .env file

`EIC_TENANT` EIC tenant name

`EIC_USERNAME` EIC username with appropriate permissions

`EIC_PASSWORD` EIC user's password


## Sample SDK Usage
### Sample Java program

App.java
```Java
import com.vivek9237.eic.restsdk.EicClient;

public class App {
    public static void main( String[] args )
    {
        try {
            // Creating an instance of the EicClient class with tenant, username, and password parameters
            EicClient eicClient = new EicClient("company-tenant","admin","strongestPassword");
            // Attempting to retrieve user information for the username "admin"
            System.out.println(eicClient.getUserByUsername("admin"));
        } catch (Exception e) {
            // Handling Exception by printing the stack trace
            e.printStackTrace();
        }
    }
}
```

## Feedback

If you have any feedback, please reach out to us at vivek.ku.mohanty@gmail.com
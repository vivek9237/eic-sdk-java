# EIC REST SDK

## About
This Java-based SDK easily connects with Saviynt's EIC, using EIC v5 APIs for smooth integration. It's user-friendly, with simple steps for connectivity, clear instructions, and a focus on security. Developers can make efficient and powerful solutions by using EIC v5 API features in this SDK.

## Documentation
- [Javadoc](https://vivek9237.github.io/eic-rest-sdk/javadoc/)

## Environment Variables
To run this project, you will need to add the following environment variables to your .env file

`EIC_TENANT` EIC tenant name

`EIC_USERNAME` EIC username with appropriate permissions

`EIC_PASSWORD` EIC password

## Features

- Fetching Users
- Fetching Dataset_values
- Fetching Accounts

## Sample SDK Usage
### Sample Java program

This sample code demonstrates the usage of the EIC REST SDK.
It initializes EicClient with tenant, username, and password, attempts to retrieve user information for a specified username.

#### App.java
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
## Roadmap

- Add more API integration

- Add ways to securely pass credentials


## Authors

- [@vivek9237](https://www.github.com/vivek9237)

## Feedback

If you have any feedback, please reach out to us at vivek.ku.mohanty@gmail.com
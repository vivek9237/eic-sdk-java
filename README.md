# EIC REST SDK

[![Project Status](http://opensource.box.com/badges/active.svg)](http://opensource.box.com/badges)
![Platform](https://img.shields.io/badge/java-%3E%3D8-blue)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://raw.githubusercontent.com/vivek9237/eic-sdk-java/main/LICENSE)
[![Build main](https://github.com/vivek9237/eic-sdk-java/actions/workflows/release.yml/badge.svg)](https://github.com/vivek9237/eic-sdk-java/actions/workflows/release.yml)


## About
This Java-based SDK easily connects with Saviynt's EIC, using EIC v5 APIs for smooth integration. It's user-friendly, with simple steps for connectivity, clear instructions, and a focus on security. Developers can make efficient and powerful solutions by using EIC v5 API features in this SDK.

## Documentation
- [Javadoc](https://vivek9237.github.io/eic-sdk-java/javadoc/)

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
import io.github.vivek9237.eic.restsdk.EicClient;

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

## Contributing

Please read [CONTRIBUTING.md](https://github.com/vivek9237/eic-sdk-java/blob/main/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

- [@vivek9237](https://www.github.com/vivek9237)

See also the list of [contributors](https://github.com/vivek9237/eic-sdk-java/graphs/contributors) who have participated in this project.

## Feedback
We value your input! If you have any feedback, suggestions, or comments, please don't hesitate to share them with us. You can reach out to us at vivek.ku.mohanty@gmail.com.

If you encounter any bugs, please report them by creating an issue here - [Report Bugs](https://github.com/vivek9237/eic-sdk-java/issues/new?assignees=&labels=&projects=&template=bug_report.md&title=)

If you have a feature request, feel free to submit it here - [Request New Features](https://github.com/vivek9237/eic-sdk-java/issues/new?assignees=&labels=&projects=&template=feature_request.md&title=)

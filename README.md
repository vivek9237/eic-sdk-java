

<br />
<div align="center">
    
[![Project Status](http://opensource.box.com/badges/active.svg)](http://opensource.box.com/badges)
![Platform](https://img.shields.io/badge/java-1.8-blue)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://raw.githubusercontent.com/vivek9237/eic-sdk-java/main/LICENSE)
[![Build main](https://github.com/vivek9237/eic-sdk-java/actions/workflows/release.yml/badge.svg)](https://github.com/vivek9237/eic-sdk-java/actions/workflows/release.yml)
[![Javadoc](https://img.shields.io/badge/javadoc-blue)](https://vivek9237.github.io/eic-sdk-java/javadoc/)


  <h1 align="center">EIC REST SDK</h1>

  <p align="center">
    This Java-based SDK easily connects with Saviynt's EIC, using EIC v5 APIs for smooth integration. It's user-friendly, with simple steps for connectivity, clear instructions, and a focus on security. Developers can make efficient and powerful solutions by using EIC v5 API features in this SDK.
    <br />
    <a href="https://vivek9237.github.io/eic-sdk-java/javadoc/"><strong>Explore SDK Javadoc »</strong></a>
    <br />
    <br />
  </p>
</div>

## Features

- Fetching Users
- Fetching Dataset_values
- Fetching Accounts

## Getting Started
To get started with the EIC REST SDK, follow these steps:

### Prerequisites
- Java Development Kit (JDK) version 8
- Apache Maven
- Update your Maven settings.xml file to include credentials for GitHub Packages, if you haven't already done so. You can follow the [GitHub documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry) for instructions on how to set up authentication for GitHub Packages in Maven.

### Installation
You can include the EIC REST SDK in your Maven project by adding the following dependency to your `pom.xml` file:

```xml
<dependency>
  <groupId>io.github.vivek9237.eic.restsdk</groupId>
  <artifactId>eic-sdk-java</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
> **_NOTE:_**   For more information, see [EIC=SDK-JAVA Package](https://github.com/vivek9237/eic-sdk-java/packages/2144093)

### Environment Variables
To run this project, you will need to add the following environment variables to your .env file

- `EIC_TENANT` EIC tenant name

- `EIC_REFRESH_TOKEN` EIC API Refresh Token


## Sample SDK Usage
### Sample Java program

This sample code demonstrates the usage of the EIC REST SDK.
It initializes EicClient with tenant, username, and password, attempts to retrieve user information for a specified username.

#### App.java
```Java
import io.github.vivek9237.eic.restsdk.EicClient;
import java.util.logging.Logger;
import java.util.Map;

public class App {
    Logger log = Logger.getLogger(SampleUsage.class.getName());
    public static void main( String[] args )
    {
        try {
            //Fetch all the environment variables
            Map<String, String> envVariables = System.getenv();
            // Creating an instance of the EicClient class with tenant, and refreshToken parameters
            EicClient eicClient = new EicClient(envVariables.get("EIC_TENANT"), envVariables.get("EIC_REFRESH_TOKEN"));
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

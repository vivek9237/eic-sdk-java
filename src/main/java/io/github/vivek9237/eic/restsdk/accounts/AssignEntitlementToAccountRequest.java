package io.github.vivek9237.eic.restsdk.accounts;

public class AssignEntitlementToAccountRequest {
    private String securitysystem;
    private String endpoint;
    private String accountname;
    private String username;

    public String getSecuritysystem() {
        return securitysystem;
    }

    public void setSecuritysystem(String securitysystem) {
        this.securitysystem = securitysystem;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

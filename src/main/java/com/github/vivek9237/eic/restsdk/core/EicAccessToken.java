package com.github.vivek9237.eic.restsdk.core;

import java.util.Date;

import com.github.vivek9237.eic.restsdk.utils.EicClientUtils;

/**
 * Represents an access token used for authentication.
 */
public class EicAccessToken {
    private String token;
    private Date expiryDate;

    /**
     * Constructs an AccessToken object with the provided token and expiry date.
     * 
     * @param token      The access token.
     * @param expiryDate The expiry date of the token.
     */
    public EicAccessToken(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    /**
     * Checks if the access token is valid.
     * 
     * @return true if the token is valid; otherwise false.
     */
    public Boolean isValid() {
        return EicClientUtils.hasDatePassed(expiryDate, 5);
    }

    /**
     * Gets the access token.
     * 
     * @return The access token.
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Gets the expiry date of the access token.
     * 
     * @return The expiry date of the access token.
     */
    public Date getExpiryDate() {
        return this.expiryDate;
    }
}

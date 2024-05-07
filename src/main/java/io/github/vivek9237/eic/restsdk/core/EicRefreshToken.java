package io.github.vivek9237.eic.restsdk.core;

import java.util.Date;
import io.github.vivek9237.eic.restsdk.utils.EicClientUtils;

/**
 * Represents a refresh token used for authentication.
 */
public class EicRefreshToken {
    private String token;
    private Date expiryDate;

    /**
     * Constructs a EicRefreshToken object with the provided token and sets the
     * expiry
     * date to 300 days from the current date.
     * 
     * @param token The refresh token.
     */
    public EicRefreshToken(String token) {
        this.token = token;
        this.expiryDate = EicClientUtils.addDaysToDate(new Date(), 300);
    }

    /**
     * Constructs a EicRefreshToken object with the provided token and expiry date.
     * 
     * @param token      The refresh token.
     * @param expiryDate The expiry date of the token.
     */
    public EicRefreshToken(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    /**
     * Checks if the refresh token is valid.
     * 
     * @return Always returns false as the validity check is not implemented.
     */
    public Boolean isValid() {
        return false;
    }

    /**
     * Gets the refresh token.
     * 
     * @return The refresh token.
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Gets the expiry date of the refresh token.
     * 
     * @return The expiry date of the refresh token.
     */
    public Date getExpiryDate() {
        return this.expiryDate;
    }
}

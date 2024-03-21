package com.github.vivek9237.eic.restsdk.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class representing a response for updating an account.
 */
public class UpdateAccountResponse {

    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("message")
    @Expose
    private String message;

    public String getErrorCode() {
        return errorCode;
    }
    
    public String getMessage() {
        return message;
    }
}
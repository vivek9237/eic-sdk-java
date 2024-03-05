package com.vivek9237.eic.restsdk;

import java.util.Date;

public class AccessToken {
    private String token;
    private Date exipryDate;
    AccessToken(String token,Date exipryDate) {
        this.token = token;
        this.exipryDate = exipryDate;
    }
    public Boolean isValid(){
        return EicClientUtils.hasDatePassed(exipryDate,5);
    }
    public String getToken(){
        return this.token;
    }
    public Date getExpiryDate(){
        return this.exipryDate;
    }
}

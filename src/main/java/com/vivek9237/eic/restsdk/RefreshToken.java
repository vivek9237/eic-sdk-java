package com.vivek9237.eic.restsdk;

import java.util.Date;

public class RefreshToken {
    private String token;
    private Date exipryDate;
    RefreshToken(String token) {
        this.token = token;
        this.exipryDate = EicClientUtils.addDaysToDate(new Date(), 300);
    }
    RefreshToken(String token,Date exipryDate) {
        this.token = token;
        this.exipryDate = exipryDate;
    }
    public Boolean isValid(){
        return false;
    }
    public String getToken(){
        return this.token; 
    }
    public Date getExpiryDate(){
        return this.exipryDate;
    }
}

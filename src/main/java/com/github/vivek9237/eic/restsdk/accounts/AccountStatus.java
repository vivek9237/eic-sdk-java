package com.github.vivek9237.eic.restsdk.accounts;

public enum AccountStatus {
    ACTIVESTATUS("1"),
    INACTIVESTATUS("2"),
    DECOMMISSION_ACTIVE("3"),
    DECOMMISSION_INACTIVE("4"),
    SUSSPENDEDSTATUS("Manually Suspended"),
    PROVISIONSTATUS("Manually Provisioned"),
    SUSPENDEDFROMIMPORTSERVICESTATUS("SUSPENDED FROM IMPORT SERVICE");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

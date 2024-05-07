package io.github.vivek9237.eic.restsdk.accounts;

/**
 * Enum representing various status types of an account.
 */
public enum AccountStatus {
    ACTIVESTATUS("1"),
    INACTIVESTATUS("2"),
    DECOMMISSION_ACTIVE("3"),
    DECOMMISSION_INACTIVE("4"),
    SUSSPENDEDSTATUS("Manually Suspended"),
    PROVISIONSTATUS("Manually Provisioned"),
    SUSPENDEDFROMIMPORTSERVICESTATUS("SUSPENDED FROM IMPORT SERVICE");

    private final String value;

    /**
     * Constructs an AccountStatus enum with the specified value.
     * 
     * @param value the value associated with the status
     */
    AccountStatus(String value) {
        this.value = value;
    }

    /**
     * Returns the value associated with the status.
     * 
     * @return the value associated with the status
     */
    public String getValue() {
        return value;
    }
}

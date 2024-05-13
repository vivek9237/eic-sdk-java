package io.github.vivek9237.eic.restsdk.users;

/**
 * Enum representing various status types of a user.
 */
public enum UserStatus {
    ACTIVE("1"),
    INACTIVE("2");

    private final String value;

    /**
     * Constructs an UserStatus enum with the specified value.
     * 
     * @param value the value associated with the status
     */
    UserStatus(String value) {
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

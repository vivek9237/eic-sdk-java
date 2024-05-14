package io.github.vivek9237.eic.utils;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EicCommonUtils {
    private static final long LDAP_EPOCH_DIFF = 11644473600000L;

    /**
     * Time unit constants for use in dynamic date subtraction.
     */
    public enum TimeUnit {
        MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS
    }

    /**
     * Reads a properties file from the specified path and returns a
     * {@link Properties} object.
     *
     * @param path the file path of the properties file to read
     * @return a {@link Properties} object containing the key-value pairs from the
     *         properties file
     * @throws IOException if an I/O error occurs while reading the properties file
     */
    public static Properties readPropertyFile(String path) throws IOException {
        Properties props = new Properties();
        FileReader reader = new FileReader(path);
        props.load(reader);
        return props;
    }

    /**
     * Converts a string to a date using the specified date format.
     *
     * @param dateString the string representation of the date
     * @param dateFormat the date format of the input dateString
     * @return the Date object
     * @throws ParseException if the date string cannot be parsed
     */
    public static Date stringToDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(dateString);
    }

    /**
     * Converts a date to a string using the specified date format.
     *
     * @param date the Date object
     * @param dateFormat the date format of the output dateString
     * @return the string representation of the date
     */
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * Adds a specified amount of time to a date based on the given time unit.
     *
     * @param date     the original date
     * @param amount   the amount of time to add
     * @param timeUnit the calendar field (e.g., Calendar.MILLISECOND,
     *                 Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR,
     *                 Calendar.DAY_OF_YEAR, Calendar.MONTH, Calendar.YEAR)
     * @return the new Date object with the added time
     */
    public static Date addDate(Date date, int amount, int timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(timeUnit, amount);
        return calendar.getTime();
    }

    /**
     * Converts a Date to Unix epoch time.
     *
     * @param date the Date object
     * @return the Unix epoch time in milliseconds
     */
    public static long dateToEpoch(Date date) {
        return date.getTime();
    }

    /**
     * Converts a Unix epoch time to a Date.
     *
     * @param epochTime the Unix epoch time in milliseconds
     * @return the Date object
     */
    public static Date epochToDate(long epochTime) {
        return new Date(epochTime);
    }

    /**
     * Converts an LDAP epoch time to a Date.
     *
     * @param ldapEpochTime the LDAP epoch time in 100-nanosecond intervals
     * @return the Date object
     */
    public static Date ldapEpochToDate(long ldapEpochTime) {
        long milliseconds = (ldapEpochTime / 10000) - LDAP_EPOCH_DIFF;
        return new Date(milliseconds);
    }

    /**
     * Converts a Date to LDAP epoch time.
     *
     * @param date the Date object
     * @return the LDAP epoch time in 100-nanosecond intervals
     */
    public static long dateToLdapEpoch(Date date) {
        long milliseconds = date.getTime() + LDAP_EPOCH_DIFF;
        return milliseconds * 10000;
    }

}

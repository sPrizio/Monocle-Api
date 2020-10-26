package com.priago.monocleapi.core.services.nonentities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Service-layer to handle date & time throughout Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface DateTimeService {

    /**
     * Returns the server's {@link ZoneId}, commonly used for time zone calculations
     */
    ZoneId obtainServerZoneId();

    /**
     * Retrieves the current time based on a pre-configured time-zone in the properties file, else it will default to the
     * system's timezone
     */
    LocalDateTime now();

    /**
     * Retrieves the current time based for the timezone, else it will default to the system's timezone
     */
    LocalDateTime now(String timezone);

    /**
     * Attempts to parse the given string and format into a {@link LocalDateTime} object
     */
    LocalDateTime parseDateTimeStringForFormat(String dateTimeString, DateTimeFormatter format);

    /**
     * Attempts to parse the given string and format into a {@link LocalDate} object
     */
    LocalDate parseDateStringForFormat(String dateString, DateTimeFormatter format);

    /**
     * Obtain today's date
     */
    LocalDate today();
}

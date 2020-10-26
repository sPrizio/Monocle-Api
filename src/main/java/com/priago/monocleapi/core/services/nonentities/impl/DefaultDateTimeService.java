package com.priago.monocleapi.core.services.nonentities.impl;

import com.priago.monocleapi.core.services.nonentities.DateTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Core.DEFAULT_ZONE_ID;
import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Core.TIME_ZONE;

/**
 * Default implementation of {@link DateTimeService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("dateTimeService")
public class DefaultDateTimeService implements DateTimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDateTimeService.class);


    //  METHODS

    @Override
    public ZoneId obtainServerZoneId() {
        return ZoneId.of(TIME_ZONE);
    }

    @Override
    public LocalDateTime now() {
        try {
            return LocalDateTime.now(obtainServerZoneId());
        } catch (Exception e) {
            LOGGER.error("Date & time could not be created for time zone {}, returning today's date as default", TIME_ZONE);
            return LocalDateTime.now(DEFAULT_ZONE_ID);
        }
    }

    @Override
    public LocalDateTime now(String timezone) {
        try {
            return LocalDateTime.now(ZoneId.of(timezone));
        } catch (Exception e) {
            LOGGER.error("Date & time could not be created for time zone {}, returning today's date as default", timezone);
            return LocalDateTime.now(DEFAULT_ZONE_ID);
        }
    }

    @Override
    public LocalDateTime parseDateTimeStringForFormat(String dateTimeString, DateTimeFormatter format) {
        try {
            return LocalDateTime.parse(dateTimeString, format);
        } catch (Exception e) {
            LOGGER.error("Could not parse the given dateTimeString {} for the given format {}", dateTimeString, format);
            return null;
        }
    }

    @Override
    public LocalDate parseDateStringForFormat(String dateString, DateTimeFormatter format) {
        try {
            return LocalDate.parse(dateString, format);
        } catch (Exception e) {
            LOGGER.error("Could not parse the given dateString {} for the given format {}", dateString, format);
            return null;
        }
    }

    @Override
    public LocalDate today() {
        return now().toLocalDate();
    }
}

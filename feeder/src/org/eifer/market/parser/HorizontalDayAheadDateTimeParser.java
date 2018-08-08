package org.eifer.market.parser;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HorizontalDayAheadDateTimeParser extends DateTimeParser {

    @Override
    public Instant parseDateTimeToInstant(String date, String hour) {
        if (isDaylightSavingTime(hour)) {
            Instant instant = parseToInstant(date, hour);
            return isTheSecondRepeatedHour(hour) ? instant.plus(1, ChronoUnit.HOURS) : instant;
        }
        return parseToInstant(date, hour);
    }

}

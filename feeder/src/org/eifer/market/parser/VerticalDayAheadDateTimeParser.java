package org.eifer.market.parser;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class VerticalDayAheadDateTimeParser extends DateTimeParser {

    @Override
    public Instant parseDateTimeToInstant(String date, String hour) {
        if (isDaylightSavingTime(hour)) {
            Instant instant = parseToInstant(date, hour);
            return isTheSecondRepeatedHour(hour) ? instant.plus(1, ChronoUnit.HOURS) : instant;
        }
        return parseToInstant(date, hour);
    }

    @Override
    String parseHour(String eexHour) {

        String [] quarterHourMinutes = new String[] { "00", "15", "30", "45" };
        String [] halfHourMinutes = new String[] { "00", "30" };

        if (eexHour.contains("qh")) {
            int quarter = Integer.parseInt(eexHour.substring(eexHour.lastIndexOf('h') + 1)) - 1;
            String hour = eexHour.substring(0, eexHour.indexOf('q'));
            hour = hour.length() == 1 ? "0" + hour : hour;
            return  hour + ":" + quarterHourMinutes[quarter] + ":00";
        } else if (eexHour.contains("hh")) {
            int half = Integer.parseInt(eexHour.substring(eexHour.lastIndexOf('h') + 1)) - 1;
            String hour = eexHour.substring(0, eexHour.indexOf('h'));
            hour = hour.length() == 1 ? "0" + hour : hour;
            return hour + ":" + halfHourMinutes[half] + ":00";
        } else {
            String hour = eexHour;
            hour = hour.length() == 1 ? "0" + hour : hour;
            return  hour + ":00:00";
        }
    }

}

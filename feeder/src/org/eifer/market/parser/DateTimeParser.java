package org.eifer.market.parser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DateTimeParser {

    public abstract Instant parseDateTimeToInstant(String date, String hour);

    boolean isDaylightSavingTime(String hour) {
        return hour.toLowerCase().contains("a") || hour.toLowerCase().contains("b");
    }

    boolean isTheSecondRepeatedHour(String hour) {
        return hour.toLowerCase().contains("b");
    }

    String substractOneHour(String hour) {
        return String.valueOf(Integer.parseInt(hour) - 1);
    }

    public Instant parseToInstant(String date, String hourWithNotation) {
        String unparsedHour = hourWithNotation.toLowerCase().contains("a") ? hourWithNotation.toLowerCase().replace("a", "") :
                hourWithNotation.toLowerCase().replace("b", "");

        String dateReplaced = date.replace("-", "/");
        dateReplaced = dateReplaced.replace(".", "/");
        String [] dateFields = dateReplaced.split("/");
        int day = Integer.parseInt(dateFields[0]);
        int month = Integer.parseInt(dateFields[1]);
        int year = Integer.parseInt(dateFields[2]);

        String [] hourFields = parseHour(unparsedHour).split(":");
        int hour = Integer.parseInt(hourFields[0]);
        int minutes = Integer.parseInt(hourFields[1]);
        int seconds = Integer.parseInt(hourFields[2]);

        ZonedDateTime zonedDateTime = LocalDateTime.of(year, month, day, hour, minutes, seconds).atZone(ZoneId.of("Europe/Berlin"));
        return zonedDateTime.toInstant();
    }

    String parseHour(String eexHour) {

        String [] quarterHourMinutes = new String[] { "00", "15", "30", "45" };
        String [] halfHourMinutes = new String[] { "00", "30" };

        if (eexHour.contains("qh")) {
            int quarter = Integer.parseInt(eexHour.substring(eexHour.lastIndexOf('h') + 1)) - 1;
            String hour = substractOneHour(eexHour.substring(0, eexHour.indexOf('q')));
            hour = hour.length() == 1 ? "0" + hour : hour;
            return  hour + ":" + quarterHourMinutes[quarter] + ":00";
        } else if (eexHour.contains("hh")) {
            int half = Integer.parseInt(eexHour.substring(eexHour.lastIndexOf('h') + 1)) - 1;
            String hour = substractOneHour(eexHour.substring(0, eexHour.indexOf('h')));
            hour = hour.length() == 1 ? "0" + hour : hour;
            return hour + ":" + halfHourMinutes[half] + ":00";
        } else {
            String hour = substractOneHour(eexHour);
            hour = hour.length() == 1 ? "0" + hour : hour;
            return  hour + ":00:00";
        }
    }


}

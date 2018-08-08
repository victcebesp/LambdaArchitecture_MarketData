package org.eifer.market.parser;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;

public class MasterDataDateTimeParser {

    public Instant parseDateTimeToInstant(String timeStamp){

        String dateTime = timeStamp.split("\\+")[0] + 'Z';

        String offset = timeStamp.split("\\+")[1];
        int hourToSubtract = Integer.parseInt(offset.substring(1, offset.indexOf(':')));

        return Instant.parse(dateTime).minus(hourToSubtract, HOURS);

    }

}

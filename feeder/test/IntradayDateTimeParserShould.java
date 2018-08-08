import org.eifer.market.parser.DateTimeParser;
import org.eifer.market.parser.IntradayDateTimeParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntradayDateTimeParserShould {

    DateTimeParser dateTimeParser;

    @Before
    public void before(){
        dateTimeParser = new IntradayDateTimeParser();
    }

    @Test
    public void given_oclock_hour_from_1_until_2_in_daylight_change_date_when_parsed_return_one_hour_less_oclock() {

            assertEquals("2015-10-24T22:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1").toString());
            assertEquals("2015-10-24T23:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2").toString());
    }

    @Test
    public void given_oclock_hour_at_daylight_change_in_daylight_change_date_when_parsed_return_one_hour_less_oclock() {

        assertEquals("2015-10-25T00:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3A").toString());
        assertEquals("2015-10-25T01:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3B").toString());
    }

    @Test
    public void given_oclock_hour_from_4_until_24_in_daylight_change_date_when_parsed_return_one_hour_less_oclock() {

        for (int i = 4; i < 25; i++) {
            if (i < 12)
                assertEquals("2015-10-25T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i)).toString());
            else
                assertEquals("2015-10-25T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i)).toString());
        }

    }

    @Test
    public void given_half_hour_from_1_until_2_in_daylight_change_date_when_parsed_return_one_hour_less_in_half_hour() {

        assertEquals("2015-10-24T22:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1hh1").toString());
        assertEquals("2015-10-24T22:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1hh2").toString());
        assertEquals("2015-10-24T23:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2hh1").toString());
        assertEquals("2015-10-24T23:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2hh2").toString());
    }

    @Test
    public void given_half_hour_at_daylight_change_in_daylight_change_date_when_parsed_return_one_hour_less_in_half_hour() {

        assertEquals("2015-10-25T00:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Ahh1").toString());
        assertEquals("2015-10-25T00:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Ahh2").toString());
        assertEquals("2015-10-25T01:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bhh1").toString());
        assertEquals("2015-10-25T01:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bhh2").toString());
    }

    @Test
    public void given_half_hour_from_4_until_24_in_daylight_change_date_when_parsed_return_one_hour_less_in_half_hour(){

        for (int i = 4; i < 25; i++) {
            if (i < 12){
                assertEquals("2015-10-25T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "hh1").toString());
                assertEquals("2015-10-25T0" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "hh2").toString());
            }
            else{
                assertEquals("2015-10-25T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "hh1").toString());
                assertEquals("2015-10-25T" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "hh2").toString());
            }
        }

    }

    @Test
    public void given_quarter_hour_from_1_until_2_in_daylight_change_date_when_parsed_return_one_hour_less_in_quarter_hour() {

        assertEquals("2015-10-24T22:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1qh1").toString());
        assertEquals("2015-10-24T22:15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1qh2").toString());
        assertEquals("2015-10-24T22:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1qh3").toString());
        assertEquals("2015-10-24T22:45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "1qh4").toString());
        assertEquals("2015-10-24T23:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2qh1").toString());
        assertEquals("2015-10-24T23:15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2qh2").toString());
        assertEquals("2015-10-24T23:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2qh3").toString());
        assertEquals("2015-10-24T23:45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "2qh4").toString());
    }

    @Test
    public void given_quarter_hour_at_daylight_change_in_daylight_change_date_when_parsed_return_one_hour_less_in_quarter_hour() {

        assertEquals("2015-10-25T00:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Aqh1").toString());
        assertEquals("2015-10-25T00:15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Aqh2").toString());
        assertEquals("2015-10-25T00:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Aqh3").toString());
        assertEquals("2015-10-25T00:45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Aqh4").toString());
        assertEquals("2015-10-25T01:00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bqh1").toString());
        assertEquals("2015-10-25T01:15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bqh2").toString());
        assertEquals("2015-10-25T01:30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bqh3").toString());
        assertEquals("2015-10-25T01:45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", "3Bqh4").toString());
    }

    @Test
    public void given_quarter_hour_from_4_until_24_in_daylight_change_date_when_parsed_return_one_hour_less_in_quarter_hour(){

        for (int i = 4; i < 25; i++) {
            if (i < 12){
                assertEquals("2015-10-25T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh1").toString());
                assertEquals("2015-10-25T0" + (i - 2) + ":15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh2").toString());
                assertEquals("2015-10-25T0" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh3").toString());
                assertEquals("2015-10-25T0" + (i - 2) + ":45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh4").toString());
            }
            else{
                assertEquals("2015-10-25T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh1").toString());
                assertEquals("2015-10-25T" + (i - 2) + ":15:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh2").toString());
                assertEquals("2015-10-25T" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh3").toString());
                assertEquals("2015-10-25T" + (i - 2) + ":45:00Z", dateTimeParser.parseDateTimeToInstant("25/10/2015", String.valueOf(i) + "qh4").toString());
            }
        }

    }

    @Test
    public void given_oclock_hour_from_1_until_24_when_parsed_return_one_hour_less_in_hour(){
        assertEquals("2015-10-25T23:00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1").toString());
        for (int i = 2; i < 25; i++) {
            if (i < 12)
                assertEquals("2015-10-26T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i)).toString());
            else
                assertEquals("2015-10-26T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i)).toString());
        }
    }

    @Test
    public void given_half_hor_from_1_until_24_when_parsed_return_one_hour_less_in_half_hour(){

        assertEquals("2015-10-25T23:00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1hh1").toString());
        assertEquals("2015-10-25T23:30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1hh2").toString());

        for (int i = 2; i < 25; i++) {
            if (i < 12){
                assertEquals("2015-10-26T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "hh1").toString());
                assertEquals("2015-10-26T0" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "hh2").toString());
            }
            else{
                assertEquals("2015-10-26T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "hh1").toString());
                assertEquals("2015-10-26T" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "hh2").toString());
            }
        }

    }

    @Test
    public void given_quarter_hour_from_1_until_24_when_parsed_return_one_hour_less_in_quarter_hour(){

        assertEquals("2015-10-25T23:00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1qh1").toString());
        assertEquals("2015-10-25T23:15:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1qh2").toString());
        assertEquals("2015-10-25T23:30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1qh3").toString());
        assertEquals("2015-10-25T23:45:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", "1qh4").toString());

        for (int i = 2; i < 25; i++) {
            if (i < 12){
                assertEquals("2015-10-26T0" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh1").toString());
                assertEquals("2015-10-26T0" + (i - 2) + ":15:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh2").toString());
                assertEquals("2015-10-26T0" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh3").toString());
                assertEquals("2015-10-26T0" + (i - 2) + ":45:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh4").toString());
            }
            else{
                assertEquals("2015-10-26T" + (i - 2) + ":00:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh1").toString());
                assertEquals("2015-10-26T" + (i - 2) + ":15:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh2").toString());
                assertEquals("2015-10-26T" + (i - 2) + ":30:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh3").toString());
                assertEquals("2015-10-26T" + (i - 2) + ":45:00Z", dateTimeParser.parseDateTimeToInstant("26/10/2015", String.valueOf(i) + "qh4").toString());
            }
        }

    }

    @Test
    public void foo() {

        assertEquals("2005-03-26T23:00:00Z", dateTimeParser.parseDateTimeToInstant("27/03/2005", "1").toString());
        assertEquals("2005-03-27T00:00:00Z", dateTimeParser.parseDateTimeToInstant("27/03/2005", "2").toString());
        assertEquals("2005-03-27T01:00:00Z", dateTimeParser.parseDateTimeToInstant("27/03/2005", "4").toString());

        assertEquals("2011-01-02T23:00:00Z", dateTimeParser.parseDateTimeToInstant("03/01/2011", "1").toString());
        assertEquals("2011-01-03T00:00:00Z", dateTimeParser.parseDateTimeToInstant("03/01/2011", "2").toString());
        assertEquals("2011-01-03T01:00:00Z", dateTimeParser.parseDateTimeToInstant("03/01/2011", "3A").toString());
        assertEquals("2011-01-03T02:00:00Z", dateTimeParser.parseDateTimeToInstant("03/01/2011", "4").toString());
    }

}

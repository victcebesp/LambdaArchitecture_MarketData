package projection;

import org.eifer.box.schemas.DayAheadReport;
import org.eifer.box.schemas.IntradayReport;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;
import static market.Type.*;

public class Report {

    private final Instant hourInstant;
    private final String priceZone;
    private final Map<String, Integer> halfHourIndexes;
    private final Map<String, Integer> quarterHourIndexes;
    private Row[] quarterHours;
    private Row[] halfHours;
    private Row hour;

    public Report(Instant instant, String priceZone) {
        this.hourInstant = instant.truncatedTo(HOURS);
        this.priceZone = priceZone;
        this.quarterHours = initializeQuarterHours();
        this.halfHours = initializeHalfHours();
        this.hour = initializeHour();
        this.halfHourIndexes = fillHalfHourIndexesMap();
        this.quarterHourIndexes = fillQuarterHourIndexesMap();
    }

    public String priceZone() {
        return priceZone;
    }

    public Row hour() {
        return hour;
    }

    public Row[] halfHours() {
        return halfHours;
    }

    public Row[] quarterHours() {
        return quarterHours;
    }

    public void updateDayAheadFields(DayAheadReport dayAheadReport) {
        hour.updateDayAheadRowData(dayAheadReport);
    }

    public void updateIntradayFields(IntradayReport intradayReport) {
        getRow(intradayReport).updateIntradayRowData(intradayReport);
    }

    private Row getRow(IntradayReport intradayReport) {
        if (intradayReport.type().equals(HOURLY.type()))
            return hour;
        else if (intradayReport.type().equals(HALFHOURLY.type()))
            return getHalfHourRow(intradayReport);
        else
            return getQuarterHourRow(intradayReport);
    }

    private Row getHalfHourRow(IntradayReport intradayReport) {
        Row row = halfHours[inferIndexFromInstant(intradayReport)];
        return row == null ? new Row().type(HALFHOURLY.type()) : row;
    }

    private int inferIndexFromInstant(IntradayReport intradayReport) {

        Map<String, Integer> indexes;

        if (intradayReport.type().equals(HALFHOURLY.type()))
            indexes = halfHourIndexes;
        else
            indexes = quarterHourIndexes;

        return indexes.get(extractMinutes(intradayReport.ts()));
    }

    private String extractMinutes(Instant instant) {
        String cutInstant = instant.toString().substring(instant.toString().indexOf(":") + 1);
        return cutInstant.substring(0, cutInstant.indexOf(":"));
    }

    private Row getQuarterHourRow(IntradayReport intradayReport) {
        Row row = quarterHours[inferIndexFromInstant(intradayReport)];
        return row == null ? new Row().type(QUARTERHOURLY.type()) : row;
    }

    private Row[] initializeQuarterHours() {
        Row [] rows = new Row[4];
        for (int i = 0; i < rows.length; i++)
            rows[i] = new Row().type(QUARTERHOURLY.type()).instant(hourInstant, i);
        return rows;
    }

    private Row[] initializeHalfHours() {
        Row [] rows = new Row[2];
        for (int i = 0; i < rows.length; i++)
            rows[i] = new Row().type(HALFHOURLY.type()).instant(hourInstant, i);
        return rows;
    }

    private Row initializeHour() {
        return new Row().type(HOURLY.type()).instant(hourInstant, 0);
    }

    private Map<String, Integer> fillHalfHourIndexesMap() {
        Map<String, Integer> halfHourIndexes = new HashMap<>();
        halfHourIndexes.put("00", 0);
        halfHourIndexes.put("30", 1);
        return halfHourIndexes;
    }

    private Map<String, Integer> fillQuarterHourIndexesMap() {
        Map<String, Integer> quarterHourIndexesMap = new HashMap<>();
        quarterHourIndexesMap.put("00", 0);
        quarterHourIndexesMap.put("15", 1);
        quarterHourIndexesMap.put("30", 2);
        quarterHourIndexesMap.put("45", 3);
        return quarterHourIndexesMap;
    }
}

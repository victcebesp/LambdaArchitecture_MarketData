package org.eifer.market.generators;

import org.eifer.box.schemas.DayAheadReport;
import org.eifer.market.parser.VerticalDayAheadDateTimeParser;

import java.util.List;
import java.util.stream.Collectors;

import static org.eifer.market.generators.Type.HOURLY;

public class VerticalDayAheadReportGenerator implements DayAheadGenerator {

    @Override
    public List<DayAheadReport> generateAllDayAheadReportEvents(List<String> records) {
        String date = records.remove(0).split(";")[1];

        return records.stream()
                .map(r -> generateDayAheadReportEventFrom(r, date))
                .collect(Collectors.toList());
    }

    private DayAheadReport generateDayAheadReportEventFrom(String record, String date) {

        String[] fields = record.split(";");

        int hour = 3;
        int priceZone = 1;
        int price = 4;
        int power = 5;

        return new DayAheadReport()
                .ts(new VerticalDayAheadDateTimeParser().parseDateTimeToInstant(date, getInitialHour(fields[hour])))
                .priceZone(fields[priceZone])
                .volume(fields[power] + " MW")
                .price(fields[price] + " €")
                .type(HOURLY.type());

    }

    private String getInitialHour(String hour) {
        return hour.substring(0, hour.indexOf('-'));
    }

}

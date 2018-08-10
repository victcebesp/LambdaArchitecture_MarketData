package org.eifer.market.generator;

import org.eifer.box.schemas.DayAheadReport;
import org.eifer.market.parser.HorizontalDayAheadDateTimeParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eifer.market.generator.Type.HOURLY;

public class HorizontalDayAheadReportGenerator implements DayAheadGenerator {

    @Override
    public List<DayAheadReport> generateAllDayAheadReportEvents(List<String> records) {
        String date = records.remove(0).split(";")[1];
        List<DayAheadReport> dayAheadReports = new ArrayList<>();

        List<String> filteredRecords = getAvailablePriceZonesRecords(records);

        Map<String, List<String>> classifiedPriceAndVolumes = classifyRecordsByPriceZone(filteredRecords);

        classifiedPriceAndVolumes.forEach((key, value) -> dayAheadReports.addAll(createAllAntiqueDayAheadReportFromRecord(value, date, key)));

        return dayAheadReports;
    }

    private List<String> getAvailablePriceZonesRecords(List<String> records) {
        return records.stream()
                .filter(r -> isAvailablePriceZone(r.split(";")[2]))
                .collect(Collectors.toList());
    }

    private boolean isAvailablePriceZone(String priceZone) {
        return "DE-AT".contains(priceZone) || "CH".contains(priceZone) || "FR".contains(priceZone);
    }

    private Map<String, List<String>> classifyRecordsByPriceZone(List<String> records) {

        Map<String, List<String>> classifiedPriceAndVolumes = new HashMap<>();
        classifiedPriceAndVolumes.put("DE-AT", new ArrayList<>(2));
        classifiedPriceAndVolumes.put("FR", new ArrayList<>(2));
        classifiedPriceAndVolumes.put("CH", new ArrayList<>(2));

        for (String record : records) {
            String recordPriceZone = record.split(";")[2];
            if ("DE-AT".contains(recordPriceZone)) {
                classifiedPriceAndVolumes.get("DE-AT").add(record);
            } else if ("FR".contains(recordPriceZone)) {
                classifiedPriceAndVolumes.get("FR").add(record);
            } else if ("CH".contains(recordPriceZone)) {
                classifiedPriceAndVolumes.get("CH").add(record);
            }
        }

        return classifiedPriceAndVolumes;
    }

    private List<DayAheadReport> createAllAntiqueDayAheadReportFromRecord(List<String> priceZonePriceAndVolume, String date, String priceZone) {

        if (priceZonePriceAndVolume.size() != 2) return new ArrayList<>();

        String[] hours = "1,2,3A,3B,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24".split(",");
        String [] prices = getPricesFrom(priceZonePriceAndVolume);
        String [] volumes = getVolumesFrom(priceZonePriceAndVolume);

        List<DayAheadReport> dayAheadReports = new ArrayList<>();

        for (int i = 0; i < hours.length; i++) {

            if (prices[i].equals("") || volumes[i].equals("")) continue;

            dayAheadReports.add(new DayAheadReport()
                    .price(prices[i] + " €")
                    .volume(volumes[i] + " MW")
                    .priceZone(priceZone)
                    .type(HOURLY.type())
                    .ts(new HorizontalDayAheadDateTimeParser().parseDateTimeToInstant(date, hours[i]))
            );
        }

        return dayAheadReports;

    }

    private String[] getPricesFrom(List<String> priceZonePriceAndVolume) {

        String [] prices = new String[25];
        String [] records;

        if (priceZonePriceAndVolume.get(0).startsWith("PR"))
            records = priceZonePriceAndVolume.get(0).split(";");
         else
            records = priceZonePriceAndVolume.get(1).split(";");

        System.arraycopy(records, 3, prices, 0, prices.length);

        return prices;
    }

    private String[] getVolumesFrom(List<String> priceZonePriceAndVolume) {

        String [] volumes = new String[25];
        String [] records;

        if (priceZonePriceAndVolume.get(0).startsWith("OM"))
            records = priceZonePriceAndVolume.get(0).split(";");
        else
            records = priceZonePriceAndVolume.get(1).split(";");

        for (int i = 0; i < volumes.length; i++) volumes[i] = records[i + 3];

        return volumes;
    }
}
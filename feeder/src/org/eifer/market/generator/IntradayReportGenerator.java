package org.eifer.market.generator;

import org.eifer.box.schemas.IntradayReport;
import org.eifer.market.parser.IntradayDateTimeParser;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eifer.market.generator.Type.*;

public class IntradayReportGenerator {

    private final Map<String, Map<Field, Integer>> indexes;

    private enum Field {
        DATE,
        FROM,
        ID3,
        WEIGHTEDPRICE
    }

    public IntradayReportGenerator (){
        indexes = createIndexes();
    }

    public List<IntradayReport> generateAllIntradayReportEvents(List<String> records, String prizeZone) {

        generateFieldIndexes(records.remove(0), prizeZone);

        return records.stream()
                .map(r -> generateIntradayReportEventFrom(r, prizeZone))
                .collect(Collectors.toList());

    }

    private Map<String, Map<Field, Integer>> createIndexes() {

        Map<String, Map<Field, Integer>> createdIndexes = new HashMap<>();

        createdIndexes.put("DE-AT", createFieldsMap());
        createdIndexes.put("FR", createFieldsMap());
        createdIndexes.put("CH", createFieldsMap());

        return createdIndexes;
    }

    private Map<Field, Integer> createFieldsMap() {
        Map<Field, Integer> fieldsMap = new EnumMap<>(Field.class);
        fieldsMap.put(Field.DATE, null);
        fieldsMap.put(Field.FROM, null);
        fieldsMap.put(Field.ID3, null);
        fieldsMap.put(Field.WEIGHTEDPRICE, null);

        return fieldsMap;
    }

    private void generateFieldIndexes(String record, String prizeZone) {
        Map<Field, Integer> generatedPriceZoneFields = createFieldsMap();
        String[] fields = record.split(",");

        for (int i = 0; i < fields.length; i++)
            if (fields[i].toLowerCase().contains("id3-price"))
                generatedPriceZoneFields.put(Field.ID3, i);
            else if (fields[i].toLowerCase().contains("weighted average price"))
                generatedPriceZoneFields.put(Field.WEIGHTEDPRICE, i);
            else if (fields[i].toLowerCase().contains("delivery day"))
                generatedPriceZoneFields.put(Field.DATE, i);
            else if (fields[i].toLowerCase().contains("hour from"))
                generatedPriceZoneFields.put(Field.FROM, i);

        indexes.put(prizeZone, generatedPriceZoneFields);
    }

    private IntradayReport generateIntradayReportEventFrom(String record, String priceZone) {

        String [] fields = record.split(",");

        return new IntradayReport()
                .ts(new IntradayDateTimeParser().parseDateTimeToInstant(get(Field.DATE, fields, priceZone), get(Field.FROM, fields, priceZone)))
                .priceZone(priceZone)
                .weightedPrice(get(Field.WEIGHTEDPRICE, fields, priceZone) + " €")
                .id3(get(Field.ID3, fields, priceZone) + " €")
                .type(inferTypeFrom(get(Field.FROM, fields, priceZone)));
    }

    private String get(Field field, String[] fields, String priceZone) {
        Integer index = indexes.get(priceZone).get(field);
        if (index == null) return "";
        return fields[index];
    }

    private String inferTypeFrom(String fromHour) {
        if (fromHour.contains("qh")) return QUARTERHOURLY.type();
        if (fromHour.contains("hh")) return HALFHOURLY.type();
        else return HOURLY.type();
    }


}

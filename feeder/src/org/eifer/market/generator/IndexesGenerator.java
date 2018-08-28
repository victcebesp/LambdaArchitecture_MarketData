package org.eifer.market.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class IndexesGenerator {

    private final Map<String, BiConsumer<String, Map<String, Integer>>> fieldFillers;

    public IndexesGenerator() {
        fieldFillers = fillFieldFiller();
    }

    public Map<String, Integer> getIndexes(List<String> indexesRecords) {
        return fillIndexesWith(indexesRecords);
    }

    private Map<String, BiConsumer<String, Map<String, Integer>>> fillFieldFiller() {
        Map<String, BiConsumer<String, Map<String, Integer>>> fieldFillers;
        fieldFillers = new HashMap<>();
        fieldFillers.put("GCIL", this::fillGcilFields);
        fieldFillers.put("GUIL", this::fillGuilFields);
        fieldFillers.put("PCIL", this::fillPcilFields);
        fieldFillers.put("COIL", this::fillCoilFields);
        return fieldFillers;
    }

    private Map<String, Integer> fillIndexesWith(List<String> indexesRecords) {

        Map<String, Integer> indexes = new HashMap<>();

        indexesRecords.forEach(e -> {
            String key = e.split(";")[0].substring(2).equals("ICIL") ? "GCIL" : e.split(";")[0].substring(2);
            fieldFillers.get(key).accept(e, indexes);
        });

        return indexes;
    }

    private void fillGcilFields(String indexRecord, Map<String, Integer> indexes) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].equals("[TimeStamp]")) indexes.put("ts", i);
            else if (splittedRecord[i].equals("[Capacity]") || splittedRecord[i].equals("[InstalledCapacity]")) indexes.put("gcilCapacity", i);
            else if (splittedRecord[i].equals("[UnitID]")) indexes.put("gcilUnitID", i);
        }
    }

    private void fillGuilFields(String indexRecord, Map<String, Integer> indexes) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].equals("[UnitID]")) indexes.put("guilUnitID", i);
            else if (splittedRecord[i].equals("[ProdConsID]")) indexes.put("guilPlantID", i);
            else if (splittedRecord[i].equals("[UnitName]")) indexes.put("guilUnitName", i);
            else if (splittedRecord[i].equals("[ConnectingArea]")) indexes.put("guilConnectingArea", i);
            else if (splittedRecord[i].equals("[Source]")) indexes.put("guilSource", i);
            else if (splittedRecord[i].equals("[StartDate]")) indexes.put("guilStartDate", i);
            else if (splittedRecord[i].equals("[EndDate]")) indexes.put("guilEndDate", i);
            else if (splittedRecord[i].equals("[Commercialisation]")) indexes.put("guilCommercialisation", i);
        }
    }

    private void fillPcilFields(String indexRecord, Map<String, Integer> indexes) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].equals("[CompanyID]")) indexes.put("pcilCompanyID", i);
            else if (splittedRecord[i].equals("[ProdConsID]")) indexes.put("pcilPlantID", i);
            else if (splittedRecord[i].equals("[ProdConsName]")) indexes.put("pcilPlantName", i);
            else if (splittedRecord[i].equals("[WGS84Latitude]")) indexes.put("pcilLatitude", i);
            else if (splittedRecord[i].equals("[WGS84Longitude]")) indexes.put("pcilLongitude", i);
            else if (splittedRecord[i].equals("[Country]")) indexes.put("pcilCountry", i);
            else if (splittedRecord[i].equals("[ReportingReason]")) indexes.put("pcilReportingReason", i);
        }
    }

    private void fillCoilFields(String indexRecord, Map<String, Integer> indexes) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].equals("[CompanyID]")) indexes.put("coilCompanyID", i);
            else if (splittedRecord[i].equals("[CompanyName]")) indexes.put("coilCompanyName", i);
        }
    }

}

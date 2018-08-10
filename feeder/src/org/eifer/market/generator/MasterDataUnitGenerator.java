package org.eifer.market.generator;

import org.eifer.box.schemas.MasterDataUnit;
import org.eifer.market.parser.MasterDataDateTimeParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MasterDataUnitGenerator {

    private int ts;
    private int guilUnitID;
    private int capacity;
    private int unitName;
    private int guilPlantID;
    private int connectingArea;
    private int source;
    private int startDate;
    private int endDate;
    private int latitude;
    private int longitude;
    private int country;
    private int reportingReason;
    private int coilCompanyName;
    private int coilCompanyID;
    private int gcilUnitID;
    private int pcilCompanyID;
    private int pcilPlantID;
    private int pcilPlantName;
    private final Map<String, Consumer<String>> fieldFillers;

    public MasterDataUnitGenerator(List<String> indexesRecords) {
        fieldFillers = fillFieldFiller();
        fillIndexesWith(indexesRecords);
    }

    private void fillIndexesWith(List<String> indexesRecords) {
        indexesRecords.forEach(e -> {
            String key = e.split(";")[0].substring(2).equals("ICIL") ? "GCIL" : e.split(";")[0].substring(2);
            fieldFillers.get(key).accept(e);
        });
    }

    public List<MasterDataUnit> generateAllEvents(Map<String, List<String>> classifiedRecords) {

        List<MasterDataUnit> masterDataUnits = new ArrayList<>();
        int first = 0;

        List<String> generationCapacityRecords = classifiedRecords.get("GCIL");
        List<String> generationUnitsRecords = classifiedRecords.get("GUIL");
        List<String> producerConsumerRecords = classifiedRecords.get("PCIL");
        List<String> companyRecords = classifiedRecords.get("COIL");

        generationCapacityRecords.forEach(e -> {

            String [] gcilFields = e.split(";");
            MasterDataUnit masterDataUnit = new MasterDataUnit();

            updateMasterDataUnitGcilFields(gcilFields, masterDataUnit);

            List<String> guilRecords = generationUnitsRecords.stream()
                    .filter(r -> r.split(";")[guilUnitID].equals(gcilFields[gcilUnitID]))
                    .collect(Collectors.toList());

            if (guilRecords.isEmpty()) return;

            String guil = guilRecords.get(first);

            String[] splittedGuil = guil.split(";");
            updateMasterDataUnitGuilFields(masterDataUnit, splittedGuil);


            String pcil = producerConsumerRecords.stream()
                    .filter(r -> r.split(";")[pcilPlantID].equals(splittedGuil[guilPlantID]))
                    .collect(Collectors.toList()).get(first);

            String[] splittedPcil = pcil.split(";");
            updateMasterDataUnitPcilFields(masterDataUnit, splittedPcil);

            String coil = companyRecords.stream()
                    .filter(r -> r.split(";")[coilCompanyID].equals(splittedPcil[pcilCompanyID]))
                    .collect(Collectors.toList()).get(first);

            String[] splittedCoil = coil.split(";");

            updateMasterDataUnitCoilFields(masterDataUnit, splittedCoil);

            masterDataUnits.add(masterDataUnit);

        });

        return masterDataUnits;
    }

    private void updateMasterDataUnitGcilFields(String[] fields, MasterDataUnit masterDataUnit) {
        masterDataUnit.unitID(fields[guilUnitID]);
        masterDataUnit.capacity(Double.parseDouble(fields[capacity].replace(',', '.')));
        masterDataUnit.ts(new MasterDataDateTimeParser().parseDateTimeToInstant(fields[ts]));
    }

    private void updateMasterDataUnitGuilFields(MasterDataUnit masterDataUnit, String[] splittedGuil) {
        masterDataUnit.source(splittedGuil[source]);
        masterDataUnit.connectingArea(splittedGuil[connectingArea]);
        masterDataUnit.startDate(splittedGuil[startDate]);
        masterDataUnit.endDate(splittedGuil[endDate]);
        masterDataUnit.unitName(splittedGuil[unitName]);
        masterDataUnit.plantID(splittedGuil[guilPlantID]);
    }

    private void updateMasterDataUnitPcilFields(MasterDataUnit masterDataUnit, String[] splittedPcil) {
        masterDataUnit.country(splittedPcil[country]);
        masterDataUnit.latitude(splittedPcil[latitude]);
        masterDataUnit.longitude(splittedPcil[longitude]);
        masterDataUnit.companyID(splittedPcil[coilCompanyID]);
        masterDataUnit.reportReason(splittedPcil[reportingReason]);
        masterDataUnit.plantName(splittedPcil[pcilPlantName]);
    }

    private void updateMasterDataUnitCoilFields(MasterDataUnit masterDataUnit, String [] splittedCoil) {
        masterDataUnit.companyName(splittedCoil[coilCompanyName]);
    }

    private void fillGcilFields(String indexRecord) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].contains("TimeStamp")) ts = i;
            if (splittedRecord[i].contains("Capacity")) capacity = i;
            if (splittedRecord[i].contains("UnitID")) gcilUnitID = i;
        }
    }

    private void fillGuilFields(String indexRecord) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].contains("UnitID")) guilUnitID = i;
            if (splittedRecord[i].contains("ProdConsID")) guilPlantID = i;
            if (splittedRecord[i].contains("UnitName")) unitName = i;
            if (splittedRecord[i].contains("ConnectingArea")) connectingArea = i;
            if (splittedRecord[i].contains("Source")) source = i;
            if (splittedRecord[i].contains("StartDate")) startDate = i;
            if (splittedRecord[i].contains("EndDate")) endDate = i;
        }
    }

    private void fillPcilFields(String indexRecord) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].contains("CompanyID")) pcilCompanyID = i;
            if (splittedRecord[i].contains("ProdConsID")) pcilPlantID = i;
            if (splittedRecord[i].contains("ProdConsName")) pcilPlantName = i;
            if (splittedRecord[i].contains("WGS84Latitude")) latitude = i;
            if (splittedRecord[i].contains("WGS84Longitude")) longitude = i;
            if (splittedRecord[i].contains("Country")) country = i;
            if (splittedRecord[i].contains("ReportingReason")) reportingReason = i;
        }
    }

    private void fillCoilFields(String indexRecord) {
        String[] splittedRecord = indexRecord.split(";");
        for (int i = 0; i < splittedRecord.length; i++){
            if (splittedRecord[i].contains("CompanyID")) coilCompanyID = i;
            if (splittedRecord[i].contains("CompanyName")) coilCompanyName = i;
        }
    }


    private Map<String, Consumer<String>> fillFieldFiller() {
        Map<String, Consumer<String>> fieldFillers;
        fieldFillers = new HashMap<>();
        fieldFillers.put("GCIL", this::fillGcilFields);
        fieldFillers.put("GUIL", this::fillGuilFields);
        fieldFillers.put("PCIL", this::fillPcilFields);
        fieldFillers.put("COIL", this::fillCoilFields);
        return fieldFillers;
    }
}




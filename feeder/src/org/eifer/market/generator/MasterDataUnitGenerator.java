package org.eifer.market.generator;

import org.eifer.box.schemas.EexMasterDataUnit;
import org.eifer.market.parser.MasterDataDateTimeParser;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.eifer.box.FeederBox.existingMasterDataUnits;
import static org.eifer.market.comparator.MasterDataUnitComparator.compare;

public class MasterDataUnitGenerator {

    private final Map<String, Integer> indexes;

    public MasterDataUnitGenerator(List<String> indexesRecords) {
         indexes = new IndexesGenerator().getIndexes(indexesRecords);
    }

    public List<EexMasterDataUnit> generateAllEvents(Map<String, List<String>> classifiedRecords) {

        List<EexMasterDataUnit> masterDataUnits = new ArrayList<>();

        List<String> generationCapacityRecords = classifiedRecords.get("GCIL");
        List<String> generationUnitsRecords = classifiedRecords.get("GUIL");
        List<String> producerConsumerRecords = classifiedRecords.get("PCIL");
        List<String> companyRecords = classifiedRecords.get("COIL");

        generationCapacityRecords.forEach(e -> {

            String [] gcilFields = e.split(";");

            Optional<String> guilRecord = generationUnitsRecords.stream()
                    .filter(r -> r.split(";")[indexes.get("guilUnitID")].equals(gcilFields[indexes.get("gcilUnitID")]))
                    .findFirst();

            if (!guilRecord.isPresent()) return;

            String[] guilFields = guilRecord.get().split(";");

            Optional<String> pcilRecord = producerConsumerRecords.stream()
                    .filter(r -> r.split(";")[indexes.get("pcilPlantID")].equals(guilFields[indexes.get("guilPlantID")]))
                    .findFirst();

            if (!pcilRecord.isPresent()) return;

            String[] pcilFields = pcilRecord.get().split(";");

            Optional<String> coilRecord = companyRecords.stream()
                    .filter(r -> r.split(";")[indexes.get("coilCompanyID")].equals(pcilFields[indexes.get("pcilCompanyID")]))
                    .findFirst();

            if (!coilRecord.isPresent()) return;

            String[] coilFields = coilRecord.get().split(";");

            EexMasterDataUnit masterDataUnit = createMasterDataUnit(gcilFields, guilFields, pcilFields, coilFields);

            if (isNotAnExistingUnit(masterDataUnit) || hasChanged(masterDataUnit)) {
                masterDataUnits.add(masterDataUnit);
                existingMasterDataUnits().put(masterDataUnit.unitID(), masterDataUnit);
            }

        });

        return masterDataUnits.stream().sorted(comparing(a -> a.ts().toString())).collect(Collectors.toList());
    }

    private EexMasterDataUnit createMasterDataUnit(String[] gcilFields, String[] guilFields, String[] pcilFields, String[] coilFields) {
        return new EexMasterDataUnit()
            .unitID(gcilFields[indexes.get("guilUnitID")])
            .capacity(Double.parseDouble(gcilFields[indexes.get("gcilCapacity")].replace(',', '.')) + "MW")
            .ts(new MasterDataDateTimeParser().parseDateTimeToInstant(gcilFields[indexes.get("ts")]))
            .source(guilFields[indexes.get("guilSource")])
            .connectingArea(guilFields[indexes.get("guilConnectingArea")])
            .startDate(guilFields[indexes.get("guilStartDate")])
            .endDate(guilFields[indexes.get("guilEndDate")])
            .unitName(guilFields[indexes.get("guilUnitName")])
            .plantID(guilFields[indexes.get("guilPlantID")])
            .country(pcilFields[indexes.get("pcilCountry")])
            .latitude(pcilFields[indexes.get("pcilLatitude")])
            .longitude(pcilFields[indexes.get("pcilLongitude")])
            .companyID(pcilFields[indexes.get("coilCompanyID")])
            .reportReason(toEnglish(pcilFields[indexes.get("pcilReportingReason")]))
            .plantName(pcilFields[indexes.get("pcilPlantName")])
            .companyName(coilFields[indexes.get("coilCompanyName")]);
    }

    private String toEnglish(String pcilReportingReason) {
        if (pcilReportingReason.equals("gesetzlich")) return "legally obliged";
        if (pcilReportingReason.equals("freiwillig")) return "voluntary";
        if (pcilReportingReason.equals("gesetzlich und freiwillig")) return "legally obliged and voluntary";
        else return "unknown";
    }

    private boolean hasChanged(EexMasterDataUnit masterDataUnit) {
        return !compare(existingMasterDataUnits().get(masterDataUnit.unitID()), masterDataUnit);
    }

    private boolean isNotAnExistingUnit(EexMasterDataUnit masterDataUnit) {
        return existingMasterDataUnits().get(masterDataUnit.unitID()) == null;
    }

}




package org.eifer.market.generator;

import org.eifer.box.schemas.MasterDataUnit;
import org.eifer.market.parser.MasterDataDateTimeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.eifer.box.FeederBox.existingMasterDataUnits;
import static org.eifer.market.comparator.MasterDataUnitComparator.compare;

public class MasterDataUnitGenerator {

    private final Map<String, Integer> indexes;

    public MasterDataUnitGenerator(List<String> indexesRecords) {
         indexes = new IndexesGenerator().getIndexes(indexesRecords);
    }

    public List<MasterDataUnit> generateAllEvents(Map<String, List<String>> classifiedRecords) {

        List<MasterDataUnit> masterDataUnits = new ArrayList<>();

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

            MasterDataUnit masterDataUnit = createMasterDataUnit(gcilFields, guilFields, pcilFields, coilFields);

            if (isNotAnExistingUnit(masterDataUnit) || hasChanged(masterDataUnit)) {
                masterDataUnits.add(masterDataUnit);
                existingMasterDataUnits().put(masterDataUnit.unitID(), masterDataUnit);
            }

        });

        return masterDataUnits;
    }

    private MasterDataUnit createMasterDataUnit(String[] gcilFields, String[] guilFields, String[] pcilFields, String[] coilFields) {
        return new MasterDataUnit()
            .unitID(gcilFields[indexes.get("guilUnitID")])
            .capacity(Double.parseDouble(gcilFields[indexes.get("gcilCapacity")].replace(',', '.')))
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
            .reportReason(pcilFields[indexes.get("pcilReportingReason")])
            .plantName(pcilFields[indexes.get("pcilPlantName")])
            .companyName(coilFields[indexes.get("coilCompanyName")]);
    }

    private boolean hasChanged(MasterDataUnit masterDataUnit) {
        return !compare(existingMasterDataUnits().get(masterDataUnit.unitID()), masterDataUnit);
    }

    private boolean isNotAnExistingUnit(MasterDataUnit masterDataUnit) {
        return existingMasterDataUnits().get(masterDataUnit.unitID()) == null;
    }

}




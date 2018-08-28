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
    private final Map<String, String> connectingAreaTranslations;

    public MasterDataUnitGenerator(List<String> indexesRecords) {
         indexes = new IndexesGenerator().getIndexes(indexesRecords);
         connectingAreaTranslations = createConnectingAreaTranslationMap();
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

            if (isUnexistingUnit(masterDataUnit) || hasChanged(masterDataUnit)) {
                masterDataUnits.add(masterDataUnit);
                existingMasterDataUnits().put(masterDataUnit.unitID(), masterDataUnit);
            }

        });

        return masterDataUnits.stream().sorted(comparing(a -> a.ts().toString())).collect(Collectors.toList());
    }

    private EexMasterDataUnit createMasterDataUnit(String[] gcilFields, String[] guilFields, String[] pcilFields, String[] coilFields) {
        return new EexMasterDataUnit()
            .unitID(gcilFields[indexes.get("guilUnitID")])
            .capacity(Double.parseDouble(gcilFields[indexes.get("gcilCapacity")].replace(',', '.')) + " MW")
            .ts(new MasterDataDateTimeParser().parseDateTimeToInstant(gcilFields[indexes.get("ts")]))
            .source(guilFields[indexes.get("guilSource")])
            .connectingArea(translateConnectingArea(guilFields[indexes.get("guilConnectingArea")]))
            .startDate(guilFields[indexes.get("guilStartDate")])
            .endDate(guilFields[indexes.get("guilEndDate")])
            .unitName(guilFields[indexes.get("guilUnitName")])
            .plantID(guilFields[indexes.get("guilPlantID")])
            .commercialisation(translateCommercialisation(guilFields[indexes.get("guilCommercialisation")]))
            .country(pcilFields[indexes.get("pcilCountry")])
            .latitude(pcilFields[indexes.get("pcilLatitude")])
            .longitude(pcilFields[indexes.get("pcilLongitude")])
            .reportReason(translateToEnglish(pcilFields[indexes.get("pcilReportingReason")]))
            .plantName(pcilFields[indexes.get("pcilPlantName")])
            .companyID(coilFields[indexes.get("coilCompanyID")])
            .companyName(coilFields[indexes.get("coilCompanyName")]);
    }

    private String translateCommercialisation(String commercialisation) {
        return commercialisation.equals("0") ? "mostly OTC" : commercialisation.equals("1") ? "mostly power exchange" : commercialisation;
    }

    private String translateToEnglish(String pcilReportingReason) {
        if (pcilReportingReason.equals("gesetzlich")) return "legally obliged";
        if (pcilReportingReason.equals("freiwillig")) return "voluntary";
        if (pcilReportingReason.equals("gesetzlich und freiwillig")) return "legally obliged and voluntary";
        else return "unknown";
    }

    private boolean hasChanged(EexMasterDataUnit masterDataUnit) {
        return !compare(existingMasterDataUnits().get(masterDataUnit.unitID()), masterDataUnit);
    }

    private boolean isUnexistingUnit(EexMasterDataUnit masterDataUnit) {
        return existingMasterDataUnits().get(masterDataUnit.unitID()) == null;
    }

    private Map<String, String> createConnectingAreaTranslationMap() {

        Map<String, String> translations = new HashMap<>();

        translations.put("10YDE-EON------1", "TenneT (DE) (formerly Transpower, E.ON)");
        translations.put("10YDE-ENBW-----N", "TransnetBW (formerly EnBW TNG)");
        translations.put("10YDE-RWENET---I", "Amprion (formerly RWE)");
        translations.put("10YDE-VE-------2", "50Hertz (formerly VE-T)");
        translations.put("10YAT-APG------L", "APG");
        translations.put("10YAT-VKW-UNG--K", "VKW-Netz");
        translations.put("10YCB-CZECH-REP5", "CEPS");
        translations.put("10YCH-SWISSGRIDZ", "Swissgrid");
        translations.put("10YCA-BULGARIA-R", "ESO EAD");
        translations.put("10YBE----------2", "Elia");
        translations.put("10YNL----------L", "TenneT (NL) ");
        translations.put("10YHU-MAVIR----U", "MAVIR");
        translations.put("37Y701133MH0000P", "GASPOOL");
        translations.put("21Y-ERTV-------8", "NetConnect Germany (NCG)");
        translations.put("21Y000000000025G", "MG-OST-AT - Market Area East AT (CEGH)");
        translations.put("21Y---A001A018-N", "VTP-CH (Swissgas)");
        translations.put("21Y---A001A001-B", "VOB-CZ (formerly RWE Transgas Net)");
        translations.put("21X-BG-A-A0A0A-C", "Bulgartransgaz");

        return translations;
    }

    private String translateConnectingArea(String connectingArea) {
        String translatedConnectedArea = connectingAreaTranslations.getOrDefault(connectingArea, "");
        return translatedConnectedArea.equals("") ? connectingArea : translatedConnectedArea;
    }

}




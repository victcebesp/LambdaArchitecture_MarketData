package org.eifer.market.feeder;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.FeederBox;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.schemas.MasterDataUnit;
import org.eifer.market.directorywalker.MasterDataDirectoryWalker;
import org.eifer.market.generator.MasterDataUnitGenerator;
import org.eifer.market.reader.MasterDataReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MasterDataFeeder implements Feeder {

    private final String path;

    public MasterDataFeeder(String path) {
        this.path = path;
    }

    @Override
    public void feedTank() throws IOException {

        new MasterDataDirectoryWalker().getFilePaths(path).forEach(p ->
            getEvents(p).forEach(e -> TanksConnectors.masterDataUnit().feed(Inl.toMessage(e)))
        );

    }

    private List<MasterDataUnit> getEvents(String path) {
        List<String> records = new MasterDataReader().readRecords(path);
        Map<String, List<String>> classifiedRecords = classifyRecords(records.stream().filter(e -> !e.startsWith("#")).collect(Collectors.toList()));
        List<String> indexesRecords = records.stream().filter(e -> e.startsWith("#")).collect(Collectors.toList());

        return new MasterDataUnitGenerator(indexesRecords).generateAllEvents(classifiedRecords);
    }

    private Map<String, List<String>> classifyRecords(List<String> records) {

        Map<String, List<String>> classifiedRecords = new HashMap<>();

        records.forEach(e -> {

            String key = e.split(";")[0].equals("ICIL") ? "GCIL" : e.split(";")[0];

            List<String> list = classifiedRecords.getOrDefault(key, new ArrayList<>());
            list.add(e);
            classifiedRecords.put(key, list);
        });

        return classifiedRecords;
    }

}

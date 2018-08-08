package org.eifer.market.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MasterDataReader implements Reader {

    @Override
    public List<String> readRecords(String path) {

        try {
            return Files.readAllLines(Paths.get(path)).stream()
                    .filter(this::isSuitableRecord)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean isSuitableRecord(String record) {
        String[] prefixs = "GCIL,GUIL,PCIL,COIL,ICIL,# GCIL,# GUIL,# PCIL,# COIL,# ICIL".split(",");
        for (String prefix : prefixs) if (record.startsWith(prefix)) return true;
        return false;
    }

}

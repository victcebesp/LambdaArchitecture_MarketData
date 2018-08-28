package org.eifer.market.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActualGenerationReader implements Reader {

    @Override
    public List<String> readRecords(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath)).stream()
                    .filter(line -> line.startsWith("AUGL"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

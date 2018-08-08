package org.eifer.market.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DayAheadReader implements Reader {

    @Override
    public List<String> readRecords(String path) {
        try {
            return Files.readAllLines(Paths.get(path), Charset.forName("UTF-8")).stream()
                    .filter(r -> r.startsWith("PR") || r.startsWith("ST") || r.startsWith("OM"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
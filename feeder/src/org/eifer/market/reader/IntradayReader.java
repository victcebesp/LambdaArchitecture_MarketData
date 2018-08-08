package org.eifer.market.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IntradayReader implements Reader {
    @Override
    public List<String> readRecords(String path) {
        try {
            List<String> records = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
            return records.subList(1, records.size());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

package org.eifer.market.reader;

import java.util.List;

public interface Reader {

    List<String> readRecords(String path);

}

package org.eifer.market.directorywalker;

import java.io.IOException;
import java.util.List;

public interface DirectoryWalker {

    List<String> getFilePaths(String path) throws IOException;

}

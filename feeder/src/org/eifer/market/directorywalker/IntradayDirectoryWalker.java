package org.eifer.market.directorywalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IntradayDirectoryWalker implements DirectoryWalker {
    @Override
    public List<String> getFilePaths(String directoryPath) throws IOException {
        return Files.walk(Paths.get(directoryPath))
                .map(Path::toString)
                .filter(s -> s.contains("intraday_results_hours"))
                .collect(Collectors.toList());
    }
}

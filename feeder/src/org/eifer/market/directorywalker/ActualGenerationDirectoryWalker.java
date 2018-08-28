package org.eifer.market.directorywalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ActualGenerationDirectoryWalker implements DirectoryWalker {

    @Override
    public List<String> getFilePaths(String path) throws IOException {
        return Files.walk(Paths.get(path))
                .map(Path::toString)
                .filter(e -> e.contains("ExPostInformationActualUnitGenerationPower"))
                .collect(Collectors.toList());
    }

}

package org.eifer.market.directorywalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VerticalDayAheadDirectoryWalker implements DirectoryWalker {
    @Override
    public List<String> getFilePaths(String directoryPath) throws IOException {
        Pattern pattern = Pattern.compile("PowerSpotMarketAuctionResults_[0-9]*.csv");

        return Files.walk(Paths.get(directoryPath))
                .map(Path::toString)
                .filter(e -> pattern.asPredicate().test(e))
                .sorted(Comparator.comparing(a -> a.substring(a.lastIndexOf("_"), a.lastIndexOf("."))))
                .collect(Collectors.toList());
    }
}

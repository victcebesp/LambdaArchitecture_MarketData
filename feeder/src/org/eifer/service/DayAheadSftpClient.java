package org.eifer.service;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DayAheadSftpClient extends SftpClient {

    private final String directoriesPath;

    public DayAheadSftpClient(String directoriesPath) {
        this.directoriesPath = directoriesPath;
    }

    @Override
    public SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException {
        for (int year = fromYear; year <= toYear; year++)
            retrieveDayAheadFilesFrom(directoriesPath + year + "/");
        return this;
    }

    private void retrieveDayAheadFilesFrom(String directory) throws IOException {
        List<String> daysDirectories = client.ls(directory).stream()
                .map(RemoteResourceInfo::getPath)
                .filter(e -> !e.endsWith(".csv"))
                .collect(Collectors.toList());

        List<String> dayAheadFilePaths = new ArrayList<>();
        Pattern pattern = Pattern.compile("PowerSpotMarketAuctionResults_[0-9]*.csv");

        for (String dayDirectory : daysDirectories) {
            dayAheadFilePaths.addAll(client.ls(dayDirectory).stream()
                    .map(RemoteResourceInfo::getPath)
                    .filter(e -> pattern.asPredicate().test(e) || e.contains("sarf_") || e.contains("lpx_smrf_"))
                    .collect(Collectors.toList()));
        }

        String destinyPath = "./tmp/marketData/" + getYearFrom(directory) + '/';
        new File(destinyPath).mkdirs();

        for (String dayAheadFilePath : dayAheadFilePaths)
            client.get(dayAheadFilePath, destinyPath + getFileName(dayAheadFilePath));
    }

    private String getFileName(String dayAheadFilePath) {
        String[] pathFields = dayAheadFilePath.split("/");
        return pathFields[pathFields.length - 1];
    }

    private String getYearFrom(String path) {
        String year = path.substring(0, path.lastIndexOf('/'));
        return year.substring(year.lastIndexOf('/') + 1);
    }

}

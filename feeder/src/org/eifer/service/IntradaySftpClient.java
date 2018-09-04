package org.eifer.service;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IntradaySftpClient extends SftpClient {

    private final String directoriesPath;

    public IntradaySftpClient(String directoriesPath) {
        this.directoriesPath = directoriesPath;
    }

    @Override
    public SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException {
        for (int year = fromYear; year <= toYear; year++)
            retrieveFilesFrom(directoriesPath + year + "/");
        return this;
    }

    private void retrieveFilesFrom(String directory) throws IOException {
        List<RemoteResourceInfo> ls = client.ls(directory);
        List<String> intradayResultFileNames = ls.stream()
                .map(RemoteResourceInfo::getName)
                .filter(r -> r.contains("intraday_results_hours_"))
                .collect(Collectors.toList());

        String destinyPath = "./tmp/marketData/" + getYearFrom(directory) + '/';
        new File(destinyPath).mkdirs();

        for (String intradayResultFile : intradayResultFileNames)
            client.get(directory + intradayResultFile, destinyPath + intradayResultFile);
    }

    private String getYearFrom(String path) {
        String year = path.substring(0, path.lastIndexOf('/'));
        return year.substring(year.lastIndexOf('/') + 1);
    }
}
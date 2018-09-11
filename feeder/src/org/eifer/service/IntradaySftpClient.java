package org.eifer.service;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IntradaySftpClient extends SftpClient {

    private final String directoriesPath;

    public IntradaySftpClient(String directoriesPath, String destinyPath) {
        this.directoriesPath = directoriesPath;
        this.destinyPath = destinyPath;
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

        String destiny = destinyPath + getYearFrom(directory) + '/';
        new File(destiny).mkdirs();

        for (String intradayResultFile : intradayResultFileNames)
            client.get(directory + intradayResultFile, destiny + intradayResultFile);
    }

    private String getYearFrom(String path) {
        String year = path.substring(0, path.lastIndexOf('/'));
        return year.substring(year.lastIndexOf('/') + 1);
    }
}
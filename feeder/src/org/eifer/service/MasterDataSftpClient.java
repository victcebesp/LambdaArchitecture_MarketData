package org.eifer.service;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MasterDataSftpClient extends SftpClient {

    private final String directoriesPath;

    public MasterDataSftpClient(String directoriesPath, String destinyPath) {
        this.directoriesPath = directoriesPath;
        this.destinyPath = destinyPath;
    }

    @Override
    public SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException {

        retrieveFilesFrom(directoriesPath);

        for (int year = fromYear; year <= toYear; year++)
            retrieveFilesFrom(directoriesPath + year + "/");
        return this;
    }

    private void retrieveFilesFrom(String directory) throws IOException {
        List<RemoteResourceInfo> ls = client.ls(directory);
        List<String> masterDataFileNames = ls.stream().map(RemoteResourceInfo::getName)
                .filter(r -> r.endsWith(".csv"))
                .collect(Collectors.toList());

        new File(destinyPath).mkdirs();

        for (String masterDataFile : masterDataFileNames)
            client.get(directory + masterDataFile, destinyPath + masterDataFile);
    }

}

package org.eifer.service;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActualGenerationSftpClient extends SftpClient {

    private final String directoriesPath;

    public ActualGenerationSftpClient(String directoriesPath) {
        this.directoriesPath = directoriesPath;
    }

    @Override
    public SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException {
        for (int year = fromYear; year <= toYear; year++)
            retrieveDayAheadFilesFrom(directoriesPath + year + "/");
        return this;
    }

    private void retrieveDayAheadFilesFrom(String directory) throws IOException {

        System.out.println(directory);

        List<String> daysDirectories = client.ls(directory).stream()
                .map(RemoteResourceInfo::getPath)
                .collect(Collectors.toList());

        List<String> actualGenerationFilePaths = new ArrayList<>();

        for (String dayDirectory : daysDirectories) {
            actualGenerationFilePaths.addAll(client.ls(dayDirectory).stream()
                    .map(RemoteResourceInfo::getPath)
                    .filter(e -> e.contains("ExPostInformationActualUnitGenerationPower"))
                    .collect(Collectors.toList()));
        }



        String destinyPath = "C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/";
        new File(destinyPath).mkdirs();

        System.out.println(actualGenerationFilePaths.size());

        for (String actualGenerationFilePath : actualGenerationFilePaths)
            client.get(actualGenerationFilePath, destinyPath);
    }
}
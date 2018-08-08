package org.eifer.service;

import cottons.utils.Files;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;

public abstract class SftpClient {

    SFTPClient client;

    public SftpClient connect(String serverIP, String username, String password) {
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        try {
            sshClient.connect(serverIP);
            sshClient.authPassword(username, password.toCharArray());
            client = sshClient.newSFTPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void closeConnection() throws IOException {
        Files.removeDir("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData");
        client.close();
    }

    public abstract SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException;

}

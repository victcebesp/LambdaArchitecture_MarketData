package org.eifer.service;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;

public abstract class SftpClient {

    public static SFTPClient client;

    public static void connect(String serverIP, String username, String password) {
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        try {
            sshClient.connect(serverIP);
            sshClient.authPassword(username, password.toCharArray());
            client = sshClient.newSFTPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection() throws IOException {
        client.close();
    }

    public abstract SftpClient retrieveFilesBetween(int fromYear, int toYear) throws IOException;

}

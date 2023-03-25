package de.lukegoll.application.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SFTPHost {

    @Value("${sftp.host}")
    private String sftpHost = "";

    @Value("${sftp.user}")
    private String sftpUser = "SFTP-User";
    @Value("${sftp.password}")
    private String sftpPassword = "";


    private JSch jsch;
    private ChannelSftp channel;
    private Session session;


    public SFTPHost() {
        jsch = new JSch();
    }


    /**
     * Authenticate with remote using password
     *
     * @param password password of remote
     * @throws JSchException If there is problem with credentials or connection
     */
    public void authPassword(String password) throws JSchException {
        session = jsch.getSession(sftpUser, sftpHost, 22);
        //disable known hosts checking
        //if you want to set knows hosts file You can set with jsch.setKnownHosts("path to known hosts file");
        var config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }


    public static void main(String[] args) throws JSchException {
        SFTPHost sftpHost1 = new SFTPHost();

            sftpHost1.authPassword(sftpHost1.getSftpPassword());

    }

    @Autowired
    public String getSftpHost() {
        return sftpHost;
    }

    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    @Autowired
    public String getSftpUser() {
        return sftpUser;
    }

    public void setSftpUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }

    @Autowired
    public String getSftpPassword() {
        return sftpPassword;
    }

    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }
}

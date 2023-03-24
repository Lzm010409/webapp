package de.lukegoll.application.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SFTPHost {

    @Value("${sftp.host}")
    private String sftpHost ="";

    @Value("${sftp.user}")
    private String sftpUser ="SFTP-User";
    @Value("${sftp.password}")
    private String sftpPassword="";

    public ChannelSftp setupSFTP() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(getSftpHost());
        Session jschSession = jsch.getSession(getSftpUser(), getSftpHost());
        jschSession.setPassword(getSftpPassword());
        jschSession.connect();
        System.out.println(jschSession.getHost());
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public static void main(String[] args) {
        SFTPHost sftpHost1 = new SFTPHost();
        try {
            sftpHost1.setupSFTP();
        } catch (Exception e) {

        }
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

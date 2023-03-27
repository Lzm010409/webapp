package de.lukegoll.application.smb;

import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SMBService {


    @Value("${smb.name}")
    String url;
    @Value("${smb.user}")
    String userName;
    @Value("${smb.password}")
    String password;

    public SMBService() {

    }

    public Session connect(String userName, String password) {
        SMBClient client = new SMBClient();
        Session session;
        try (Connection connection = client.connect(url)) {
            AuthenticationContext ac = new AuthenticationContext(userName, password.toCharArray(), null);
            session = connection.authenticate(ac);
            return session;
        } catch (Exception e) {
            return null;
        }
    }

    public void test(Session session){
        try (DiskShare share = (DiskShare) session.connectShare("TEST")) {
            for (FileIdBothDirectoryInformation f : share.list("WebApp-Test", "*")) {
                System.out.println("File : " + f.getFileName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*public void doRecursiveLookup(SmbFile smb) {
        try {
            if (smb.isDirectory()) {
                System.out.println(smb.getName());
                for (SmbFile f : smb.listFiles()) {
                    if (f.isDirectory()) {
                        doRecursiveLookup(f);
                    } else {
                        System.out.println("\t:" + f.getName());
                    }
                }
            } else {
                System.out.println("\t:" + smb.getName());
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


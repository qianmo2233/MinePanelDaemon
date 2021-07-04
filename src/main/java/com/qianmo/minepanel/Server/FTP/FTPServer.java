package com.qianmo.minepanel.Server.FTP;

import com.qianmo.minepanel.DaemonConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.DbUserManagerFactory;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration("FTP")
@Slf4j
public class FTPServer {
    protected FtpServer server;

    final DataSource dataSource;

    final DaemonConfiguration daemonConfiguration;

    public FTPServer(DataSource dataSource, DaemonConfiguration daemonConfiguration) {
        this.dataSource = dataSource;
        this.daemonConfiguration = daemonConfiguration;
        Init();
        log.info("FTP server is already Initialized!");
    }

    private void Init() {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(daemonConfiguration.getFtp_port());
        DataConnectionConfigurationFactory dataConnectionConfFactory = new DataConnectionConfigurationFactory();
        dataConnectionConfFactory.setPassivePorts("10000-10500");
        listenerFactory.setDataConnectionConfiguration(dataConnectionConfFactory.createDataConnectionConfiguration());

/*
        todo 增加SSL安全配置
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File("src/main/resources/ftpserver.jks"));
        ssl.setKeystorePassword("password");
        ssl.setSslProtocol("SSL");
        set the SSL configuration for the listener
        listenerFactory.setSslConfiguration(ssl.createSslConfiguration());
        listenerFactory.setImplicitSsl(true);
*/

        Listener listener = listenerFactory.createListener();
        serverFactory.addListener("default", listener);
        Map<String, Ftplet> ftpLets = new HashMap();
        ftpLets.put("ftpService", new FTPLet());
        serverFactory.setFtplets(ftpLets);
        DbUserManagerFactory dbUserManagerFactory = new DbUserManagerFactory();
        dbUserManagerFactory.setDataSource(dataSource);
        dbUserManagerFactory.setAdminName("admin");
        dbUserManagerFactory.setSqlUserAdmin("SELECT userid FROM FTP_USER WHERE userid='{userid}' AND userid='admin'");
        dbUserManagerFactory.setSqlUserInsert("INSERT INTO FTP_USER (userid, userpassword, homedirectory, " +
                "enableflag, writepermission, idletime, uploadrate, downloadrate) VALUES " +
                "('{userid}', '{userpassword}', '{homedirectory}', {enableflag}, " +
                "{writepermission}, {idletime}, uploadrate}, {downloadrate})");
        dbUserManagerFactory.setSqlUserDelete("DELETE FROM FTP_USER WHERE userid = '{userid}'");
        dbUserManagerFactory.setSqlUserUpdate("UPDATE FTP_USER SET userpassword='{userpassword}',homedirectory='{homedirectory}',enableflag={enableflag},writepermission={writepermission},idletime={idletime},uploadrate={uploadrate},downloadrate={downloadrate},maxloginnumber={maxloginnumber}, maxloginperip={maxloginperip} WHERE userid='{userid}'");
        dbUserManagerFactory.setSqlUserSelect("SELECT * FROM FTP_USER WHERE userid = '{userid}'");
        dbUserManagerFactory.setSqlUserSelectAll("SELECT userid FROM FTP_USER ORDER BY userid");
        dbUserManagerFactory.setSqlUserAuthenticate("SELECT userid, userpassword FROM FTP_USER WHERE userid='{userid}'");
        dbUserManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        serverFactory.setUserManager(dbUserManagerFactory.createUserManager());
        server = serverFactory.createServer();
    }

    public void Start(){
        try {
            server.start();
            log.info("FTP server is starting!");
            log.info("The FTP server runs on port " + daemonConfiguration.getFtp_port());
        }catch(FtpException e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        server.stop();
        log.info("FTP server is stopping!");
    }
}

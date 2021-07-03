package com.qianmo.minepanel.Service.Manager;

import com.qianmo.minepanel.Entity.FTPUserEntity;
import com.qianmo.minepanel.Service.CRUD.FTPUserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FTPUserManager {
    @Autowired
    private FTPUserCRUD ftpUserCRUD;

    public void add(String username, String password, String dir) {
        FTPUserEntity ftpUserEntity = new FTPUserEntity();
        ftpUserEntity.setUserid(username);
        ftpUserEntity.setUserpassword(password);
        ftpUserEntity.setHomedirectory(dir);
        ftpUserCRUD.Add(ftpUserEntity);
    }

    public void del(String username) {
        ftpUserCRUD.Delete(username);
    }

    public FTPUserEntity up(String username, String password, String dir) {
        FTPUserEntity ftpUserEntity = new FTPUserEntity();
        ftpUserEntity.setUserid(username);
        ftpUserEntity.setUserpassword(password);
        ftpUserEntity.setHomedirectory(dir);
        return ftpUserCRUD.Update(ftpUserEntity);
    }

    public List<FTPUserEntity> get() {
        return ftpUserCRUD.getAllUser();
    }

    public FTPUserEntity get(String username) {
        return ftpUserCRUD.getUser(username);
    }
}

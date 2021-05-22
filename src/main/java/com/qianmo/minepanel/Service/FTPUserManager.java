package com.qianmo.minepanel.Service;

import com.qianmo.minepanel.Entity.FTPUser;
import com.qianmo.minepanel.Repository.FTPUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FTPUserManager {
    @Resource
    private FTPUserRepository ftpUserRepository;

    public void Add(FTPUser ftpUser) {
        ftpUserRepository.save(ftpUser);
    }

    public void Delete(String username) {
        ftpUserRepository.deleteById(username);
    }

    public FTPUser Update(FTPUser ftpUser) {
        return ftpUserRepository.save(ftpUser);
    }

    public List<FTPUser> getAllUser() {
        return ftpUserRepository.findAll();
    }

    public FTPUser getUser(String username) {
        return ftpUserRepository.findById(username).orElse(null);
    }
}

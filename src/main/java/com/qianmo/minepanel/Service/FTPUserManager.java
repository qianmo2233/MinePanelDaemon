package com.qianmo.minepanel.Service;

import com.qianmo.minepanel.Entity.FTPUserEntity;
import com.qianmo.minepanel.Repository.FTPUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FTPUserManager {
    @Resource
    private FTPUserRepository ftpUserRepository;

    public void Add(FTPUserEntity ftpUserEntity) {
        ftpUserRepository.save(ftpUserEntity);
    }

    public void Delete(String username) {
        ftpUserRepository.deleteById(username);
    }

    public FTPUserEntity Update(FTPUserEntity ftpUserEntity) {
        return ftpUserRepository.save(ftpUserEntity);
    }

    public List<FTPUserEntity> getAllUser() {
        return ftpUserRepository.findAll();
    }

    public FTPUserEntity getUser(String username) {
        return ftpUserRepository.findById(username).orElse(null);
    }
}
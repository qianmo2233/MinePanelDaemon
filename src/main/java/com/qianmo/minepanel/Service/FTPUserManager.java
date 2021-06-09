package com.qianmo.minepanel.Service;

import com.qianmo.minepanel.Entity.FTPUser;
import com.qianmo.minepanel.Mapper.FTPUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FTPUserManager {
    @Resource
    private FTPUserMapper ftpUserMapper;

    public void Add(FTPUser ftpUser) {
        ftpUserMapper.insert(ftpUser);
    }

    public void Delete(String username) {
        ftpUserMapper.deleteById(username);
    }

    public int Update(FTPUser ftpUser) {
        return ftpUserMapper.updateById(ftpUser);
    }

    public List<FTPUser> getAllUser() {
        return ftpUserMapper.selectList(null);
    }

    public FTPUser getUser(String username) {
        return ftpUserMapper.selectById(username);
    }
}

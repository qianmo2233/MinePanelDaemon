package com.qianmo.minepanel.Service.CRUD;

import com.qianmo.minepanel.Entity.ServerEntity;
import com.qianmo.minepanel.Repository.ServerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerCRUD {
    @Resource
    private ServerRepository serverRepository;

    public ServerEntity Add(ServerEntity serverEntity){
        return serverRepository.save(serverEntity);
    }

    public void Delete(Integer id) {
        serverRepository.deleteById(id);
    }

    public ServerEntity Update(ServerEntity serverEntity) {
        return serverRepository.save(serverEntity);
    }

    public List<ServerEntity> getAllServer() {
        return serverRepository.findAll();
    }

    public ServerEntity getServer(Integer id) {
        return serverRepository.findById(id).orElse(null);
    }
}

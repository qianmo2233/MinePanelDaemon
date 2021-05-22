package com.qianmo.minepanel.Service;

import com.qianmo.minepanel.Entity.Server;
import com.qianmo.minepanel.Repository.ServerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerManager {
    @Resource
    private ServerRepository serverRepository;

    public Server Add(Server server){
        return serverRepository.save(server);
    }

    public void Delete(Integer id) {
        serverRepository.deleteById(id);
    }

    public Server Update(Server server) {
        return serverRepository.save(server);
    }

    public List<Server> getAllServer() {
        return serverRepository.findAll();
    }

    public Server getServer(Integer id) {
        return serverRepository.findById(id).orElse(null);
    }
}

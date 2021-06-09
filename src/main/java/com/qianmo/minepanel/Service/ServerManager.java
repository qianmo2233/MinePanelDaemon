package com.qianmo.minepanel.Service;

import com.qianmo.minepanel.Entity.Server;
import com.qianmo.minepanel.Mapper.ServerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerManager {
    @Resource
    private ServerMapper serverMapper;

    public void Add(Server server){
        serverMapper.insert(server);
    }

    public void Delete(Integer id) {
        serverMapper.deleteById(id);
    }

    public int Update(Server server) {
        return serverMapper.updateById(server);
    }

    public List<Server> getAllServer() {
        return serverMapper.selectList(null);
    }

    public Server getServer(Integer id) {
        return serverMapper.selectById(id);
    }
}

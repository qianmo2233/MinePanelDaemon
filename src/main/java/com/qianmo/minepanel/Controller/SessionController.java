package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Enum.ResponseCode;
import com.qianmo.minepanel.Response.SessionResponse;
import com.qianmo.minepanel.Response.UnauthorizedResponse;
import com.qianmo.minepanel.Server.WebSocket.SessionPool;
import com.qianmo.minepanel.Service.Manager.ServerManager;
import com.qianmo.minepanel.Utils.Common;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final DaemonConfiguration daemonConfiguration;
    private final ServerManager serverManager;

    public SessionController(DaemonConfiguration daemonConfiguration, ServerManager serverManager) {
        this.daemonConfiguration = daemonConfiguration;
        this.serverManager = serverManager;
    }

    @GetMapping("create")
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> createSession(@QueryParam("id") Integer id, @QueryParam("token") String token) {
        if(!daemonConfiguration.getToken().equals(token)) return new ResponseEntity<>(new UnauthorizedResponse().response, HttpStatus.UNAUTHORIZED);
        if(!serverManager.existsServer(id)) return new ResponseEntity<>(new SessionResponse(ResponseCode.NOT_FOUND, "Server not found").response, HttpStatus.NOT_FOUND);
        if(SessionPool.getSessions().containsValue(id)) {
            return new ResponseEntity<>(new SessionResponse(ResponseCode.SUCCESS, SessionPool.getSessions().inverse().get(id)).response, HttpStatus.OK);
        }
        String session = Common.getRandomString(10);
        SessionPool.getSessions().put(session, id);
        return new ResponseEntity<>(new SessionResponse(ResponseCode.SUCCESS, session), HttpStatus.OK);
    }
}

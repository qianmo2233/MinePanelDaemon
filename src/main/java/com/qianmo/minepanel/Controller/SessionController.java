package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.DaemonConfiguration;
import com.qianmo.minepanel.Enum.ResponseCode;
import com.qianmo.minepanel.Response.SessionResponse;
import com.qianmo.minepanel.Response.UnauthorizedResponse;
import com.qianmo.minepanel.Server.WebSocket.SessionPool;
import com.qianmo.minepanel.Service.Manager.ServerManager;
import com.qianmo.minepanel.Utils.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Component
@RestController
@Path("/session")
public class SessionController {

    private final DaemonConfiguration daemonConfiguration;
    private final ServerManager serverManager;

    public SessionController(DaemonConfiguration daemonConfiguration, ServerManager serverManager) {
        this.daemonConfiguration = daemonConfiguration;
        this.serverManager = serverManager;
    }

    @GET
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSession(@QueryParam("id") Integer id, @QueryParam("token") String token) {
        if(!daemonConfiguration.getToken().equals(token)) return new UnauthorizedResponse().get();
        if(!serverManager.existsServer(id)) return new SessionResponse(ResponseCode.NOT_FOUND, "Server not found").get();
        if(SessionPool.getSessions().containsValue(id)) {
            return new SessionResponse(ResponseCode.SUCCESS, SessionPool.getSessions().inverse().get(id)).get();
        }
        String session = Common.getRandomString(10);
        SessionPool.getSessions().put(session, id);
        return new SessionResponse(ResponseCode.SUCCESS, session).get();
    }
}

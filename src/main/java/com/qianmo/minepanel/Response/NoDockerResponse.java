package com.qianmo.minepanel.Response;

import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;

public class NoDockerResponse extends AbstractResponse{
    public NoDockerResponse() {
        response.put("code", ResponseCode.NOT_AVAILABLE.get());
        response.put("msg", "Docker is not available");
    }
    @Override
    public Response get() {
        return Response.status(ResponseCode.NOT_AVAILABLE.get()).entity(response).build();
    }
}

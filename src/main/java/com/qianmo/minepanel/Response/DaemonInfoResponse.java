package com.qianmo.minepanel.Response;

import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;

public class DaemonInfoResponse extends AbstractResponse{
    public DaemonInfoResponse() {
        response.put("code", ResponseCode.SUCCESS.get());
        response.put("version", "0.0.1");
        response.put("api", "0.0.1");
    }
    @Override
    public Response get() {
        return Response.status(ResponseCode.SUCCESS.get()).entity(response).build();
    }
}

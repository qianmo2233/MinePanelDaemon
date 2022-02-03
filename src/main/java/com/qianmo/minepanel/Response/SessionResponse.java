package com.qianmo.minepanel.Response;

import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;

public class SessionResponse extends AbstractResponse{
    public SessionResponse(ResponseCode code, String msg) {
        response.put("code", code.get());
        response.put("msg", msg);
    }

    @Override
    public Response get() {
        return Response.status(responseCode.get()).entity(response).build();
    }
}

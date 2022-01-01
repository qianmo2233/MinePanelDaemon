package com.qianmo.minepanel.Response;

import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;

public class UnauthorizedResponse extends AbstractResponse {
    public UnauthorizedResponse() {
        response.put("code", ResponseCode.UNAUTHORIZED.get());
        response.put("msg", "Unauthorized");
    }

    @Override
    public Response get() {
        return Response.status(ResponseCode.UNAUTHORIZED.get()).entity(response).build();
    }
}

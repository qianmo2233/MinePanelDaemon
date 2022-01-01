package com.qianmo.minepanel.Response;

import com.github.dockerjava.api.model.Container;
import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;
import java.util.List;

public class DockerContainersResponse extends AbstractResponse{
    public DockerContainersResponse(List<Container> containers) {
        response.put("code", ResponseCode.SUCCESS.get());
        response.put("containers", containers);
    }
    @Override
    public Response get() {
        return Response.status(ResponseCode.SUCCESS.get()).entity(response).build();
    }
}

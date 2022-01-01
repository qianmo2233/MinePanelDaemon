package com.qianmo.minepanel.Response;

import com.github.dockerjava.api.model.Image;
import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;
import java.util.List;

public class DockerImagesResponse extends AbstractResponse{
    public DockerImagesResponse(List<Image> images) {
        response.put("code", ResponseCode.SUCCESS.get());
        response.put("images", images);
    }
    @Override
    public Response get() {
        return Response.status(ResponseCode.SUCCESS.get()).entity(response).build();
    }
}

package com.qianmo.minepanel.Response;

import com.qianmo.minepanel.Enum.ResponseCode;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResponse {
    public ResponseCode responseCode;
    public Map<Object, Object> response = new HashMap<>();

    public abstract Response get();
}

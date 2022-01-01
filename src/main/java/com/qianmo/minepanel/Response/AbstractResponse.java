package com.qianmo.minepanel.Response;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResponse {
    Map<Object, Object> response = new HashMap<>();

    public abstract Response get();
}

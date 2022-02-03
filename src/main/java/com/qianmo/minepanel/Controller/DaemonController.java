package com.qianmo.minepanel.Controller;

import com.qianmo.minepanel.Response.DaemonInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/daemon")
public class DaemonController {
    @RequestMapping("info")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> getInfo() {
        return new ResponseEntity<>(new DaemonInfoResponse().response, HttpStatus.OK);
    }
}

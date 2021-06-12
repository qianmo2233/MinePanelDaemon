package com.qianmo.minepanel.Container;

import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContainerEntity {
    private String id;
    private Process process;
    private InputStream inputStream;
    private OutputStream outputStream;

    private List consoles = new ArrayList();

    public ContainerEntity(String id, Process process, InputStream inputStream, OutputStream outputStream) {
        this.id = id;
        this.process = process;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
}

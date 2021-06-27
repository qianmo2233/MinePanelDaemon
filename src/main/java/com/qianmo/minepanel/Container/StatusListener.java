package com.qianmo.minepanel.Container;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatusListener implements Runnable{
    private final Process process;
    private final Integer id;

    public StatusListener(Process process, Integer id) {
        this.process = process;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int code = process.waitFor();
            if (code != 0) {
                log.warn("Container " + id + " has crashed or killed");
            } else {
                log.info("Container " + id + " has stopped");
            }
            if(ContainerManager.getContainer().containsKey(id)){
                ContainerManager.destroy(id);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

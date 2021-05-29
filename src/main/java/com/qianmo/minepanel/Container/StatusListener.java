package com.qianmo.minepanel.Container;

import com.qianmo.minepanel.MinePanelApplication;

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
                MinePanelApplication.getLogger().warn("Container " + id + " has crashed or killed");
            } else {
                MinePanelApplication.getLogger().info("Container " + id + " has stopped");
            }
            if(ContainerManager.getContainer().containsKey(id)){
                ContainerManager.destroy(id);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
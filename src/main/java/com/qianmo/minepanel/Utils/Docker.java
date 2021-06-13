package com.qianmo.minepanel.Utils;

import com.github.dockerjava.api.model.Container;

import java.util.List;

public class Docker {
    public static boolean hasContainer(String id, List<Container> list) {
        for(Container container : list) {
            if(container.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static Container getContainer(String id, List<Container> list) {
        for(Container container : list) {
            if(container.getId().equals(id)) {
                return container;
            }
        }
        return null;
    }
}

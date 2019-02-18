package com.njz.letsgoapp.util.rxbus.busEvent;

import com.njz.letsgoapp.bean.server.ServerItem;

/**
 * Created by LGQ
 * Time: 2019/2/18
 * Function:
 */

public class ServerSelectedEvent {
    ServerItem serverItem;

    public ServerSelectedEvent(ServerItem serverItem) {
        this.serverItem = serverItem;
    }

    public ServerItem getServerItem() {
        return serverItem;
    }
}

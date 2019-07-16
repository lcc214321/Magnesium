package net.onebean.server.mngt.api.model;

import java.util.ArrayList;
import java.util.List;

public class UpSteamSyncNodeVo {
    private String nodeName;

    private List<UpSteamSyncNodeChildVo> nodes = new ArrayList<>();

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<UpSteamSyncNodeChildVo> getNodes() {
        return nodes;
    }

    public void setNodes(List<UpSteamSyncNodeChildVo> nodes) {
        this.nodes = nodes;
    }
}

package com.influx.pojo;

public class NodeInfoTest {
    private String nodeId;
    private String nodeName;
    private Integer nodeType;
    private Integer continueSucessCount;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getContinueSucessCount() {
        return continueSucessCount;
    }

    public void setContinueSucessCount(Integer continueSucessCount) {
        this.continueSucessCount = continueSucessCount;
    }
}

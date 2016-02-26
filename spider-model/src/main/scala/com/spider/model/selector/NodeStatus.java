package com.spider.model.selector;

/**
 * Created by jason on 16-2-26.
 */
public enum  NodeStatus {
    UNKNOW((byte) 0),
    MASTER((byte) 1);

    private byte value;

    private NodeStatus(byte value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    public static void main(String[] args) {

        NodeStatus status = NodeStatus.MASTER;

        if (NodeStatus.MASTER == status) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}

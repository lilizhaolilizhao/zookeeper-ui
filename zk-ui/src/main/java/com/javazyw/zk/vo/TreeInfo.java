package com.javazyw.zk.vo;

import org.apache.zookeeper.data.Stat;

public class TreeInfo {

    private Stat stat;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }


}

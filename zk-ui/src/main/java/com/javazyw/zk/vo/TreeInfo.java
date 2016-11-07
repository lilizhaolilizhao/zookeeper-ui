package com.javazyw.zk.vo;

import org.apache.zookeeper.data.Stat;

public class TreeInfo {

    private Stat stat;

    private String data;

    private String dataFormat;

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

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

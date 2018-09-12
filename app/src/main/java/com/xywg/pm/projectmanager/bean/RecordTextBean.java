package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/7/17
 */
public class RecordTextBean implements Serializable {

    private String recordText;
    private String recordUrl;
    private int recordCount;

    public RecordTextBean(String recordText, String recordUrl, int recordCount) {
        this.recordText = recordText;
        this.recordUrl = recordUrl;
        this.recordCount = recordCount;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}

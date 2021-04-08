package com.netcracker.wizardapp.domain;

import java.io.Serializable;

public class Note implements Serializable{
    Long pageId;
    Long buttonId;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getButtonId() {
        return buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "pageId=" + pageId +
                ", buttonId=" + buttonId +
                '}';
    }
}

package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.wizardapp.serializeservice.ButtonSerializer;
import com.netcracker.wizardapp.serializeservice.PageSerializer;

import java.io.Serializable;

public class Note implements Serializable{
    @JsonSerialize(using = PageSerializer.class)
    Page page;
    @JsonSerialize(using = ButtonSerializer.class)
    Button button;

    public Note(Page page, Button button) {
        this.page = page;
        this.button = button;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}

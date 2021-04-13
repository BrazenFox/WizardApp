package com.netcracker.wizardapp.payload.request;

import com.netcracker.wizardapp.domain.Note;

import java.util.List;

public class ResultRequest {
    Long wizard_id;
    Long user_id;
    List<Note> notes;

    public Long getWizard_id() {
        return wizard_id;
    }

    public void setWizard_id(Long wizard_id) {
        this.wizard_id = wizard_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}

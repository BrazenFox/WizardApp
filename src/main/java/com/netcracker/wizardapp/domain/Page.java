package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private Long number;

    private String content;

    @ManyToOne
    @JoinColumn(name = "wizard_id", referencedColumnName = "id")
    @JsonBackReference
    private Wizard wizard;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Button> buttons;

    public Page() {
    }

    public Page(String name, Wizard wizard) {
        this.name = name;
        this.wizard = wizard;
    }

    public Page(String name, Wizard wizard, Long number, String content) {
        this.name = name;
        this.wizard = wizard;
        this.content = content;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }


    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", content='" + content + '\'' +
                ", wizard=" + wizard +
                ", buttons=" + buttons +
                '}';
    }
}

package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PageTypes type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "wizard_id", referencedColumnName = "id")
    @JsonBackReference
    private Wizard wizard;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Button> buttons;

    /*@OneToMany(mappedBy = "toPage", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Button> linkButtons;*/


    public Page() {
    }

    public Page(String name, Wizard wizard) {
        this.name = name;
        this.wizard = wizard;
    }

    public Page(String name, Wizard wizard, String content) {
        this.name = name;
        this.wizard = wizard;
        this.content = content;
    }

    public Page(String name, Wizard wizard, String content, PageTypes type) {
        this.name = name;
        this.wizard = wizard;
        this.content = content;
        this.type=type;
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

    public void addButton(Button button) {
        this.buttons.add(button);
    }

    public void removeButton(Button button) {
        this.buttons.remove(button);
    }

    public PageTypes getType() {
        return type;
    }

    public void setType(PageTypes type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", wizard=" + wizard +
                ", buttons=" + buttons +
                '}';
    }
}

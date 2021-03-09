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
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "wizard_id", referencedColumnName = "id")
    @JsonBackReference
    private Wizard wizard;

    @OneToMany(mappedBy="page", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Button> buttons;

    public Page() {
    }

    public Page(String name, Wizard wizard) {
        this.name = name;
        this.wizard = wizard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addButton(Button button){
        this.buttons.add(button);
    }
    
}

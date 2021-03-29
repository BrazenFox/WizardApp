package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wizard",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Wizard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "wizard", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Page> pages;

    public Wizard() {
    }

    public Wizard(String name) {
        this.name = name;
    }

    public Wizard(String name, User creator) {
        this.creator = creator;
        this.name = name;
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

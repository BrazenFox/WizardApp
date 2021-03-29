package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    @JsonBackReference
    private Page page;

    ///////////////
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_page_id", referencedColumnName = "id")
    private Page toPage;*/
    private Long toPage;
    /////////////////

    public Button() {
    }

    public Button(String name, Page page) {
        this.name = name;
        this.page = page;
    }

    public Button(String name, Page page, Long toPage) {
        this.name = name;
        this.page = page;
        this.toPage = toPage;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long getToPage() {
        return toPage;
    }

    public void setToPage(Long toPage) {
        this.toPage = toPage;
    }


    @Override
    public String toString() {
        return "Button{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", page=" + page +
                ", toPage=" + toPage +
                '}';
    }
}

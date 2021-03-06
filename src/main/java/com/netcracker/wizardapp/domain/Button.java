package com.netcracker.wizardapp.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.wizardapp.serializeservice.PageSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "toPage"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "toPage")
public class Button implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    @JsonBackReference
    private Page page;

    ///////////////@JsonIgnore
    @JsonSerialize(using = PageSerializer.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_page_id", referencedColumnName = "id")
    //@JsonBackReference
    private Page toPage;
    /////////////////

    public Button() {
    }

    public Button(String name, Page page) {
        this.name = name;
        this.page = page;
    }

    public Button(String name, Page page, Page toPage) {
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

    public Page getToPage() {
        return toPage;
    }

    public void setToPage(Page toPage) {
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

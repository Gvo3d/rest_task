package org.yakimovdenis.resttask.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="contacts")
public class Contact implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name")
    private String name;

    public Contact(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact() {
    }
}

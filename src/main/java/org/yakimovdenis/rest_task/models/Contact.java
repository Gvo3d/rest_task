package org.yakimovdenis.rest_task.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name")
    private String name;

    public Contact(String name) {
        this.name = name;
    }

    public Contact() {
    }
}

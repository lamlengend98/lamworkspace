package com.example.demoFlyway.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    /*

     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Size(min = 1, max = 100)
    private int id;

    @Column(name = "name", nullable = false)
    @Size(max = 100)
    private String name;

    @Column(name = "email")
    @Size(max = 100)
    private String email;


    public User() {
        super();
    }

    public User(int id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email=email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
package ru.otus.dobrovolsky.users;

import com.google.gson.Gson;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = -9133935693716266029L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "age", length = 3, nullable = false)
    private int age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String toString() {

        return new Gson().toJson(this);
    }
}

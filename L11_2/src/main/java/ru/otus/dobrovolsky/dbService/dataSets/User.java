package ru.otus.dobrovolsky.dbService.dataSets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User extends DataSet implements Serializable {
    private static final long serialVersionUID = -3965617320693081552L;

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

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", age='" + age + "\'" +
                '}';
    }
}

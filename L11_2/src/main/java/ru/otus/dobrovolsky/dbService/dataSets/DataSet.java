package ru.otus.dobrovolsky.dbService.dataSets;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class DataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

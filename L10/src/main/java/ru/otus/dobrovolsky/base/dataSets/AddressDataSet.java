package ru.otus.dobrovolsky.base.dataSets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    @Column(name = "postal_index")
    private int index;

    public AddressDataSet() {
    }

    public AddressDataSet(String street, int index) {
        this.street = street;
        this.index = index;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "street='" + street + '\'' +
                ", index=" + index +
                '}';
    }
}

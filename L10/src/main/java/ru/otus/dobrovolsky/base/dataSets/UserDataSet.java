package ru.otus.dobrovolsky.base.dataSets;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = PhoneDataSet.class, mappedBy = "user", orphanRemoval = true)
    private List<PhoneDataSet> phones;

    public UserDataSet() {
    }

    public UserDataSet(long id, String name, AddressDataSet address, PhoneDataSet... phones) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPhones(Arrays.asList(phones));
        Arrays.asList(phones).forEach((p) -> p.setUser(this));
    }

    public UserDataSet(String name, AddressDataSet address, PhoneDataSet... phones) {
        this.setId(-1);
        this.setName(name);
        this.setAddress(address);
        this.setPhones(Arrays.asList(phones));
        Arrays.asList(phones).forEach((p) -> p.setUser(this));

    }

    public UserDataSet(String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.setId(-1);
        this.setName(name);
        this.setAddress(address);
        this.setPhones(phones);
        phones.forEach((p) -> p.setUser(this));
    }

    public String getName() {
        return name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAddress(AddressDataSet address) {
        this.address = address;
    }

    private void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", address=" + address.toString() +
                ", phones=" + phones.toString() +
                '}';
    }
}
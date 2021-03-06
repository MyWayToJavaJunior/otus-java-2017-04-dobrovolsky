package ru.otus.dobrovolsky.backend.dataSet;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@NamedQueries({
        @NamedQuery(
                name = "namedQuery",
                query = "from UserDataSet u where u.id = :id",
                hints = {
                        @QueryHint(
                                name = "org.hibernate.cacheable",
                                value = "true")
                }
        )
})
@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AddressDataSet.class, mappedBy = "user", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = PhoneDataSet.class, mappedBy = "user", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<PhoneDataSet> phones;

    public UserDataSet() {
    }

    public UserDataSet(long id, String name, AddressDataSet address, PhoneDataSet... phones) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        address.setUser(this);
        this.setPhones(Arrays.asList(phones));
        Arrays.asList(phones).forEach((p) -> p.setUser(this));
    }

    public UserDataSet(String name, AddressDataSet address, PhoneDataSet... phones) {
        this.setId(-1);
        this.setName(name);
        this.setAddress(address);
        address.setUser(this);
        this.setPhones(Arrays.asList(phones));
        Arrays.asList(phones).forEach((p) -> p.setUser(this));

    }

    public UserDataSet(String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.setId(-1);
        this.setName(name);
        this.setAddress(address);
        address.setUser(this);
        this.setPhones(phones);
        phones.forEach((p) -> p.setUser(this));
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    private void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
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

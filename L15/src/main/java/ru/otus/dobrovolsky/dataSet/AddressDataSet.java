package ru.otus.dobrovolsky.dataSet;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    @Column(name = "postal_index")
    private int index;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public AddressDataSet() {
    }

    public AddressDataSet(String street, int index) {
        this.street = street;
        this.index = index;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "street='" + street + '\'' +
                ", index=" + index +
                '}';
    }
}

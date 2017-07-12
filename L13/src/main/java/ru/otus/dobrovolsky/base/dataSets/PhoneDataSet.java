package ru.otus.dobrovolsky.base.dataSets;

import javax.persistence.*;

@Entity
@Table(name = "phones")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.cache.annotation.Cacheable("ru.otus.dobrovolsky.base.dataSets.PhoneDataSet")
public class PhoneDataSet extends DataSet {

    @Column(name = "code")
    private int code;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(int code, String number) {
        this.code = code;
        this.number = number;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "code=" + code +
                ", number='" + number + '\'' +
                '}';
    }
}
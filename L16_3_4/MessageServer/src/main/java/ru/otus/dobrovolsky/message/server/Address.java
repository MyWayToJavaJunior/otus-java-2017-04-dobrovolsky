package ru.otus.dobrovolsky.message.server;

public class Address {
    private final String id;

    public Address(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
}

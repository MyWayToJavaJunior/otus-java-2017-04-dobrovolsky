package ru.otus.dobrovolsky.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Dummy {
    private int id;
    private String name;
    private boolean bool;
    private char ch;
    private double aDouble;
    private List<String> books;

    public Dummy() {
        Random random = new Random();
        id = random.nextInt();
        bool = true;
        setCh();
        setaDouble();
        books = new ArrayList<>();
    }

    public Dummy(String name) {
        Random random = new Random();
        id = random.nextInt();
        this.name = name;
        bool = true;
        setCh();
        setaDouble();
        books = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks() {
        books.add("The Land of Crimson Clouds");
        books.add("Hard to Be a God");
        books.add("Monday Begins on Saturday");
        books.add("Prisoners of Power");
        books.add("Beetle in the Anthill");
        books.add("The Time Wanderers");
        books.add("Roadside Picnic");
        books.add("The Doomed City");
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public void setCh() {
        Random random = new Random();
        ch = (char) (random.nextInt(26) + 'a');
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public void setaDouble() {
        Random random = new Random();
        aDouble = random.nextDouble();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBool() {
        return bool;
    }

    public char getCh() {
        return ch;
    }

    public double getaDouble() {
        return aDouble;
    }

    public static Dummy buildDummy() {
        Dummy dummy = new Dummy("Test Dummy1111");

        dummy.setBooks();
        return dummy;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}

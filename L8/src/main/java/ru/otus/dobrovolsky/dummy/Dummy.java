package ru.otus.dobrovolsky.dummy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ketaetc on 29.05.17.
 */
public class Dummy {
    private int id;
    private String name;
    private List<String> books = new ArrayList<>();
    private Set<String> articles = new HashSet<>();
    private ObjectDummy objectDummy;
    private List<ObjectDummy> someList = new ArrayList<>();

    public Dummy() {
        Random random = new Random();
        id = random.nextInt();
        objectDummy = new ObjectDummy();
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
    }

    public Dummy (String name) {
        Random random = new Random();
        id = random.nextInt();
        this.name = name;
        objectDummy = new ObjectDummy();
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<String> books) {
        this.books = books;
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

    public void setArticles(Set<String> articles) {
        this.articles = articles;
    }

    public void setArticles() {
        articles.add("First");
        articles.add("Second");
        articles.add("Third");
        articles.add("Fourth");
        articles.add("Fifths");
    }

    public void setObjectDummy(ObjectDummy objectDummy) {
        this.objectDummy = objectDummy;
    }

    public void setSomeList(List<ObjectDummy> someList) {
        this.someList = someList;
    }

    public void setSomeList() {
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
        someList.add(new ObjectDummy());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getBooks() {
        return books;
    }

    public Set<String> getArticles() {
        return articles;
    }

    public ObjectDummy getObjectDummy() {
        return objectDummy;
    }

    public List<ObjectDummy> getSomeList() {
        return someList;
    }

    private class ObjectDummy {
        private String label;

        private ObjectDummy() {
            Random random = new Random();
            label = new String(Double.toString(random.nextInt()));
        }

        @Override
        public String toString() {
            return "label:   " + this.label;
        }
    }

    public static Dummy buildDummy() {
        Dummy dummy = new Dummy("Test Dummy");

        dummy.setArticles();
        dummy.setBooks();
        dummy.setSomeList();

        return dummy;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Dummy:\n"
                + "id:  " + this.id + "\n"
                + "name:    " + this.name + "\n"
                + "books:\n" + this.books.stream().collect(Collectors.joining(",\n")) + "\n"
                + "articles:\n" + this.articles.stream().collect(Collectors.joining(",\n")) + "\n"
                + "objectDummy:\n" + this.objectDummy.toString() + "\n"
                + "someList:\n" + this.someList.stream().map(d -> d.toString()).collect(Collectors.joining(",\n"));
    }
}

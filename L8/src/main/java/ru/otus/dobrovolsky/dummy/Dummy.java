package ru.otus.dobrovolsky.dummy;

import java.util.*;

@SuppressWarnings("unused")
public class Dummy {
    private int id;
    private String name;
    private int[] intArr = new int[4];
    private boolean[] intBoolArr = new boolean[4];
    private Integer[] intObjArr = new Integer[4];
    private String[] strObjArr = new String[4];
    private List<String> books = new ArrayList<>();
    private Set<String> articles = new HashSet<>();
    private SmallDummy smallDummy;
    private List<SmallDummy> someList = new ArrayList<>();
    private Map<Integer, String> someMap = new HashMap<>();
    private boolean bool;
    private char[] charArr = new char[4];
    private char ch;
    private double aDouble;

    public Dummy() {
        Random random = new Random();
        id = random.nextInt();
        setIntArr();
        setIntBoolArr();
        setIntObjArr();
        setStrObjArr();
        setObjectDummy();
        setSomeMap();
        bool = true;
        setCharArr();
        setCh();
        setaDouble();
    }

    public Dummy(String name) {
        Random random = new Random();
        id = random.nextInt();
        this.name = name;
        setIntArr();
        setIntBoolArr();
        setIntObjArr();
        setStrObjArr();
        setObjectDummy();
        setSomeMap();
        bool = true;
        setCharArr();
        setCh();
        setaDouble();
    }

    public static Dummy buildDummy() {
        Dummy dummy = new Dummy("Test Dummy");

        dummy.setArticles();
        dummy.setBooks();
        dummy.setSomeList();

        return dummy;
    }

    public void setIntArr() {
        Random random = new Random();
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = Math.abs(random.nextInt());
        }
    }

    public void setIntBoolArr() {
        Random random = new Random();
        for (int i = 0; i < intBoolArr.length; i++) {
            intBoolArr[i] = random.nextBoolean();
        }
    }

    public void setIntObjArr() {
        Random random = new Random();
        for (int i = 0; i < intObjArr.length; i++) {
            intObjArr[i] = Math.abs(random.nextInt());
        }
    }

    public void setStrObjArr(String[] strObjArr) {
        this.strObjArr = strObjArr;
    }

    public void setStrObjArr() {
        Random random = new Random();
        for (int i = 0; i < strObjArr.length; i++) {
            strObjArr[i] = Integer.toString(random.nextInt());
        }
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

    public void setArticles() {
        articles.add("First");
        articles.add("Second");
        articles.add("Third");
        articles.add("Fourth");
        articles.add("Fifths");
    }

    public void setObjectDummy() {
        smallDummy = new SmallDummy();
        smallDummy = new SmallDummy();
        smallDummy = new SmallDummy();
        smallDummy = new SmallDummy();
        smallDummy = new SmallDummy();
        smallDummy = new SmallDummy();
    }

    public void setSomeList() {
        someList.add(new SmallDummy());
        someList.add(new SmallDummy());
        someList.add(new SmallDummy());
        someList.add(new SmallDummy());
        someList.add(new SmallDummy());
    }

    public void setSomeMap() {
        Random random = new Random();

        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
        someMap.put(Math.abs(random.nextInt()), Integer.toString(random.nextInt()));
    }

    public void setCharArr(char[] charArr) {
        this.charArr = charArr;
    }

    public void setCharArr() {
        Random random = new Random();
        for (int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (random.nextInt(26) + 'a');
        }
    }

    public void setCh() {
        Random random = new Random();
        ch = (char) (random.nextInt(26) + 'a');
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

    public void setName(String name) {
        this.name = name;
    }

    public int[] getIntArr() {
        return intArr;
    }

    public void setIntArr(int[] intArr) {
        this.intArr = intArr;
    }

    public boolean[] getIntBoolArr() {
        return intBoolArr;
    }

    public void setIntBoolArr(boolean[] intBoolArr) {
        this.intBoolArr = intBoolArr;
    }

    public Integer[] getIntObjArr() {
        return intObjArr;
    }

    public void setIntObjArr(Integer[] intObjArr) {
        this.intObjArr = intObjArr;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public Set<String> getArticles() {
        return articles;
    }

    public void setArticles(Set<String> articles) {
        this.articles = articles;
    }

    public SmallDummy getSmallDummy() {
        return smallDummy;
    }

    public void setSmallDummy(SmallDummy smallDummy) {
        this.smallDummy = smallDummy;
    }

    public List<SmallDummy> getSomeList() {
        return someList;
    }

    public void setSomeList(List<SmallDummy> someList) {
        this.someList = someList;
    }

    public Map<Integer, String> getSomeMap() {
        return someMap;
    }

    public void setSomeMap(Map<Integer, String> someMap) {
        this.someMap = someMap;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}


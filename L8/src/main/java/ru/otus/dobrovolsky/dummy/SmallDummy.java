package ru.otus.dobrovolsky.dummy;

import java.util.Random;

class SmallDummy {

    private String label;

    SmallDummy() {
        Random random = new Random();
        label = Double.toString(random.nextInt());
    }
}

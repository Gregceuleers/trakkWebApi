package com.trakknamur.demo.models.enums;

public enum TypeTrou {
    PAR_3(10,180),
    PAR_4(181,350),
    PAR_5(351,600);

    int min;
    int max;

    TypeTrou(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

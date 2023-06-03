package com.example.erp.models;

public class Pair {

    private final int key;
    private final String value;
    public Pair(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}

package com.zor07.domain;

public enum EntryType {
    INCOME,
    OUTCOME;

    private String name;

    EntryType() {
        this.name = name();
    }

    public String getName() {
        return name;
    }
}

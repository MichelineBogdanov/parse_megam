package ru.bogdanov.entity.megam_beans;

import java.util.ArrayList;

public class Root extends MegaMBean {
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}

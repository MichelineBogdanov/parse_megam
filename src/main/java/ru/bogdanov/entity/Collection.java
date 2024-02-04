package ru.bogdanov.entity;

import java.util.ArrayList;

public class Collection {
    public String id;
    public String name;
    public String slug;
    public String webUrl;
    public boolean isSelected;
    public ArrayList<ChildCollection> childCollections;
    public boolean isActive;
    public String relativeUrl;
    public String type;
}

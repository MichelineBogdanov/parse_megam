package ru.bogdanov.entity;

import java.util.ArrayList;

public class Root {
    public boolean success;
    public Meta meta;
    public ArrayList<Object> errors;
    public String total;
    public String offset;
    public String limit;
    public ArrayList<Item> items;
    public ArrayList<Object> tags;
    public ArrayList<Object> categories;
    public ArrayList<Object> merchantIds;
    public Object priceRange;
    public ArrayList<Object> filters;
    public int selectedFilterCount;
    public CollectionSelector collectionSelector;
    public Processor processor;
    public boolean hasPlus18;
    public Navigation navigation;
    public ArrayList<Object> flags;
    public ArrayList<Object> keywords;
    public String view;
    public ArrayList<Object> allowedServiceSchemes;
    public ArrayList<Sorting> sorting;
    public int listingSize;
    public Object assumedCollection;
    public ArrayList<Object> alternativeAssumedCollections;
    public Object queryChangesInfo;
    public String searchTextContext;
    public String goodsURL;
    public String version;
}

package ru.bogdanov.entity;

import java.util.ArrayList;

public class Goods {
    public String goodsId;
    public String title;
    public String titleImage;
    public ArrayList<Attribute> attributes;
    public String webUrl;
    public String slug;
    public ArrayList<Box> boxes;
    public String categoryId;
    public String brand;
    public ArrayList<Object> contentFlags;
    public ArrayList<String> contentFlagsStr;
    public int stocks;
    public int photosCount;
    public int offersCount;
    public ArrayList<String> logisticTags;
    public ArrayList<String> images;
    public ArrayList<Object> documents;
    public String description;
    public ArrayList<Object> videos;
    public String mainCollectionId;

    public Package mypackage;

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}

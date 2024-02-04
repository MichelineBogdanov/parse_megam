package ru.bogdanov.entity;

import java.util.ArrayList;
import java.util.Date;

public class Item {
    public Goods goods;
    public int price;
    public int priceFrom;
    public int priceTo;
    public int bonusPercent;
    public int bonusAmount;
    public double rating;
    public int reviewCount;
    public int offerCount;
    public int finalPrice;
    public FavoriteOffer favoriteOffer;
    public ArrayList<Object> relatedItems;
    public ArrayList<Object> productSelectors;
    public ArrayList<Object> extraOptions;
    public ArrayList<DeliveryPossibility> deliveryPossibilities;
    public ArrayList<Object> discounts;
    public ArrayList<String> contentFlagsStr;
    public ArrayList<Object> contentFlags;
    public ArrayList<Object> badges;
    public int crossedPrice;
    public String crossedPricePeriod;
    public int lastPrice;
    public boolean isAvailable;
    public int crossedPriceChangePercentage;
    public ArrayList<String> collections;
    public boolean hasOtherOffers;

    @Override
    public String toString() {
        return "Item{" +
                "goods=" + goods +
                ", price=" + price +
                ", priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                ", bonusPercent=" + bonusPercent +
                ", bonusAmount=" + bonusAmount +
                ", rating=" + rating +
                ", finalPrice=" + finalPrice +
                '}';
    }
}



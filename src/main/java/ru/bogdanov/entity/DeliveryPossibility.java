package ru.bogdanov.entity;

import java.util.ArrayList;

public class DeliveryPossibility {
    public String code;
    public String date;
    public int amount;
    public boolean isDefault;
    public Object maxDeliveryDays;
    public ArrayList<Object> appliedPromotionTags;
    public boolean isDbm;
    public String deliveryPriceType;
    public String displayName;
    public String displayDeliveryDate;
    public ArrayList<Object> deliveryOptions;
}

package ru.bogdanov.entity;

import java.util.ArrayList;

public class FavoriteOffer {
    public String id;
    public int price;
    public int score;
    public boolean isFavorite;
    public String merchantId;
    public ArrayList<DeliveryPossibility> deliveryPossibilities;
    public int finalPrice;
    public int bonusPercent;
    public int bonusAmount;
    public int availableQuantity;
    public int bonusAmountFinalPrice;
    public ArrayList<Object> discounts;
    public ArrayList<Object> priceAdjustments;
    public String deliveryDate;
    public String pickupDate;
    public String merchantOfferId;
    public String merchantName;
    public String merchantLogoUrl;
    public String merchantUrl;
    public int merchantSummaryRating;
    public boolean isBpgByMerchant;
    public int nds;
    public int oldPrice;
    public int oldPriceChangePercentage;
    public Object maxDeliveryDays;
    public String bpgType;
    public int creditPaymentAmount;
    public int installmentPaymentAmount;
    public Object showMerchant;
    public Object salesBlockInfo;
    public String dueDate;
    public String dueDateText;
    public String locationId;
    public boolean spasiboIsAvailable;
    public boolean isShowcase;
    public ArrayList<String> loyaltyPromotionFlags;
    public ArrayList<Object> pricesPerMeasurement;
    public ArrayList<Object> availablePaymentMethods;
    public int superPrice;
    public String warehouseId;
    public Object bnplPaymentParams;
    public Object installmentPaymentParams;
    public ArrayList<Object> bonusInfoGroups;
}

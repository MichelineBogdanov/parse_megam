package ru.bogdanov.entity.megam_beans;

public class Item extends MegaMBean {
    private Goods goods;
    private int price;
    private int priceFrom;
    private int priceTo;
    private int bonusPercent;
    private int bonusAmount;
    private double rating;
    private int finalPrice;
    private int crossedPrice;
    private String crossedPricePeriod;
    private int lastPrice;
    private boolean isAvailable;

    public Item(Goods goods
            , int price
            , int priceFrom
            , int priceTo
            , int bonusPercent
            , int bonusAmount
            , double rating
            , int finalPrice
            , int crossedPrice
            , String crossedPricePeriod
            , int lastPrice
            , boolean isAvailable) {
        this.goods = goods;
        this.price = price;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.bonusPercent = bonusPercent;
        this.bonusAmount = bonusAmount;
        this.rating = rating;
        this.finalPrice = finalPrice;
        this.crossedPrice = crossedPrice;
        this.crossedPricePeriod = crossedPricePeriod;
        this.lastPrice = lastPrice;
        this.isAvailable = isAvailable;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public int getBonusPercent() {
        return bonusPercent;
    }

    public void setBonusPercent(int bonusPercent) {
        this.bonusPercent = bonusPercent;
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(int bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getCrossedPrice() {
        return crossedPrice;
    }

    public void setCrossedPrice(int crossedPrice) {
        this.crossedPrice = crossedPrice;
    }

    public String getCrossedPricePeriod() {
        return crossedPricePeriod;
    }

    public void setCrossedPricePeriod(String crossedPricePeriod) {
        this.crossedPricePeriod = crossedPricePeriod;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(int lastPrice) {
        this.lastPrice = lastPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

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



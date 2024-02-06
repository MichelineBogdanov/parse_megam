package ru.bogdanov.entity.megam_beans;

public class Goods extends MegaMBean {
    private String goodsId;
    private String title;
    private String webUrl;
    private String brand;

    public Goods(String goodsId, String title, String webUrl, String brand) {
        this.goodsId = goodsId;
        this.title = title;
        this.webUrl = webUrl;
        this.brand = brand;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}

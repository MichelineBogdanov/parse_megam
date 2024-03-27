package ru.bogdanov.entity.megam_beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.config.SettingsDlg;

import java.net.URI;
import java.net.URISyntaxException;

public class Goods extends MegaMBean {

    private String goodsId;
    private String title;
    private URI webUrl;
    private String brand;

    private static final Logger LOG = LoggerFactory.getLogger(Goods.class);

    public Goods(String goodsId, String title, String webUrl, String brand) {
        this.goodsId = goodsId;
        this.title = title;
        try {
            this.webUrl = new URI(webUrl);
        } catch (URISyntaxException e) {
            LOG.error("Не удалось преобразовать строку в URL");
            throw new RuntimeException(e);
        }
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

    public URI getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        try {
            this.webUrl = new URI(webUrl);
        } catch (URISyntaxException e) {
            LOG.error("Не удалось преобразовать строку в URL");
            throw new RuntimeException(e);
        }
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

package ru.bogdanov.config;

public class Config {

    private boolean isFixApp;
    private boolean isOpenBrowser;
    private int sale = 0;
    private int pages = 0;
    private double rate = 0;

    public Config() {
    }

    public boolean isFixApp() {
        return isFixApp;
    }

    public void setFixApp(boolean fixApp) {
        isFixApp = fixApp;
    }

    public boolean isOpenBrowser() {
        return isOpenBrowser;
    }

    public void setOpenBrowser(boolean openBrowser) {
        isOpenBrowser = openBrowser;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Config{" +
                "isFixApp=" + isFixApp +
                ", isOpenBrowser=" + isOpenBrowser +
                ", sale=" + sale +
                ", rate=" + rate +
                '}';
    }
}

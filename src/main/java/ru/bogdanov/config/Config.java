package ru.bogdanov.config;

public class Config {

    private boolean isFixApp;
    private boolean isOpenBrowser;
    private int sale = 0;
    private double rate = 0;

    public Config() {
    }

    public Config(boolean isFixApp, boolean isOpenBrowser, int sale, double rate) {
        this.isFixApp = isFixApp;
        this.isOpenBrowser = isOpenBrowser;
        this.sale = sale;
        this.rate = rate;
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

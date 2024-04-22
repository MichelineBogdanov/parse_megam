package ru.bogdanov.app;

public interface UICallback {

    /**
     * Adds rows to the main table. This method can be called from outside EDT.
     *
     * @param json json to add in table
     */
    void putTableData(String json);

    /**
     * Sets number to the main counter, replacing existing context. This method can be called from outside EDT.
     */
    void increaseCounter();

    /**
     * Sets current progress. Values should be in the range [0,100]. This method can be called from outside EDT.
     *
     * @param progressPercent progress value to set
     */
    void setProgress(int progressPercent);

    /**
     * Performs required UI operations when parsing starts. This method can be called from outside EDT.
     */
    void startParsing();

    /**
     * Performs required UI operations when parsing stops. This method can be called from outside EDT.
     */
    void stopParsing();

    /**
     * Displays error message. This method can be called from outside EDT.
     *
     * @param message message to display
     */
    void showError(String message);

}

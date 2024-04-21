package ru.bogdanov.workers;

public interface Loader {

    /**
     * Performs parsing operation in new thread
     */
    void execute();

    /**
     * Cancels current operation
     */
    void stop();

}

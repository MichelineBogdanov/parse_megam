package ru.bogdanov.workers;

public interface Loader {

    /**
     * Performs parsing operation in new thread
     */
    void executeTask();

    /**
     * Cancels current operation
     */
    void stop();

    /**
     * Pause current task
     */
    void pause();

}

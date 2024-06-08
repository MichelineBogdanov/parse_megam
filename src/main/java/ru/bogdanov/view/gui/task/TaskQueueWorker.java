package ru.bogdanov.view.gui.task;

public interface TaskQueueWorker {

    void expand(int row);

    void start(int row);

    void stop(int row);

    void delete(int row);

}

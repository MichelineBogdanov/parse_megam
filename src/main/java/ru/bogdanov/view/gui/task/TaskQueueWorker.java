package ru.bogdanov.view.gui.task;

import java.awt.event.ActionEvent;

public interface TaskQueueWorker {

    void expand(ActionEvent e);

    void start(ActionEvent e);

    void stop(ActionEvent e);

    void delete(ActionEvent e);

}

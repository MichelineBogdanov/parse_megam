package ru.bogdanov.view.gui;

import org.w3c.dom.events.Event;

import java.awt.event.ActionEvent;

public interface TaskQueueWorker {

    void expand(ActionEvent e);

    void start(ActionEvent e);

    void stop(ActionEvent e);

    void delete(ActionEvent e);

}

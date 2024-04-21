package ru.bogdanov.view.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.events.Event;
import ru.bogdanov.workers.ParseLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

public class TaskTable extends JTable implements TaskQueueWorker {

    ArrayList<ParseLoader> taskList = new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(TaskTable.class);

    private ButtonColumn expandColumn;
    private ButtonColumn startColumn;
    private ButtonColumn stopColumn;
    private ButtonColumn deleteColumn;

    Vector<String> header = new Vector<>() {{
        add("Развернуть");
        add("URI");
        add("Запустить");
        add("Остановить");
        add("Удалить");
        add("Прогресс");
    }};

    public TaskTable() {
        Action expandAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                expand(e);
            }
        };
        Action startAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                start(e);
            }
        };
        Action stopAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                stop(e);
            }
        };
        Action deleteAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                delete(e);
            }
        };
        this.setModel(new DefaultTableModel(header, 0));
        expandColumn = new ButtonColumn(this, expandAction, 0);
        startColumn = new ButtonColumn(this, startAction, 2);
        stopColumn = new ButtonColumn(this, stopAction, 3);
        deleteColumn = new ButtonColumn(this, deleteAction, 4);
    }

    @Override
    public void expand(ActionEvent e) {

    }

    @Override
    public void start(ActionEvent e) {
        int modelRow = Integer.parseInt(e.getActionCommand());
        ParseLoader task = taskList.get(modelRow);
        task.execute();
    }

    @Override
    public void stop(ActionEvent e) {
        int modelRow = Integer.parseInt(e.getActionCommand());
        ParseLoader task = taskList.get(modelRow);
        task.stop();
    }

    @Override
    public void delete(ActionEvent e) {
        JTable table = (JTable) e.getSource();
        int modelRow = Integer.parseInt(e.getActionCommand());
        Object delete = table.getModel().getValueAt(modelRow, 2);
        Window window = SwingUtilities.windowForComponent(table);
        int result = JOptionPane.showConfirmDialog(
                window,
                "Are you sure you want to " + delete,
                "Delete Row Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            ((DefaultTableModel) table.getModel()).removeRow(modelRow);
        }
    }

    public void addTask(ParseLoader task) {
        ((DefaultTableModel) (this.getModel())).addRow(new Object[]{"развернуть", task.getUrl(), "старт", "стоп", "удалить", null});
        taskList.add(task);
    }

    public void startAll() {
        for (ParseLoader parseLoader : taskList) {
            parseLoader.execute();
            LOG.info("Парсинг начат!");
        }
    }

    public void stopAll() {
        for (ParseLoader parseLoader : taskList) {
            parseLoader.stop();
            LOG.info("Парсинг остановлен!");
        }
    }
}

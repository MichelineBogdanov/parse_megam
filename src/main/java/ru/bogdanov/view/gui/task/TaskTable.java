package ru.bogdanov.view.gui.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.view.gui.common.ButtonColumn;
import ru.bogdanov.workers.ParseLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

public class TaskTable extends JTable implements TaskQueueWorker {

    ArrayList<ParseLoader> taskList = new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(TaskTable.class);

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
        new ButtonColumn(this, expandAction, 0);
        new ButtonColumn(this, startAction, 2);
        new ButtonColumn(this, stopAction, 3);
        new ButtonColumn(this, deleteAction, 4);
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
        ((DefaultTableModel) table.getModel()).removeRow(modelRow);
        ParseLoader task = taskList.get(modelRow);
        task.stop();
        taskList.remove(modelRow);
    }

    public void addTask(ParseLoader task) {
        ((DefaultTableModel) (this.getModel())).addRow(new Object[]{"развернуть", task.getUrl(), "старт", "стоп", "удалить", null});
        taskList.add(task);
    }

    public void startAll() {
        for (ParseLoader parseLoader : taskList) {
            parseLoader.execute();
        }
        LOG.info("Парсинг начат!");
    }

    public void stopAll() {
        for (ParseLoader parseLoader : taskList) {
            parseLoader.stop();
        }
        LOG.info("Парсинг остановлен!");
    }
}

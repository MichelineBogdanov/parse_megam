package ru.bogdanov.view.gui.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.view.gui.common.ButtonColumn;
import ru.bogdanov.workers.swing_worker.SWorkerParseLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskTable extends JTable implements TaskQueueWorker {

    private final ArrayList<SWorkerParseLoader> taskList = new ArrayList<>();

    private ExecutorService executorService = null;

    private static final Logger LOG = LoggerFactory.getLogger(TaskTable.class);

    Vector<String> header = new Vector<>() {{
        add("Развернуть");
        add("URI");
        add("Запустить");
        add("Остановить");
        add("Удалить");
        add("Статус");
    }};

    public TaskTable() {
        Action expandAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                expand(row);
            }
        };
        Action startAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                start(row);
            }
        };
        Action stopAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                stop(row);
            }
        };
        Action deleteAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                delete(row);
            }
        };
        this.setModel(new DefaultTableModel(header, 0));
        ButtonColumn buttonColumn = new ButtonColumn(this, expandAction, 0);
        buttonColumn.getRenderButton().setEnabled(false);
        buttonColumn.getEditButton().setEnabled(false);
        new ButtonColumn(this, startAction, 2);
        new ButtonColumn(this, stopAction, 3);
        new ButtonColumn(this, deleteAction, 4);
    }

    @Override
    public void expand(int row) {
        // TODO : доделать разворот на полный экран таски
        //TaskWindow taskWindow = new TaskWindow();
    }

    @Override
    public void start(int row) {
        SWorkerParseLoader task = taskList.get(row);
        task.executeTask();
    }

    @Override
    public void stop(int row) {
        SWorkerParseLoader task = taskList.get(row);
        task.stop();
    }

    @Override
    public void delete(int row) {
        ((DefaultTableModel) this.getModel()).removeRow(row);
        SWorkerParseLoader task = taskList.remove(row);
        if (task.getState() == SwingWorker.StateValue.STARTED) {
            task.stop();
        }
    }

    public synchronized void pauseTask() {
        for (SWorkerParseLoader task : taskList) {
            if (task.getState() == SwingWorker.StateValue.STARTED) {
                task.pause();
            }
        }
    }

    public void addTask(SWorkerParseLoader task) {
        ((DefaultTableModel) getModel()).addRow(new Object[]{"развернуть", task.getUrl(), "старт", "стоп", "удалить", task.getState()});
        taskList.add(task);
        task.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                this.getModel().setValueAt(evt.getNewValue(), taskList.indexOf(task), 5);
            }
        });
    }

    public void startAll() {
        this.executorService = Executors.newSingleThreadExecutor();
        for (SWorkerParseLoader task : taskList) {
            if (task.getState() == SwingWorker.StateValue.PENDING) {
                executorService.execute(task);
            }
        }
        LOG.info("Парсинг начат!");
    }

    public void stopAll() {
        for (SWorkerParseLoader task : taskList) {
            if (task.getState() == SwingWorker.StateValue.PENDING || task.getState() == SwingWorker.StateValue.STARTED) {
                task.stop();
            }
        }
        this.executorService.shutdownNow();
        LOG.info("Парсинг остановлен! Выполнено задач: " + this.executorService.toString());
        executorService = Executors.newSingleThreadExecutor();
    }

    public void refreshTasks() {
        for (int i = 0; i < taskList.size(); i++) {
            SWorkerParseLoader task = taskList.get(0);
            SWorkerParseLoader newTask = new SWorkerParseLoader(task.getUi(), task.getConfig(), task.getUrl());
            this.delete(0);
            this.addTask(newTask);
        }
    }
}

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
        ButtonColumn buttonColumn = new ButtonColumn(this, expandAction, 0);
        buttonColumn.getRenderButton().setEnabled(false);
        buttonColumn.getEditButton().setEnabled(false);
        new ButtonColumn(this, startAction, 2);
        new ButtonColumn(this, stopAction, 3);
        new ButtonColumn(this, deleteAction, 4);
    }

    @Override
    public void expand(ActionEvent e) {
        // TODO : доделать разворот на полный экран таски
        //TaskWindow taskWindow = new TaskWindow();
    }

    @Override
    public void start(ActionEvent e) {
        int modelRow = Integer.parseInt(e.getActionCommand());
        SWorkerParseLoader task = taskList.get(modelRow);
        task.executeTask();
    }

    @Override
    public void stop(ActionEvent e) {
        int modelRow = Integer.parseInt(e.getActionCommand());
        SWorkerParseLoader task = taskList.get(modelRow);
        task.stop();
    }

    @Override
    public void delete(ActionEvent e) {
        JTable table = (JTable) e.getSource();
        int modelRow = Integer.parseInt(e.getActionCommand());
        SWorkerParseLoader task = taskList.remove(modelRow);
        ((DefaultTableModel) table.getModel()).removeRow(modelRow);
        if (!task.isDone()) {
            task.stop();
        }
        System.out.println("===");
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
                if (taskList.contains(task)) {
                    getModel().setValueAt(evt.getNewValue(), taskList.indexOf(task), 5);
                }
            }
        });
    }

    public void startAll() {
        this.executorService = Executors.newSingleThreadExecutor();
        for (SWorkerParseLoader task : taskList) {
            if (!task.isDone()) {
                executorService.execute(task);
            }
        }
        LOG.info("Парсинг начат!");
    }

    public void stopAll() {
        this.executorService.shutdownNow();
        LOG.info("Парсинг остановлен! Выполнено задач: " + this.executorService.toString());
        executorService = Executors.newSingleThreadExecutor();
    }
}

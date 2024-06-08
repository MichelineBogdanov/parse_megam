package ru.bogdanov.view.gui.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.view.gui.common.URICellRenderer;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Vector;

public class ItemTable extends JTable {

    private static final Logger LOG = LoggerFactory.getLogger(ItemTable.class);

    Vector<String> header = new Vector<>() {{
        add("Название");
        add("Цена");
        add("Бонусы (в % от цены)");
        add("Итоговая цена");
        add("Рейтинг");
        add("Ссылка на товар");
    }};

    public ItemTable() {
        URICellRenderer renderer = new URICellRenderer();
        this.setDefaultRenderer(URI.class, renderer);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable source = (JTable) e.getSource();
                int row = source.getSelectedRow();
                int col = source.getSelectedColumn();
                if (source.getValueAt(row, col) instanceof URI valueAt) {
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().browse(valueAt);
                        }
                    } catch (Exception ex) {
                        LOG.error("Не удалось открыть браузер!");
                        ex.printStackTrace();
                    }
                }
            }
        });
        ItemTableModel myTableModel = new ItemTableModel(header, 0);
        this.setModel(myTableModel);
        RowSorter<ItemTableModel> sorter = new TableRowSorter<>(myTableModel);
        this.setRowSorter(sorter);
    }

    public void removeData() {
        ((ItemTableModel) getModel()).removeRows();
    }

    public void addRow(Object[] rowData) {
        ((ItemTableModel) getModel()).addRow(rowData);
    }
}

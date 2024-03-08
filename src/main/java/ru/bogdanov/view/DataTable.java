package ru.bogdanov.view;

import ru.bogdanov.entity.megam_beans.Item;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Vector;

public class DataTable extends JTable {

    public DataTable() {
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
                        ex.printStackTrace();
                    }
                }

            }

        });
        Vector<String> header = new Vector<>() {{
            add("Название");
            add("Цена");
            add("Скидка");
            add("URI");
        }};
        DataTableModel myTableModel = new DataTableModel(header, 0);
        this.setModel(myTableModel);
        RowSorter<DataTableModel> sorter = new TableRowSorter<>(myTableModel);
        this.setRowSorter(sorter);
    }
}

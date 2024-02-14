package ru.bogdanov.view;

import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.util.Vector;

public class DataTableModel extends DefaultTableModel {

    public DataTableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if ("URI".equals(getColumnName(columnIndex))) {
            return URI.class;
        }
        if (columnIndex == 1 || columnIndex == 2) {
            return Integer.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }

    @Override
    public String getColumnName(int column) {
        return super.getColumnName(column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

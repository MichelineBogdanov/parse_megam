package ru.bogdanov.view;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class DataTableModel extends DefaultTableModel {

    public DataTableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
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

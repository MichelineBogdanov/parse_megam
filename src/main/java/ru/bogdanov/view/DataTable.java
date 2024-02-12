package ru.bogdanov.view;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.Vector;

public class DataTable extends JTable {

    public DataTable() {
    }

    public DataTable(TableModel dm) {
        super(dm);
    }

    public DataTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public DataTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public DataTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public DataTable(Vector<? extends Vector> rowData, Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    public DataTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }
}

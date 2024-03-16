package ru.bogdanov.view.gui;

import ru.bogdanov.entity.megam_beans.Item;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class BeanTableModel<T> extends DefaultTableModel {

    private Class<T> type;

    public BeanTableModel(Class<T> type) {
        this.type = type;
        Field[] fields = type.getDeclaredFields();
        List<String> list = Arrays.stream(fields).map(Field::getName).toList();
        this.columnIdentifiers = new Vector<>(list);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Field[] fields = type.getDeclaredFields();
        Field field = fields[columnIndex];
        return field.getDeclaringClass();
    }

    private static Vector<String> getHeader(Field[] fields) {
        Vector<String> res = new Vector<>();
        List<String> list = Arrays.stream(fields)
                .flatMap(f -> Arrays.stream(f.getType().getDeclaredFields()))
                .map(Field::getName)
                .toList();
        list.forEach(System.out::println);
        return res;
    }

    public void addBeanRow(Item item) {
        List<Object> list = Arrays.stream(item.getClass().getDeclaredFields())
                .map(f -> {
                    try {
                        return f.get(item);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        addRow(new Vector<>(list));
    }

    @Override
    public void addRow(Object[] rowData) {
        super.addRow(rowData);
    }

    @Override
    public int getRowCount() {
        return super.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return super.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return super.getValueAt(rowIndex, columnIndex);
    }
}

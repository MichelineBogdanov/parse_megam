package ru.bogdanov.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URI;

public class URLCellRenderer extends DefaultTableCellRenderer {

    public static void main(String[] args) {
        try {
            if (Desktop.isDesktopSupported()) { // JDK 1.6.0
                Desktop.getDesktop().browse(new URI("https://megamarket.ru/catalog/processory/"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setForeground(Color.BLUE);
        return component;
    }
}

package ru.bogdanov.view;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

public class URLCellRenderer extends DefaultTableCellRenderer implements MouseListener {

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
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        String text = this.getText();
        System.out.println(text);
        //try {
        //    if (Desktop.isDesktopSupported()) { // JDK 1.6.0
        //        Desktop.getDesktop().browse(url.toURI());
        //    }
        //} catch (Exception ex) {
        //    ex.printStackTrace();
        //}
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

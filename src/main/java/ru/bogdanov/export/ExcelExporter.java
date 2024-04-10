package ru.bogdanov.export;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
    private final Component parent;

    private static final Logger LOG = LoggerFactory.getLogger(ExcelExporter.class);

    public ExcelExporter(Component parent) {
        this.parent = parent;
    }

    public void saveFile(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(parent);
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            file = new File(file + ".xlsx");
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("result");
                for (int i = 0; i < table.getRowCount(); i++) {
                    XSSFRow dataRow = sheet.createRow(i);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        XSSFCell cell = dataRow.createCell(j);
                        cell.setCellValue(table.getValueAt(i, j).toString());
                    }
                }
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                }

            } catch (IOException e) {
                LOG.error("Экспорт в эксель завершен с ошибками!");
            }
        }
    }
}

package ru.bogdanov.view;

import com.google.gson.Gson;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Request;
import org.openqa.selenium.devtools.v120.network.model.Response;
import ru.bogdanov.entity.megam_beans.Item;
import ru.bogdanov.entity.megam_beans.Root;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

public class App extends JFrame {
    private JPanel parent;
    private JTextField urlTF;
    private JLabel urlLbl;
    private JToolBar toolBar;
    private JPanel mainPanel;
    private JTable resultTable;
    private JScrollPane tableScroll;
    private JButton startBtn;
    private JButton exportBtn;
    private JButton stopBtn;

    public static void main(String[] args) {
        App app = new App();
    }

    public App() {
        setContentPane(parent);
        setTitle("Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        startBtn.addActionListener(e -> onStart());
        stopBtn.addActionListener(e -> onStop());
        initTable();
        setVisible(true);
    }

    private void onStop() {
        System.out.println("Stop!");
    }

    private void initTable() {
        Vector<String> header = new Vector<>() {{
            add("Название");
            add("Цена");
            add("Скидка");
            add("URI");
        }};
        DataTableModel myTableModel = new DataTableModel(header, 1);

        URLCellRenderer renderer = new URLCellRenderer();
        resultTable.setDefaultRenderer(URI.class, renderer);


        resultTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable source = (JTable) e.getSource();
                int row = source.getSelectedRow();
                int col = source.getSelectedColumn();
                if (!source.getColumnName(col).equals("URI")) {
                    return;
                }
                URI valueAt = (URI) source.getValueAt(row, col);
                try {
                    if (Desktop.isDesktopSupported()) { // JDK 1.6.0
                        Desktop.getDesktop().browse(valueAt);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        });

        resultTable.setModel(myTableModel);
        myTableModel.insertRow(0, new String[]{"item.getGoods().getTitle()", "item.getPrice()", "item.getBonusPercent()", "item.getGoods().getWebUrl()"});
    }

    private void onStart() {
        String url = urlTF.getText();
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            Request request = requestWillBeSent.getRequest();
            System.out.println(request.getUrl());
        });
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().equals("https://megamarket.ru/api/mobile/v1/catalogService/catalog/search")) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                System.out.println(body);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                getData(body);
                //DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
                //model.insertRow(0, new Object[]{"WordPress", "Easy", "qw", "qw", "wq"});
                //resultTable.repaint();
            }

        });
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));
        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
    }

    public Vector getData(String json) {
        Gson gson = new Gson();
        Root root = gson.fromJson(json, Root.class);
        ArrayList<Item> items = root.getItems();
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        for (Item item : items) {
            if (item.getBonusAmount() > 0) {
                model.insertRow(0, new Object[]{item.getGoods().getTitle(), item.getPrice(), item.getBonusPercent(), item.getGoods().getWebUrl()});
                resultTable.repaint();
            }
        }
        System.out.println(items);
        return null;
    }

}

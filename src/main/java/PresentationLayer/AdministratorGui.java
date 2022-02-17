package PresentationLayer;

import BussinessLayer.*;
import BussinessLayer.MenuItem;
import DataLayer.Serializator;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdministratorGui {

    private JFrame administratorFrame;
    private JTable table;
    private JTextField priceField;
    private JTextField nameField;
    private JTextField ratingField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField fatField;
    private JTextField sodiumField;
    private JTextField startDateField;
    private JTextField endHourField;
    private JTextField ordersNumberButton;
    private JTextField valueButton;
    private JTextField nrOfOrdersButton;
    private JTextField dayField;

    /**
     * Launch the application.
     */
    /**
     * Create the application.
     */
    public AdministratorGui(DeliveryService deliveryService) {
        initialize(deliveryService);

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(DeliveryService deliveryService) {
        administratorFrame = new JFrame();
        administratorFrame.setBounds(100, 100, 989, 631);
        administratorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        administratorFrame.getContentPane().setLayout(null);
        ArrayList<BaseProduct> compProd = new ArrayList<BaseProduct>();
        for(Order o :deliveryService.getHashMap().keySet()){
            System.out.println("Numarul comenzii : "+o.getIdOrder()+ " data : "+o.getDate());
            for(MenuItem item : deliveryService.getHashMap().get(o)){
                System.out.println("Produs :"+item.getTitle());
            }

        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 553, 417);
        administratorFrame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Title","Rating","Calories","Protein","Fat","Sodium","Price"}));


        JButton importMenuButton = new JButton("Import Products");
        importMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryService.getItems();
                    Serializator.menuItemsSerialization(deliveryService.getMenuItems());
                    deliveryService.getMenuItems().removeAll(deliveryService.getMenuItems());
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        importMenuButton.setBounds(10, 514, 154, 49);
        administratorFrame.getContentPane().add(importMenuButton);

        JButton showProductsButton = new JButton("Show Products");
        showProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryService.setMenuItems(Serializator.menuItemsDeserialization());
                    for(MenuItem item : deliveryService.getMenuItems()){
                        System.out.println("TItle : "+item.getTitle());
                    }
                    Object[] row= new Object[7];
                    DefaultTableModel model=(DefaultTableModel) table.getModel();
                    int rowCount=model.getRowCount();
                    for(int i=rowCount-1;i>=1;i--){
                        model.removeRow(i);
                    }
                    try{
                        for(MenuItem item:deliveryService.getMenuItems()){
                            if(item instanceof BaseProduct){
                               row[0]= item.getTitle();
                               row[1]= ((BaseProduct) item).getRating();
                               row[2]= ((BaseProduct) item).getCalories();
                               row[3]= ((BaseProduct) item).getProtein();
                               row[4]= ((BaseProduct) item).getFat();
                               row[5]= ((BaseProduct) item).getSodium();
                               row[6]= ((BaseProduct) item).getPrice();
                               model.addRow(row);
                            }else if(item instanceof CompositeProduct){
                                row[0]= item.getTitle();
                                row[1]= ((CompositeProduct) item).getRating();
                                row[2]= ((CompositeProduct) item).getCalories();
                                row[3]= ((CompositeProduct) item).getProtein();
                                row[4]= ((CompositeProduct) item).getFat();
                                row[5]= ((CompositeProduct) item).getSodium();
                                row[6]= ((CompositeProduct) item).getPrice();
                                model.addRow(row);

                            }
                        }
                    }catch(Exception e1){
                        e1.printStackTrace();

                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
        showProductsButton.setBounds(174, 514, 154, 49);
        administratorFrame.getContentPane().add(showProductsButton);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object row1= new Object();
                row1= table.getModel().getValueAt(table.getSelectedRow(),0);
                deliveryService.deleteItem(row1.toString());
                Serializator.menuItemsSerialization(deliveryService.getMenuItems());

            }
        });
        deleteButton.setBounds(338, 514, 154, 49);
        administratorFrame.getContentPane().add(deleteButton);

        priceField = new JTextField();
        priceField.setText("Introduce Price");
        priceField.setBounds(573, 11, 184, 31);
        administratorFrame.getContentPane().add(priceField);
        priceField.setColumns(10);

        nameField = new JTextField();
        nameField.setText("Introduce Name");
        nameField.setColumns(10);
        nameField.setBounds(573, 57, 184, 31);
        administratorFrame.getContentPane().add(nameField);

        ratingField = new JTextField();
        ratingField.setText("Introduce Rating");
        ratingField.setColumns(10);
        ratingField.setBounds(573, 109, 184, 31);
        administratorFrame.getContentPane().add(ratingField);

        caloriesField = new JTextField();
        caloriesField.setText("Introduce Calories");
        caloriesField.setColumns(10);
        caloriesField.setBounds(573, 165, 184, 31);
        administratorFrame.getContentPane().add(caloriesField);

        proteinField = new JTextField();
        proteinField.setText("Introduce Protein");
        proteinField.setColumns(10);
        proteinField.setBounds(573, 219, 184, 31);
        administratorFrame.getContentPane().add(proteinField);

        fatField = new JTextField();
        fatField.setText("Introduce Fats");
        fatField.setColumns(10);
        fatField.setBounds(573, 272, 184, 31);
        administratorFrame.getContentPane().add(fatField);

        sodiumField = new JTextField();
        sodiumField.setText("Introduce Sodium");
        sodiumField.setColumns(10);
        sodiumField.setBounds(573, 333, 184, 31);
        administratorFrame.getContentPane().add(sodiumField);

        JButton addToCompositeProductButton = new JButton("Add To Composite Product");
        addToCompositeProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object row1= new Object();
                row1= table.getModel().getValueAt(table.getSelectedRow(),0);
                BaseProduct baseProduct= (BaseProduct) deliveryService.searchItem((String) row1);
                compProd.add(baseProduct);
            }
        });
        addToCompositeProductButton.setBounds(10, 440, 200, 49);
        administratorFrame.getContentPane().add(addToCompositeProductButton);

        JButton addCompositeProductButton = new JButton("Add Composite Product");
        addCompositeProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CompositeProduct comp= new CompositeProduct(compProd,nameField.getText(),Float.parseFloat(ratingField.getText()));
                for(BaseProduct product : comp.getCompProd()){
                    System.out.println(product.getTitle());
                }
                deliveryService.getMenuItems().add(comp);
                Serializator.menuItemsSerialization(deliveryService.getMenuItems());
                compProd.removeAll(compProd);
            }
        });
        addCompositeProductButton.setBounds(248, 440, 200, 49);
        administratorFrame.getContentPane().add(addCompositeProductButton);

        JButton addBaseProductButton = new JButton("Add Base Product");
        addBaseProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deliveryService.getMenuItems().add(new BaseProduct(nameField.getText(),Float.parseFloat(ratingField.getText()),Integer.parseInt(caloriesField.getText()),
                        Integer.parseInt(proteinField.getText()),Integer.parseInt(fatField.getText()),Integer.parseInt(sodiumField.getText()),Integer.parseInt(priceField.getText())));
                Serializator.menuItemsSerialization(deliveryService.getMenuItems());

            }
        });
        addBaseProductButton.setBounds(486, 440, 200, 49);
        administratorFrame.getContentPane().add(addBaseProductButton);

        JButton modifyButton = new JButton("Modify Product");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object row1= new Object();
                row1= table.getModel().getValueAt(table.getSelectedRow(),0);
                BaseProduct item = new BaseProduct(nameField.getText(),Float.parseFloat(ratingField.getText()),Integer.parseInt(caloriesField.getText()),
                        Integer.parseInt(proteinField.getText()),Integer.parseInt(fatField.getText()),Integer.parseInt(sodiumField.getText()),Integer.parseInt(priceField.getText()));
                deliveryService.modifyProduct(row1.toString(),item);
                Serializator.menuItemsSerialization(deliveryService.getMenuItems());

            }
        });
        modifyButton.setBounds(502, 514, 184, 49);
        administratorFrame.getContentPane().add(modifyButton);

        JButton generateDayReportButton = new JButton("Generate Day Report");
        generateDayReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int day=Integer.parseInt(dayField.getText());
                LocalDateTime date= LocalDateTime.of(2020,10,day,15,20);
                System.out.println("START");
                for(MenuItem item: deliveryService.getMenuItems()){
                    System.out.println(item.getTitle());
                }
                System.out.println(date);
                try {
                    deliveryService.generateDayReport(date);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        generateDayReportButton.setBounds(763, 452, 200, 49);
        administratorFrame.getContentPane().add(generateDayReportButton);

        JButton generateTimeReportButton = new JButton("Generate Time Report");
        generateTimeReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int startHour=Integer.parseInt(startDateField.getText());
                int endHour=Integer.parseInt(endHourField.getText());
                LocalTime startDate = LocalTime.of(startHour,0);
                LocalTime endDate=LocalTime.of(endHour,0);
                System.out.println(startDate);
                System.out.println(endDate);
                try {
                    deliveryService.generateTimeReport(startDate,endDate);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        generateTimeReportButton.setBounds(763, 15, 200, 49);
        administratorFrame.getContentPane().add(generateTimeReportButton);

        JButton generateClientsReportButton = new JButton("Generate Clients Report");
        generateClientsReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryService.generateClientsReport(Integer.parseInt(ordersNumberButton.getText()),Integer.parseInt(valueButton.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
        generateClientsReportButton.setBounds(763, 189, 200, 49);
        administratorFrame.getContentPane().add(generateClientsReportButton);

        JButton generateProductReportButton = new JButton("Generate Product Report");
        generateProductReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryService.generateProductsReport(Integer.parseInt(nrOfOrdersButton.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        generateProductReportButton.setBounds(763, 350, 200, 49);
        administratorFrame.getContentPane().add(generateProductReportButton);

        startDateField = new JTextField();
        startDateField.setText("Introduce Start Hour");
        startDateField.setColumns(10);
        startDateField.setBounds(767, 75, 184, 31);
        administratorFrame.getContentPane().add(startDateField);

        endHourField = new JTextField();
        endHourField.setText("Introduce End Hour");
        endHourField.setColumns(10);
        endHourField.setBounds(767, 129, 184, 31);
        administratorFrame.getContentPane().add(endHourField);

        ordersNumberButton = new JTextField();
        ordersNumberButton.setText("Introduce Number of Orders");
        ordersNumberButton.setColumns(10);
        ordersNumberButton.setBounds(767, 249, 184, 31);
        administratorFrame.getContentPane().add(ordersNumberButton);

        valueButton = new JTextField();
        valueButton.setText("Introduce Value");
        valueButton.setColumns(10);
        valueButton.setBounds(767, 308, 184, 31);
        administratorFrame.getContentPane().add(valueButton);

        nrOfOrdersButton = new JTextField();
        nrOfOrdersButton.setText("Introduce Number of Orders");
        nrOfOrdersButton.setColumns(10);
        nrOfOrdersButton.setBounds(763, 410, 184, 31);
        administratorFrame.getContentPane().add(nrOfOrdersButton);

        dayField = new JTextField();
        dayField.setText("Introduce Day ");
        dayField.setColumns(10);
        dayField.setBounds(763, 514, 184, 31);
        administratorFrame.getContentPane().add(dayField);
        administratorFrame.setVisible(true);
    }
}

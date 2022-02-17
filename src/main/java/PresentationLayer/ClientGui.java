package PresentationLayer;

import BussinessLayer.*;
import DataLayer.Serializator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientGui implements Observer {

    private JFrame clientFrame;
    private JTable table;
    private JTextField nameTextField;
    private JTextField ratingTextField;
    private JTextField caloriesTextField;
    private JTextField fastTextField;
    private JTextField proteinTextField;
    private JTextField priceTextField;

    /**
     * Launch the application.
     */
    public ClientGui(DeliveryService deliveryService ,Client client ) {
        initialize(deliveryService,client);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(DeliveryService deliveryService,Client client) {
        clientFrame = new JFrame();
        clientFrame.setBounds(100, 100, 907, 573);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.getContentPane().setLayout(null);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        ArrayList<Order> orders = new ArrayList<Order>();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 548, 411);
        clientFrame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Title","Rating","Calories","Protein","Fat","Sodium","Price"}));

        JButton viewAllProductsButton = new JButton("View All Products");
        viewAllProductsButton.addActionListener(new ActionListener() {
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
        viewAllProductsButton.setBounds(10, 491, 174, 32);
        clientFrame.getContentPane().add(viewAllProductsButton);

        JButton addToOrderButton = new JButton("Add To Order");
        addToOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object row1 = new Object();
                row1=table.getModel().getValueAt(table.getSelectedRow(),0);
                MenuItem menuItem= deliveryService.searchItem((String) row1);
                menuItems.add(menuItem);

            }
        });
        addToOrderButton.setBounds(194, 491, 174, 32);
        clientFrame.getContentPane().add(addToOrderButton);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(Order o : deliveryService.getHashMap().keySet()) {
                    System.out.println(o.getIdOrder());
                    for (MenuItem item : deliveryService.getHashMap().get(o)) {
                        System.out.println(item.getTitle());
                    }
                }
                System.out.println("Size-ul hashmap=ului :"+deliveryService.getHashMap().size());
                Order order= new Order(client.getId(),deliveryService.getHashMap().size(), LocalDateTime.now());
                deliveryService.getHashMap().put(order,menuItems);
                try {
                    deliveryService.generateBill(order.getIdOrder());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                showUp();
                Serializator.ordersSerialization(deliveryService.getHashMap());
                Serializator.menuItemsSerialization(deliveryService.getMenuItems());
                menuItems.removeAll(menuItems);

            }
        });
        placeOrderButton.setBounds(378, 491, 180, 32);
        clientFrame.getContentPane().add(placeOrderButton);

        JButton searchByName = new JButton("Search By Name");
        searchByName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByName(nameTextField.getText());
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }

        });
        searchByName.setBounds(619, 155, 202, 32);
        clientFrame.getContentPane().add(searchByName);

        JButton searchByRatingButton = new JButton("Search By rating");
        searchByRatingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByRating(Float.parseFloat(ratingTextField.getText()));
                for(MenuItem item : menuItemsList) {
                    System.out.println(item.getTitle()+" ");
                }
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }
        });
        searchByRatingButton.setBounds(619, 239, 202, 32);
        clientFrame.getContentPane().add(searchByRatingButton);

        JButton searchByCaloriesButton = new JButton("Search By Calories");
        searchByCaloriesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByCalories(Integer.parseInt(caloriesTextField.getText()));
                    Object[] row= new Object[7];
                    DefaultTableModel model=(DefaultTableModel) table.getModel();
                    int rowCount=model.getRowCount();
                    for(int i=rowCount-1;i>=0;i--) {
                        model.removeRow(i);
                    }
                try{
                    for(MenuItem item:menuItemsList){
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
            }
        });
        searchByCaloriesButton.setBounds(619, 323, 202, 32);
        clientFrame.getContentPane().add(searchByCaloriesButton);

        JButton searchByFatsButton = new JButton("Search By Fats");
        searchByFatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByFats(Integer.parseInt(fastTextField.getText()));
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }
        });

        searchByFatsButton.setBounds(619, 407, 202, 32);
        clientFrame.getContentPane().add(searchByFatsButton);

        JButton searchByProteinButton = new JButton("Search By Protein");
        searchByProteinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByProteins(Integer.parseInt(proteinTextField.getText()));
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }
        });
        searchByProteinButton.setBounds(619, 491, 202, 32);
        clientFrame.getContentPane().add(searchByProteinButton);

        nameTextField = new JTextField();
        nameTextField.setBounds(619, 114, 201, 30);
        clientFrame.getContentPane().add(nameTextField);
        nameTextField.setColumns(10);

        ratingTextField = new JTextField();
        ratingTextField.setColumns(10);
        ratingTextField.setBounds(620, 198, 201, 30);
        clientFrame.getContentPane().add(ratingTextField);

        caloriesTextField = new JTextField();
        caloriesTextField.setColumns(10);
        caloriesTextField.setBounds(619, 282, 201, 30);
        clientFrame.getContentPane().add(caloriesTextField);

        fastTextField = new JTextField();
        fastTextField.setColumns(10);
        fastTextField.setBounds(619, 366, 201, 30);
        clientFrame.getContentPane().add(fastTextField);

        proteinTextField = new JTextField();
        proteinTextField.setColumns(10);
        proteinTextField.setBounds(619, 450, 201, 30);
        clientFrame.getContentPane().add(proteinTextField);

        JButton searchByPriceButton = new JButton("Search By Price");
        searchByPriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemsList = deliveryService.searchProductByPrice(Integer.parseInt(priceTextField.getText()));
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }
        });
        searchByPriceButton.setBounds(619, 71, 202, 32);
        clientFrame.getContentPane().add(searchByPriceButton);

        priceTextField = new JTextField();
        priceTextField.setColumns(10);
        priceTextField.setBounds(619, 30, 201, 30);
        clientFrame.getContentPane().add(priceTextField);

        JButton searchByMultipleCriteria = new JButton("Search By Multiple Criteria");
        searchByMultipleCriteria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<MenuItem> menuItemsList= deliveryService.searchByMultipleCriteria(nameTextField.getText(),Integer.parseInt(priceTextField.getText()),
                        Float.parseFloat(ratingTextField.getText()),Integer.parseInt(proteinTextField.getText()),5,Integer.parseInt(fastTextField.getText()),Integer.parseInt(caloriesTextField.getText()));
                Object[] row= new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--) {
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:menuItemsList){
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
            }

        });
        searchByMultipleCriteria.setBounds(169, 433, 223, 47);
        clientFrame.getContentPane().add(searchByMultipleCriteria);
        clientFrame.setVisible(true);
    }

    @Override
    public void showUp() {
        new EmployeeGui();
    }
}

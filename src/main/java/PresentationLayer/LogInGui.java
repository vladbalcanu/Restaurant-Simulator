package PresentationLayer;

import BussinessLayer.Client;
import BussinessLayer.DeliveryService;
import BussinessLayer.MenuItem;
import BussinessLayer.Order;
import DataLayer.Serializator;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class LogInGui {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JTextField textField_1;
    private JTextField textField_3;
    private JPasswordField passwordField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        ArrayList<Client> clients = new ArrayList<Client>();
        HashMap<Order, ArrayList<MenuItem>> hashMap = new HashMap<Order, ArrayList<MenuItem>>();
        DeliveryService deliveryService = new DeliveryService(menuItems, clients, hashMap);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LogInGui window = new LogInGui(deliveryService);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LogInGui(DeliveryService deliveryService) throws IOException {
        initialize(deliveryService);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(DeliveryService deliveryService) throws IOException {

        frame = new JFrame();
        frame.setBounds(100, 100, 615, 466);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        deliveryService.setClients(Serializator.clientsDeserialization());
        deliveryService.setMenuItems(Serializator.menuItemsDeserialization());
        deliveryService.setHashMap(Serializator.orderDeserialization());
        for(Client cl :deliveryService.getClients()){
            System.out.println(cl.getName()+ "   " +cl.getId());
        }

        for(Order o : deliveryService.getHashMap().keySet()){
            System.out.println("Order : "+o.getIdOrder());
            for(MenuItem item : deliveryService.getHashMap().get(o)){
                System.out.println("Item +"+item.getTitle());
            }
        }

        Serializator.menuItemsSerialization(deliveryService.getMenuItems());
        Serializator.clientsSerialization(deliveryService.getClients());


        textField = new JTextField();
        textField.setBounds(92, 100, 123, 26);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("UserName");
        lblNewLabel.setBounds(10, 100, 71, 26);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 151, 71, 26);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(92, 151, 123, 26);
        frame.getContentPane().add(passwordField);

        JLabel lblNewLabel_1 = new JLabel("Sign In");
        lblNewLabel_1.setBounds(135, 52, 47, 37);
        frame.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Log In");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName=textField.getText();
                String password=passwordField.getText();
                boolean q=false;
                if(userName.equals("Administrator") && password.equals("administrator")){
                    AdministratorGui admin = new AdministratorGui(deliveryService);
                   frame.dispose();
                   q=true;
                }else{
                    for(Client cl : deliveryService.getClients()){
                        if(userName.equals(cl.getName())&& password.equals(cl.getPassword())){
                            ClientGui client = new ClientGui(deliveryService,cl);
                            frame.dispose();
                            q=true;
                        }
                        }

                }
                if(q==false){
                    try {
                        throw new Exception();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }
        });
        btnNewButton.setBounds(92, 188, 123, 26);
        frame.getContentPane().add(btnNewButton);

        JLabel lblNewLabel_2 = new JLabel("Sign Up");
        lblNewLabel_2.setBounds(370, 63, 53, 14);
        frame.getContentPane().add(lblNewLabel_2);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(338, 100, 123, 26);
        frame.getContentPane().add(textField_1);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(338, 207, 123, 26);
        frame.getContentPane().add(textField_3);

        JLabel lblNewLabel_3 = new JLabel("UserName");
        lblNewLabel_3.setBounds(244, 100, 71, 26);
        frame.getContentPane().add(lblNewLabel_3);

        JLabel lblPassword_1 = new JLabel("Password");
        lblPassword_1.setBounds(244, 151, 71, 26);
        frame.getContentPane().add(lblPassword_1);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(338, 154, 123, 26);
        frame.getContentPane().add(passwordField_1);

        JLabel lblNewLabel_3_1 = new JLabel("Age");
        lblNewLabel_3_1.setBounds(257, 207, 71, 26);
        frame.getContentPane().add(lblNewLabel_3_1);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name= textField_1.getText();
                int age = Integer.parseInt(textField_3.getText());
                String password= passwordField_1.getText();
                int lungime=deliveryService.getClients().size();
                Client cl = new Client(name,age,lungime,password);
                deliveryService.getClients().add(cl);
                for(Client client: deliveryService.getClients()){
                    System.out.println("CLientul :"+client.getId()+ " "+client.getName());
                }
                Serializator.clientsSerialization(deliveryService.getClients());
            }
        });
        btnRegister.setBounds(338, 257, 123, 26);
        frame.getContentPane().add(btnRegister);
    }
}

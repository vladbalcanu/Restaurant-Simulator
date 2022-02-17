package DataLayer;

import BussinessLayer.*;
import BussinessLayer.MenuItem;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serializator implements Serializable {
    public static void menuItemsSerialization(ArrayList<MenuItem> menuItems) {
        try {
            FileOutputStream out = new FileOutputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisation.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            for (MenuItem item : menuItems) {
                objOut.writeObject(item);
            }
            objOut.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MenuItem> menuItemsDeserialization() throws IOException {
        FileInputStream in = new FileInputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisation.ser");
        ObjectInputStream objIn = new ObjectInputStream(in);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        while (true) {
            MenuItem menuItem = null;
            try {
                menuItem = (MenuItem) objIn.readObject();
                menuItems.add(menuItem);
            } catch (Exception e) {
                break;
            }
        }
        return menuItems;
    }

    public static void clientsSerialization(ArrayList<Client> clients) {
        try {
            FileOutputStream out = new FileOutputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationClients.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            for (Client client : clients) {
                objOut.writeObject(client);
            }
            objOut.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Client> clientsDeserialization() throws IOException {
        ArrayList<Client> clients = new ArrayList<Client>();
        FileInputStream in = new FileInputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationClients.ser");
        ObjectInputStream objIn = new ObjectInputStream(in);
        while (true) {
            Client client = null;
            try {
                client = (Client) objIn.readObject();
                clients.add(client);
            } catch (Exception e) {
                break;

            }
        }
        return clients;
    }

    public static void ordersSerialization(HashMap<Order, ArrayList<MenuItem>> hashMap) {
        try {
            FileOutputStream out = new FileOutputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationOrders.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            FileOutputStream out2 = new FileOutputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationOrdersProducts.ser");
            ObjectOutputStream objOut2 = new ObjectOutputStream(out2);
            for (Order o : hashMap.keySet()) {
                objOut.writeObject(o);
                for (MenuItem item : hashMap.get(o)) {
                    objOut2.writeObject(item);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Order, ArrayList<MenuItem>> orderDeserialization() throws IOException {
        HashMap<Order, ArrayList<MenuItem>> hashMap = new HashMap<Order, ArrayList<MenuItem>>();
        FileInputStream in = new FileInputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationOrders.ser");
        ObjectInputStream objIn = new ObjectInputStream(in);
        FileInputStream in2 = new FileInputStream("C:\\PT2021_30221_Balcanu_Vlad_Assignment_4\\serialisationOrdersProducts.ser");
        ObjectInputStream objIn2 = new ObjectInputStream(in2);

        while (true) {
            try {
                Order order = null;

                order = (Order) objIn.readObject();

                ArrayList<MenuItem> items = new ArrayList<MenuItem>();
                for (int i = 0; i < order.getNumberOfProducts(); i++) {
                    MenuItem item = null;
                    item = (MenuItem) objIn2.readObject();
                    items.add(item);
                }
                hashMap.put(order, items);

            }catch(Exception e){
                    break;
                }

        }
        return hashMap;
    }

}



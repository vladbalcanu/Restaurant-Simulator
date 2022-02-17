package BussinessLayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDeliveryServiceProcessing {
    public ArrayList<MenuItem> getMenuItems();
    public void setMenuItems(ArrayList<MenuItem> menuItems);
    public ArrayList<Client> getClients();
    public void setClients(ArrayList<Client> clients);
    public HashMap<Order, ArrayList<MenuItem>> getHashMap();
    public void setHashMap(HashMap<Order, ArrayList<MenuItem>> hashMap);
    public void generateTimeReport(LocalTime startDate, LocalTime endDate) throws IOException;
    public void generateClientsReport(int nrComenzi , int suma) throws IOException;
    public void generateProductsReport(int nrComenzi) throws IOException;
    public void generateDayReport(LocalDateTime date) throws IOException;
    public List<MenuItem> searchProductByName(String name);
    public List<MenuItem> searchProductByRating(float rating);
    public List<MenuItem> searchProductByCalories(int calories);
    public List<MenuItem> searchProductByFats(int fats);
    public List<MenuItem> searchProductByProteins(int proteins);
    public List<MenuItem> searchProductBySodium(int sodium);
    public List<MenuItem> searchProductByPrice(int price);
    public void getItems() throws FileNotFoundException;
    public ArrayList<MenuItem> searchByMultipleCriteria(String name , int price , float rating , int protein ,int sodium , int fat, int calories);
    public void deleteItem(String name);
    public MenuItem searchItem(String name);
    public void addOrder(Order order, ArrayList<MenuItem> menuItems) throws IOException;
    public Order searchOrder(int id);
    public void modifyProduct(String name,BaseProduct item);
    public void generateBill(int id) throws IOException;



















}

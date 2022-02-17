package BussinessLayer;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order implements Serializable {
 private int idClient;
 private int idOrder;
 private int totalPrice;
 private int numberOfProducts;

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }
 private LocalDateTime date;
    public int generateTotalPrice(ArrayList<MenuItem> menuItems){
        int totalPrice = 0;
        for(MenuItem e : menuItems){
            if (e instanceof BaseProduct) {
                totalPrice += ((BaseProduct) e).getPrice();
            }
            if (e instanceof CompositeProduct) {
                totalPrice += ((CompositeProduct) e).getPrice();
            }
        }
            return totalPrice;
    }
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Order(int idClient, int idOrder, LocalDateTime date) {
        this.idClient = idClient;
        this.idOrder = idOrder;
        this.date = date;
    }
}

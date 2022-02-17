package BussinessLayer;

import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private int age;
    private int id;
    private String password;
    private int orders;

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client(String name, int age, int id, String password) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.password = password;
    }
}

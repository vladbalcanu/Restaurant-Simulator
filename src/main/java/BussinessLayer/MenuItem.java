package BussinessLayer;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String title ;
    private int nrOfOrders;

    public int getNrOfOrders() {
        return nrOfOrders;
    }

    public void setNrOfOrders(int nrOfOrders) {
        this.nrOfOrders = nrOfOrders;
    }

    public MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

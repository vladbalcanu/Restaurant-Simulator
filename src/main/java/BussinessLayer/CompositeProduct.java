package BussinessLayer;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct extends MenuItem implements Serializable {
    ArrayList<BaseProduct> compProd = new ArrayList<BaseProduct>();
    private float rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private float price;

    public void addBaseProduct(BaseProduct prod){
        this.compProd.add(prod);
        this.price+=prod.getPrice();
        this.calories+=prod.getCalories();
        this.fat+=prod.getFat();
        this.protein+=prod.getProtein();
    }
    public void deleteBaseProduct(BaseProduct prod){
        this.compProd.remove(prod);
        this.price-=prod.getPrice();
        this.calories-=prod.getCalories();
        this.fat-=prod.getFat();
        this.protein-=prod.getProtein();
    }
    public ArrayList<BaseProduct> getCompProd() {
        return this.compProd;
    }

    public void setCompProd(ArrayList<BaseProduct> compProd) {
        this.compProd = compProd;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CompositeProduct(ArrayList<BaseProduct> compProd, String title, float rating, int calories, int protein, int fat, int sodium, int price) {
        super(title);
        this.compProd = compProd;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }
    public CompositeProduct(ArrayList<BaseProduct> compProd,String title,float rating){
        super(title);
        this.compProd=compProd;
        for(BaseProduct e :compProd){
            this.calories+=e.getCalories();
            this.price+=e.getPrice();
            this.fat+=e.getFat();
            this.protein+=e.getProtein();
            this.rating=rating;
            this.sodium+=e.getSodium();
        }
    }
}

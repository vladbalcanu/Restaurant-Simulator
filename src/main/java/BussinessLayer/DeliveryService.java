package BussinessLayer;

import DataLayer.Serializator;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    ArrayList<Client> clients = new ArrayList<Client>();
    HashMap<Order, ArrayList<MenuItem>> hashMap = new HashMap<Order, ArrayList<MenuItem>>();

    public DeliveryService(ArrayList<MenuItem> menuItems, ArrayList<Client> clients, HashMap<Order, ArrayList<MenuItem>> hashMap) {
        this.menuItems = menuItems;
        this.clients = clients;
        this.hashMap = hashMap;
    }
    /**
     * <p>
     *   Metoda de invariant ;
     * </p>
     */

    public boolean invariant(){
        if(this.menuItems.size()>0)
            return true;
        return false;
    }
    /**
     * <p>
     *    Getter pentru arraylistul de produse din meniu
     * </p>
     */
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
    /**
     * <p>
     *      Setter arraylistul cu produse din meniu
     * </p>
     */
    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    /**
     * <p>
     *      Getter pentru clienti;
     * </p>
     */
    public ArrayList<Client> getClients() {
        return clients;
    }
    /**
     * <p>
     *     Setter pentru clienti;
     * </p>
     */

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
    /**
     * <p>
     *     Getter pentru hashMap;
     * </p>
     */

    public HashMap<Order, ArrayList<MenuItem>> getHashMap() {
        return hashMap;
    }
    /**
     * <p>
     *     Setter pentru hashMap;
     * </p>
     */
    public void setHashMap(HashMap<Order, ArrayList<MenuItem>> hashMap) {
        this.hashMap = hashMap;
    }

    public static int billNumber = 0;

    /**
     * <p>
     *     Metoda ce ne genereaza un raport continand toate produsele cumparate intre orele trimise ca si parametrii
     * </p>
     * @param endDate
     * @param startDate a
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     */
    public void generateTimeReport(LocalTime startDate, LocalTime endDate) throws IOException {
        assert this.menuItems.size()>0;
        FileWriter scriitor = new FileWriter("raportTime.txt", true);
        scriitor.write("Raport Comenzi cuprinse intre data : " + startDate + " si data : " + endDate + "\n");
        List<Order> dateOrders = hashMap.keySet().stream().filter(e -> e.getDate().getHour() >= startDate.getHour() && e.getDate().getMinute() >= startDate.getMinute() &&
                e.getDate().getHour() <= endDate.getHour() && e.getDate().getMinute() <= endDate.getMinute())
                .collect(Collectors.toList());
        dateOrders.stream().forEach(e -> {
            try {
                scriitor.write("OrderID " + e.getIdOrder() + "  ClientID " + e.getIdClient() + " Date " + e.getDate() + "\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        dateOrders.stream().forEach(e -> System.out.println("OrderID " + e.getIdOrder() + "  ClientID " + e.getIdClient() + " Date " + e.getDate() + "\n"));
        scriitor.close();
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda ce ne genereaza un raport cu toti clientii care au cumparat de un numar mai mare de ori trimis ca si parametru si una dintre comenzi
     *     are valoarea mai mare decat decat o valoarea transmisa ca si parametru
     * </p>
     * @param nrComenzi
     * @param suma
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void generateClientsReport(int nrComenzi , int suma) throws IOException {
        assert this.menuItems.size()>0;
        FileWriter scriitor = new FileWriter("raportClients.txt", true);
        scriitor.write("Raport Clienti cu mai mult de : " + nrComenzi + " comenzi si o o suma mai mare decat : " + suma + "\n");
        ArrayList<Client> c = new ArrayList<Client>();
        ArrayList<Client> c2 = new ArrayList<Client>();
        List<Client> clientList = clients.stream().filter(e -> e.getOrders()>=nrComenzi)
                    .collect(Collectors.toList());
        hashMap.keySet().stream().forEach(e-> clientList.stream().forEach(e2 -> {
            if (e.getIdClient() == e2.getId()) {
                if (e.getTotalPrice() >= suma ) {
                    c.add(e2);
                }
            }
        })
        );
        c.stream().distinct().forEach(e ->
        {
            try {
                scriitor.write("Clientul : " + e.getId() + " are mai mult de " + nrComenzi + " comenzi si una din comenzi are valoare mai mare de : " + suma + "\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        scriitor.close();
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda ce ne genereaza un raport cu toate produsele ce au fost comandate de mai multe ori decat o valoare tramsmisa ca si parametru
     * </p>
     * @param nrComenzi
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void generateProductsReport(int nrComenzi) throws IOException {
        assert this.menuItems.size()>0;
        FileWriter scriitor = new FileWriter("raportProducts.txt", true);
        scriitor.write("Raport produse ce au fost cumparate de mai mult de :" + nrComenzi + " ori pana acum :\n");
        List<MenuItem> menuItemList = menuItems.stream().filter(e -> e.getNrOfOrders()>=nrComenzi)
                .collect(Collectors.toList());
        menuItemList.stream().forEach(e -> {
            try {
                scriitor.write("Produsul :"+e.getTitle()+" a fost cumparat de mai mult de :"+nrComenzi+" de ori\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        scriitor.close();
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *       Metoda ce ne genereaza un raport cu toate produsele ce au fost comandate intr-o anumita zi si de cate ori au fost comandate
     *  </p>
     * @param date
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void generateDayReport(LocalDateTime date) throws IOException {
        assert this.menuItems.size()>0;
        FileWriter scriitor = new FileWriter("raportDay.txt",true);
        scriitor.write("Raport produse ce au fost cumparate in data de : " + date.getDayOfMonth() + " si de cate ori a fost cumparat\n");
        menuItems.stream().forEach(items -> { hashMap.keySet().stream()
                .forEach(orders-> {
                    hashMap.get(orders).stream()
                            .forEach(e -> {
                                if (orders.getDate().getDayOfMonth() == date.getDayOfMonth() && e.getTitle().equals(items.getTitle())) {
                                    try {
                                        System.out.println(items.getNrOfOrders());
                                        scriitor.write("Produsul : " + e.getTitle() + " a fost cumparat in ziua  : " + date.getDayOfMonth() + " si a fost cumparat de " + items.getNrOfOrders() + " ori\n");
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            });
                ;});});
        scriitor.close();
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda de cautare a unui produs dupa nume (metoda folosita de client)
     * </p>
     * @param name
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByName(String name) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e ->e.getTitle().matches("(.*)"+name+"(.*)"))
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();

        return items;

    }
    /**
     * <p>
     *     Metoda de cautare a unui produs dupa rating (metoda folosita de client)
     * </p>
     * @param rating
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByRating(float rating) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getRating() == rating || e instanceof CompositeProduct && ((CompositeProduct) e).getRating() == rating)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *     Metoda de cautare a unui produs dupa calorii (metoda folosita de client)
     * </p>
     * @param calories a
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByCalories(int calories) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getCalories() == calories || e instanceof CompositeProduct && ((CompositeProduct) e).getCalories() == calories)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *     Metoda de cautare a unui produs dupa grasimi (metoda folosita de client)
     * </p>
     * @param fats
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByFats(int fats) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getFat() == fats || e instanceof CompositeProduct && ((CompositeProduct) e).getFat() == fats)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *     Metoda de cautare a unui produs dupa proteine (metoda folosita de client)
     * </p>
     * @param proteins
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByProteins(int proteins) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getProtein() == proteins || e instanceof CompositeProduct && ((CompositeProduct) e).getProtein() == proteins)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *      Metoda de cautare a unui produs dupa sodiu (metoda folosita de client)
     * </p>
     * @param sodium
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductBySodium(int sodium) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getSodium() == sodium || e instanceof CompositeProduct && ((CompositeProduct) e).getSodium() == sodium)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *      Metoda de cautare a unui produs dupa pret (metoda folosita de client)
     * </p>
     * @param price
     *
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public List<MenuItem> searchProductByPrice(int price) {
        assert this.menuItems.size()>0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getPrice() == price || e instanceof CompositeProduct && ((CompositeProduct) e).getPrice() == price)
                .collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *     Metoda prin care importam produsele din products.csv si le introducem in program
     * </p>
     * @param
     *
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return
     */
    public void getItems() throws FileNotFoundException {
        assert this.menuItems.size()>0;
        ArrayList<MenuItem> items= new ArrayList<MenuItem>();
        BufferedReader buffer=new BufferedReader(new FileReader("products.csv"));
        buffer.lines().skip(1).distinct().map( e -> Arrays.asList(e.split(","))).forEach(
                e2->items.add(new BaseProduct(e2.get(0),Float.parseFloat(e2.get(1)),Integer.parseInt(e2.get(2)),
                        Integer.parseInt(e2.get(3)),Integer.parseInt(e2.get(4)),Integer.parseInt(e2.get(5)),Integer.parseInt(e2.get(6))
        )));
        try {
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuItems=(ArrayList<MenuItem>) items.stream().distinct().collect(Collectors.toList());
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *      Metoda de cautare a unui produs dupa mai multe criterii (metoda folosita de client)
     * </p>
     * @param name
     * @param calories
     * @param fat
     * @param price
     * @param protein
     * @param rating
     * @param sodium
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return List<MenuItem> o lista de menuItems care este afisata ulteorior
     */
    public ArrayList<MenuItem> searchByMultipleCriteria(String name , int price , float rating , int protein ,int sodium , int fat, int calories){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        for(MenuItem e : menuItems){
            boolean q=true;
            if(e instanceof BaseProduct){
                if(name != null && !(e.getTitle().matches("(.*)"+name+"(.*)")))
                    q=false;
                if(price != 0 && price != ((BaseProduct) e).getPrice())
                    q=false;
                if(rating != 0 && rating != ((BaseProduct) e).getRating())
                    q=false;
                if(protein != 0 && protein != ((BaseProduct) e).getProtein())
                    q=false;
                if(fat != 0 && fat !=((BaseProduct) e).getFat())
                    q=false;
                if(calories!=0 && calories!=((BaseProduct) e).getCalories())
                    q=false;
            }
            if(e instanceof CompositeProduct){
                if(name != null && !(e.getTitle().matches("(.*)"+name+"(.*)")))
                    q=false;
                if(price != 0 && price != ((CompositeProduct) e).getPrice())
                    q=false;
                if(rating != 0 && rating != ((CompositeProduct) e).getRating())
                    q=false;
                if(protein != 0 && protein != ((CompositeProduct) e).getProtein())
                    q=false;
                if(fat != 0 && fat !=((CompositeProduct) e).getFat())
                    q=false;
                if(calories!=0 && calories!=((CompositeProduct) e).getCalories())
                    q=false;
            }
            if(q==true){
                items.add(e);
            }
        }
        assert this.menuItems.size()>0;
        assert invariant();
        return items;
    }
    /**
     * <p>
     *     Metoda de stergere a unui produs (dupa nume)
     * </p>
     * @param name
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void deleteItem(String name){
        assert this.menuItems.size()>0;
        boolean q=false;
        MenuItem item= null;
        for(int i =0 ;i < menuItems.size();i++){
            if(menuItems.get(i).getTitle().equals(name)){
                item=menuItems.get(i);
                menuItems.remove(i);
                q=true;
                break;
            }
        }
        System.out.println("Item Name : "+item.getTitle());
        if(q==true && item instanceof BaseProduct){

            for(int i =0 ;i <menuItems.size();i++){
                if(menuItems.get(i) instanceof CompositeProduct){
                    System.out.println(menuItems.get(i).getTitle());
                   for(int j =0 ;j < ((CompositeProduct) menuItems.get(i)).getCompProd().size();j++){
                       System.out.println(((CompositeProduct) menuItems.get(i)).getCompProd().get(j).getTitle());
                       System.out.println(item.getTitle());
                       if(((CompositeProduct) menuItems.get(i)).getCompProd().get(j).getTitle().equals(item.getTitle())){
                           menuItems.remove(i);
                           i--;
                       }

                   }
                }
            }
        }
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda pe care o folosim pentru a gasi produsele pentru functionalitati
     * </p>
     * @param name
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return MenuItem ne returneaza un element de tip MenuItem
     */
    public MenuItem searchItem(String name) {
        assert this.menuItems.size()>0;
        MenuItem menuItem = null;
        for (MenuItem e : menuItems) {
            if (e.getTitle().equals(name)) {
                menuItem = e;
                e.setNrOfOrders(e.getNrOfOrders()+1);
            }
        }
        assert invariant();
        return menuItem;
    }
    /**
     * <p>
     *     Metoda pe care o folosim pentru a adauga o comanda noua
     * </p>
     * @param order
     * @param menuItem
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void addOrder(Order order, ArrayList<MenuItem> menuItem) throws IOException {
        order.setTotalPrice(order.generateTotalPrice(menuItem));
        order.setNumberOfProducts(menuItem.size());
        clients.get(order.getIdClient()).setOrders( clients.get(order.getIdClient()).getOrders()+1);
        System.out.println("Clientul : "+ order.getIdClient()+ " are atatea comenzi : "+clients.get(order.getIdClient()).getOrders());
        System.out.println();
        this.hashMap.put(order, menuItem);
        Serializator.ordersSerialization(hashMap);
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda pe care o folosim pentru a gasi comenzi
     * </p>
     * @param id
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @return un obiect de tip Order
     */
    public Order searchOrder(int id) {
        assert this.menuItems.size()>0;
        Order order = null;
        for (Order o : hashMap.keySet()) {
            if(o.getIdOrder()==id) {
                order = o;
                break;
            }
        }
        assert this.menuItems.size()>0;
        assert invariant();
        return order;

    }
    /**
     * <p>
     *     Metoda de modificare a unui produs de baza
     * </p>
     * @param item
     * @param name
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void modifyProduct(String name,BaseProduct item){
        assert this.menuItems.size()>0;
        boolean q=false;
        MenuItem itemIntern= null;
        for(int i =0 ;i < menuItems.size();i++){
            if(menuItems.get(i).getTitle().equals(name) ) {
                if (menuItems.get(i) instanceof BaseProduct) {
                    itemIntern = menuItems.get(i);
                    menuItems.remove(i);
                    menuItems.add(item);
                    q = true;
                } else {
                    System.out.println("U can't modify a Composite product");
                }
                break;
            }
        }
        if(q==true ){
            for(int i =0 ;i <menuItems.size();i++){
                if(menuItems.get(i) instanceof CompositeProduct){
                    for(int j =0 ;j < ((CompositeProduct) menuItems.get(i)).getCompProd().size();j++){
                        if(((CompositeProduct) menuItems.get(i)).getCompProd().get(j).getTitle().equals(itemIntern.getTitle())){
                            ((CompositeProduct) menuItems.get(i)).deleteBaseProduct((BaseProduct) itemIntern);
                            ((CompositeProduct) menuItems.get(i)).addBaseProduct(item);
                        }

                    }
                }
            }
        }
        assert this.menuItems.size()>0;
        assert invariant();
    }
    /**
     * <p>
     *     Metoda de generare a unei chitante de fiecare data cand un client face o comanda
     * </p>
     * @param id
     *
     * @pre size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @post size-ul arrayList-ului de produse sa fie mai mare decat 0
     * @invariant size-ul arrayList-ului de produse sa fie mai mare decat 0
     *
     */
    public void generateBill(int id) throws IOException {
        assert this.menuItems.size()>0;
        for (Order o : hashMap.keySet()) {
            if (o.getIdOrder() == id) {
                String bill = "bill" + billNumber;
                float totalPrice = 0;


                FileWriter writer = new FileWriter(bill, true);
                writer.write("BILL NUMBER : " + billNumber + " Order number :" + o.getIdOrder() + "\n");
                writer.write("Client's id: " + o.getIdClient() + "\n");
                writer.write("Date :" + o.getDate() + "\n");                billNumber++;
                for (MenuItem m : hashMap.get(o)) {
                    if (m instanceof BaseProduct) {
                        writer.write(m.getTitle() + "      " + ((BaseProduct) m).getPrice() + "\n");
                        totalPrice += ((BaseProduct) m).getPrice();
                    }
                    if (m instanceof CompositeProduct) {
                        writer.write(m.getTitle() + "       " + ((CompositeProduct) m).getPrice() + "\n");
                        totalPrice += ((CompositeProduct) m).getPrice();
                    }
                }
                writer.write("Total Price :" + totalPrice + "\n");
                writer.close();
            }
        }
        assert this.menuItems.size()>0;
        assert invariant();
    }

}
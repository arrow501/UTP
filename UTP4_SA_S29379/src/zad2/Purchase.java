/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad2;


public class Purchase {
    // fields
    private String id;
    private String name;
    private String product;
    private double price;
    private double quantity;

    // constructor
    public Purchase(String id, String name, String product, double price, double quantity) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }
    public double getCost(){
        return  quantity*price;
    }

    // setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // toString
    public String toString() {
        return id + ";" + name + ";" + product + ";" + price + ";" + quantity;
    }

    // equals
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Purchase other = (Purchase) obj;
        return id.equals(other.id) && name.equals(other.name) && product.equals(other.product)
                && price == other.price && quantity == other.quantity;
    }

    // hashCode
    public int hashCode() {
        return id.hashCode();
    }
}

/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad2;
import java.beans.*;
import java.io.Serializable;

public class Purchase implements Serializable {
    private String prod;
    private String data;
    private Double price;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public Purchase(String prod, String data, Double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    public synchronized String getProd() {
        return prod;
    }

    public synchronized void setProd(String prod) {
        this.prod = prod;
    }

    public synchronized String getData() {
        return data;
    }

    public synchronized void setData(String data) throws PropertyVetoException {
        String oldData = this.data;
        this.data = data;
        pcs.firePropertyChange("data", oldData, data);
    }

    public synchronized Double getPrice() {
        return price;
    }

    public synchronized void setPrice(Double price) throws PropertyVetoException {
        Double oldPrice = this.price;
        vcs.fireVetoableChange("price", oldPrice, price);
        this.price = price;
        pcs.firePropertyChange("price", oldPrice, price);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
        vcs.addVetoableChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
        vcs.removeVetoableChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }
}
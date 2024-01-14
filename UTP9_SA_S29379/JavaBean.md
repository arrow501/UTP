
JavaBeans are classes that encapsulate many objects into a single object (the bean). They are serializable, have a zero-argument constructor, and allow access to properties using getter and setter methods. They are used to encapsulate many objects into a single object (the bean), so that they can be passed around as a single bean object instead of multiple individual objects.

## Characteristics of JavaBeans

1. **Serializable:** JavaBeans implement the `Serializable` interface, which allows them to be written out and read back into the application smoothly. This is useful for saving the state of an application or transmitting objects between servers in a network.

2. **Zero-Argument Constructor:** JavaBeans must have a public no-arg constructor. This allows easy instantiation within editing and activation frameworks.

3. **Getter and Setter Methods:** JavaBeans use getter and setter methods to retrieve and modify the properties of the bean. The methods for a property `xyz` would be `getXyz()` and `setXyz()`. This convention makes it easy to inspect and manipulate the bean properties.

## JavaBeans in the Project

In the project, the `Purchase` class in `src/zad2/Purchase.java` is an example of a JavaBean. It has a zero-argument constructor, getter and setter methods for its properties, and it implements the `Serializable` interface.

```java
public class Purchase {
    private String prod;
    private String data;
    private Double price;

    // ... rest of the class
}
```

## Property Change Support

JavaBeans can support bound properties, which are bean properties that notify interested parties when their values change. This is done using the `PropertyChangeSupport` class and `PropertyChangeListener` interface.

In the `Purchase` class, a `PropertyChangeSupport` object is used to manage listeners and fire `PropertyChangeEvent`s. The `setData` and `setPrice` methods fire these events when the data or price changes.

```java
public void setData(String data) {
    String oldData = this.data;
    this.data = data;
    pcs.firePropertyChange("data", oldData, data);
}

public void setPrice(Double price) throws PropertyVetoException {
    Double oldPrice = this.price;
    vcs.fireVetoableChange("price", oldPrice, price);
    this.price = price;
    pcs.firePropertyChange("price", oldPrice, price);
}
```

## Vetoable Change Support

JavaBeans can also support constrained properties, which are bean properties that allow other objects to veto proposed changes to their values. This is done using the `VetoableChangeSupport` class and `VetoableChangeListener` interface.

In the `Purchase` class, a `VetoableChangeSupport` object is used to manage listeners and fire `PropertyChangeEvent`s. The `setPrice` method fires a `PropertyVetoException` if the new price is less than 1000.

```java
public void setPrice(Double price) throws PropertyVetoException {
    Double oldPrice = this.price;
    vcs.fireVetoableChange("price", oldPrice, price);
    this.price = price;
    pcs.firePropertyChange("price", oldPrice, price);
}
```

## Introspection

Introspection is a feature in Java that allows for introspection of classes, interfaces, fields and methods at runtime. It is a powerful tool that enables Java code to discover information about the fields, methods and constructors of loaded classes, and to use reflected fields, methods, and constructors to operate on their underlying counterparts on objects, within security restrictions.  

## Serialization

JavaBeans can be serialized in two ways: using the default Java serialization mechanism, or using the XML encoder/decoder provided by the JavaBeans API. The latter allows for a more human-readable and editable format, which can be useful for debugging and tweaking the serialized data. 

## Conclusion

JavaBeans are a crucial part of Java programming, allowing for the encapsulation of multiple objects into a single object that can be easily passed around. They also provide support for bound and constrained properties, which can be used to create more interactive and responsive applications.
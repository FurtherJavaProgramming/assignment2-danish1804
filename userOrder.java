package application.Model;

import javafx.beans.property.*;

public class userOrder {
    private final StringProperty orderID = new SimpleStringProperty();
    private final StringProperty bookID;
    private final StringProperty bookName;
    private final StringProperty orderDate;
    private final IntegerProperty physicalCopies;
    private final IntegerProperty ebookCopies;
    private final IntegerProperty totalItems;
    private final DoubleProperty totalPrice;

 // Constructor without orderID for new orders
    public userOrder(String bookID, String bookName, String orderDate,
                     int physicalCopies, int ebookCopies, double totalPrice) {
//        this.orderID = new SimpleStringProperty(generateOrderID(getOrderID()));
        this.bookID = new SimpleStringProperty(bookID);
        this.bookName = new SimpleStringProperty(bookName);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.physicalCopies = new SimpleIntegerProperty(physicalCopies);
        this.ebookCopies = new SimpleIntegerProperty(ebookCopies);
        this.totalItems = new SimpleIntegerProperty(physicalCopies + ebookCopies);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.orderID.set(generateOrderID(orderDate));
    }

//    // Constructor for fetching existing orders
//    public userOrder(String orderID, String bookID, String bookName, String orderDate,
//                     int physicalCopies, int ebookCopies, double totalPrice) {
//        this.orderID = new SimpleStringProperty(orderID);
//        this.bookID = new SimpleStringProperty(bookID);
//        this.bookName = new SimpleStringProperty(bookName);
//        this.orderDate = new SimpleStringProperty(orderDate);
//        this.physicalCopies = new SimpleIntegerProperty(physicalCopies);
//        this.ebookCopies = new SimpleIntegerProperty(ebookCopies);
//        this.totalItems = new SimpleIntegerProperty(physicalCopies + ebookCopies);
//        this.totalPrice = new SimpleDoubleProperty(totalPrice);
//    }

    // Property methods
    public StringProperty orderIDProperty() {
        return orderID;
    }

    public StringProperty bookIDProperty() {
        return bookID;
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public StringProperty orderDateProperty() {
        return orderDate;
    }

    public IntegerProperty physicalCopiesProperty() {
        return physicalCopies;
    }

    public IntegerProperty ebookCopiesProperty() {
        return ebookCopies;
    }

    public IntegerProperty totalItemsProperty() {
        return totalItems;
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    // Getters
    public String getOrderID() {
        return orderID.get();
    }

    public String getBookID() {
        return bookID.get();
    }

    public String getBookName() {
        return bookName.get();
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public int getPhysicalCopies() {
        return physicalCopies.get();
    }

    public int getEbookCopies() {
        return ebookCopies.get();
    }

    public int getTotalItems() {
        return totalItems.get();
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    // Setters
    public void setOrderID(String orderID) {
        this.orderID.set(orderID);
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }
    
 // Utility method for generating orderID
    private String generateOrderID(String orderDate) {
        return "ORD-" + (System.currentTimeMillis() + orderDate).replaceAll("[^A-Za-z]", "").toUpperCase();
    }

    public void setPhysicalCopies(int physicalCopies) {
        if (physicalCopies < 0) {
            throw new IllegalArgumentException("Physical copies cannot be negative.");
        }
        this.physicalCopies.set(physicalCopies);
        recalculateTotalItems();
    }

    public void setEbookCopies(int ebookCopies) {
        if (ebookCopies < 0) {
            throw new IllegalArgumentException("Ebook copies cannot be negative.");
        }
        this.ebookCopies.set(ebookCopies);
        recalculateTotalItems();
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative.");
        }
        this.totalPrice.set(totalPrice);
    }

    // Helper methods
    private void recalculateTotalItems() {
        this.totalItems.set(getPhysicalCopies() + getEbookCopies());
    }

    // ToString Method for debugging or logging
    @Override
    public String toString() {
        return "userOrder{" +
               "orderID=" + getOrderID() +
               ", bookID=" + getBookID() +
               ", bookName=" + getBookName() +
               ", orderDate=" + getOrderDate() +
               ", physicalCopies=" + getPhysicalCopies() +
               ", ebookCopies=" + getEbookCopies() +
               ", totalItems=" + getTotalItems() +
               ", totalPrice=" + getTotalPrice() +
               '}';
    }
}

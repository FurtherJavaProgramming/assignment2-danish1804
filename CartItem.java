package application.Model;

import javafx.beans.property.*;

public class CartItem {

    private final Book book; // The book associated with this cart item
    private final IntegerProperty physicalCopies; // Number of physical copies in the cart
    private final IntegerProperty ebookCopies; // Number of ebook copies in the cart
    private final DoubleProperty totalCost; // Total cost of this cart item

    // Constructor to initialize the CartItem
    public CartItem(Book book, int quantity, boolean isEbook) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.book = book;

        this.physicalCopies = new SimpleIntegerProperty(isEbook ? 0 : quantity);
        this.ebookCopies = new SimpleIntegerProperty(isEbook ? quantity : 0);
        this.totalCost = new SimpleDoubleProperty(calculateTotalCost());
    }

    // Method to calculate the total cost of the cart item
    private double calculateTotalCost() {
        return (getPhysicalCopies() * book.getPhysicalPrice()) + (getEbookCopies() * book.getEbookPrice());
    }

    // Recalculate and update the total cost
    private void updateTotalCost() {
        this.totalCost.set(calculateTotalCost());
    }

    // Getters for JavaFX properties
    public IntegerProperty physicalCopiesProperty() {
        return physicalCopies;
    }

    public IntegerProperty ebookCopiesProperty() {
        return ebookCopies;
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }

    // Getter for book ID
    public String getBookID() {
        return book.getBookID();
    }

    // Getter for book title
    public String getBookTitle() {
        return book.getBookTitle();
    }

    // Getter for the Book object
    public Book getBook() {
        return this.book;
    }

    // Add physical copies to the cart item
    public void addPhysicalCopies(int amount) {
    	if (amount > 0 && amount <= book.getNumberOfPhysicalCopies()) {
            physicalCopies.set(physicalCopies.get() + amount);
            updateTotalCost();
        } else {
            throw new IllegalArgumentException("Invalid amount to add. Check stock availability.");
        }
    }

    // Remove physical copies from the cart item, ensuring non-negative result
    public void removePhysicalCopies(int amount) {
        if (physicalCopies.get() >= amount) {
            physicalCopies.set(physicalCopies.get() - amount);
            updateTotalCost();
        } else {
            throw new IllegalArgumentException("Not enough physical copies to remove.");
        }
    }

    // Add ebook copies to the cart item
    public void addEbookCopies(int amount) {
        if (amount > 0) {
            ebookCopies.set(ebookCopies.get() + amount);
            updateTotalCost();
        }
    }

    // Remove ebook copies from the cart item, ensuring non-negative result
    public void removeEbookCopies(int amount) {
        if (ebookCopies.get() >= amount) {
            ebookCopies.set(ebookCopies.get() - amount);
            updateTotalCost();
        } else {
            throw new IllegalArgumentException("Not enough ebook copies to remove.");
        }
    }

    // Getter for the number of physical copies
    public int getPhysicalCopies() {
        return physicalCopies.get();
    }

    // Getter for the number of ebook copies
    public int getEbookCopies() {
        return ebookCopies.get();
    }

    // Getter for the total cost
    public double getTotalCost() {
        return totalCost.get();
    }
    
    @Override
    public String toString() {
        return "CartItem[BookID=" + getBookID() + ", Title=" + getBookTitle() + ", PhysicalCopies=" + getPhysicalCopies() +
               ", EbookCopies=" + getEbookCopies() + ", TotalCost=" + getTotalCost() + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        // Check if the current object is being compared to itself
        if (this == obj) return true;

        // Check if the compared object is null or if it's not of the same class
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast the compared object to CartItem, as we know it's the same class
        CartItem cartItem = (CartItem) obj;

        // Compare the books associated with both CartItem instances for equality
        return book.equals(cartItem.book);
    }

    @Override
    public int hashCode() {
        // Use the hash code of the book to represent the hash code of the CartItem
        // This ensures that CartItems with the same book have the same hash code
        return book.hashCode();
    }


}

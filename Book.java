package application.Model;

import javafx.beans.property.*;
import java.util.Random;

public class Book {
    private final StringProperty bookID = new SimpleStringProperty();
    private final StringProperty bookTitle;
    private final StringProperty authorName;
    private final IntegerProperty numberOfPhysicalCopies;
    private final BooleanProperty hasEbook;
    private final DoubleProperty physicalPrice;
    private final DoubleProperty ebookPrice;
    private final BooleanProperty selected = new SimpleBooleanProperty();
    //private final IntegerProperty numberOfPhysicalCopies = new SimpleIntegerProperty();
    private final BooleanProperty hasPhysicalCopy = new SimpleBooleanProperty();

    public Book(String bookTitle, String authorName, int numberOfPhysicalCopies, boolean hasEbook, double physicalPrice, double ebookPrice) {
        setBookID(generateBookID(authorName + bookTitle));
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.authorName = new SimpleStringProperty(authorName);
        this.numberOfPhysicalCopies = new SimpleIntegerProperty();
        this.hasEbook = new SimpleBooleanProperty(hasEbook);
        this.physicalPrice = new SimpleDoubleProperty(physicalPrice);
        this.ebookPrice = new SimpleDoubleProperty(ebookPrice);
        this.bookID.set(generateBookID(authorName));
    }

    public String getBookID() {
        return bookID.get();
    }
    
    
    private String generateBookID(String authorName) {
        String initials = getAuthorInitials(authorName).replaceAll("[^A-Za-z]", "").toUpperCase();
        // Generates a number between 0 and 999999
        return "BI" + String.format("%s-%06d", initials);
    }
    private String getAuthorInitials (String authorName) {
        // Create a StringBuilder to efficiently build the initials string
        StringBuilder initials = new StringBuilder();
    
        // Split the author's name into parts, using space as the delimiter
        String[] nameParts = authorName.split(" ");
    
        // Loop through each part of the name
        for (int i = 0; i < nameParts.length; i++) {
            // Check if the current name part is not empty
            if (!nameParts[i].isEmpty()) {
                // If not empty, append the first character of this name part to the initials
                initials.append(nameParts[i].charAt(0));
            }
        }
    
        // Convert the initials to uppercase and return as a string
        return initials.toString().toUpperCase();
    }
    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public StringProperty bookIDProperty() {
        return bookID;
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName.get();
    }

    public void setAuthorName(String authorName) {
        this.authorName.set(authorName);
    }
    
 // Decrease physical copies
    public void decreasePhysicalCopies(int amount) {
        if (numberOfPhysicalCopies.get() >= amount) {
            numberOfPhysicalCopies.set(numberOfPhysicalCopies.get() - amount);
            hasPhysicalCopy.set(numberOfPhysicalCopies.get() > 0);
        } else {
            throw new IllegalArgumentException("Not enough physical copies available");
        }
    }

    // Increase physical copies
    public void increasePhysicalCopies(int amount) {
        numberOfPhysicalCopies.set(numberOfPhysicalCopies.get() + amount);
        hasPhysicalCopy.set(true);
    }

    public StringProperty authorNameProperty() {
        return authorName;
    }

    public int getNumberOfPhysicalCopies() {
        return numberOfPhysicalCopies.get();
    }

    public void setNumberOfPhysicalCopies(int numberOfPhysicalCopies) {
        this.numberOfPhysicalCopies.set(numberOfPhysicalCopies);
    }

    public IntegerProperty numberOfPhysicalCopiesProperty() {
        return numberOfPhysicalCopies;
    }

    // Getter and property for hasPhysicalCopy
    public boolean isHasPhysicalCopy() {
        return hasPhysicalCopy.get();
    }

    public BooleanProperty hasPhysicalCopyProperty() {
        return hasPhysicalCopy;
    }

    public boolean isHasEbook() {
        return hasEbook.get();
    }

    public void setHasEbook(boolean hasEbook) {
        this.hasEbook.set(hasEbook);
    }

    public BooleanProperty hasEbookProperty() {
        return hasEbook;
    }

    public double getPhysicalPrice() {
        return physicalPrice.get();
    }

    public void setPhysicalPrice(double physicalPrice) {
    	if (physicalPrice < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.physicalPrice.set(physicalPrice);
    }

    public DoubleProperty physicalPriceProperty() {
        return physicalPrice;
    }

    public double getEbookPrice() {
        return ebookPrice.get();
    }

    public void setEbookPrice(double ebookPrice) {
    	if (ebookPrice < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.ebookPrice.set(ebookPrice);
    }

    public DoubleProperty ebookPriceProperty() {
        return ebookPrice;
    }
    
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
    
    @Override
    public String toString() {
        return "Book[ID=" + getBookID() + ", Title=" + getBookTitle() + ", Author=" + getAuthorName() + "]";
    }
    
    
}

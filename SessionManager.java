package application.Manager;

import application.Model.CartItem;
import application.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SessionManager {

    private static SessionManager instance;
    private User loggedInUser;
    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    // Private constructor to prevent direct instantiation
    private SessionManager() {}

    // Get the singleton instance
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set the logged-in user
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    // Get the logged-in user
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    // Clear the logged-in user (e.g., on logout)
    public void clearSession() {
        this.loggedInUser = null;
    }
    
    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}

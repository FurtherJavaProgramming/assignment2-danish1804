package application.Controller;

import application.DAO.OrderDAO;
import application.Manager.NavigationManager;
import application.Manager.SessionManager;
import application.Model.CartItem;
import application.Model.userOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class checkoutScreenController {

    @FXML
    private TableView<CartItem> orderSummaryTable;

    @FXML
    private TableColumn<CartItem, String> titleColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;

    @FXML
    private TableColumn<CartItem, Double> priceColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private RadioButton creditCardRadio;

    @FXML
    private RadioButton paypalRadio;

    @FXML
    private Button backToCartButton;

    @FXML
    private Button placeOrderButton;

    private ObservableList<CartItem> cartItems;

    private double totalPrice;

    @FXML
    public void initialize() {
        // Configure order summary table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());

        // Fetch cart items from SessionManager
        cartItems = FXCollections.observableArrayList(SessionManager.getInstance().getCartItems());
        orderSummaryTable.setItems(cartItems);

        // Calculate and display total price
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalCost).sum();
        totalPriceLabel.setText(String.format("$%.2f", totalPrice));
    }

    @FXML
    private void handleBackToCart(ActionEvent event) {
        // Navigate back to Cart screen
        Stage stage = (Stage) orderSummaryTable.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/viewCart.fxml"); // Replace with actual FXML file name
    }

    @FXML
    private void handlePlaceOrder(ActionEvent event) {
        // Validate shipping information and payment method
        if (!isShippingInfoValid()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Information", "Please fill in all shipping fields correctly.");
            return;
        }

        if (!creditCardRadio.isSelected() && !paypalRadio.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Payment Method", "Please select a payment method.");
            return;
        }

       
       

        LocalDate orderDate = LocalDate.now();
        boolean orderPlacementSuccess = true;

        for (CartItem item : cartItems) {
            userOrder order = new userOrder(
                    item.getBook().getBookID(),
                    item.getBook().getBookTitle(),
                    orderDate.toString(),
                    item.getPhysicalCopies(),
                    item.getEbookCopies(),
                    item.getTotalCost()
            );
            boolean success = OrderDAO.addOrder(order);
            if (!success) {
                orderPlacementSuccess = false;
                break;
            }
        }

        if (orderPlacementSuccess) {
            // Clear the cart in SessionManager
            SessionManager.getInstance().clearCart();

            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Order Placed", "Your order has been placed successfully!");

            // Navigate to Order Confirmation screen with Order ID
            Stage stage = (Stage) placeOrderButton.getScene().getWindow();
            NavigationManager navigationManager = new NavigationManager(stage);
            navigationManager.navigateTo("/application/FXML_FIles/orderConfirmationScreen.fxml");// Method in OrderConfirmationController
        } else {
            showAlert(Alert.AlertType.ERROR, "Order Failed", "Failed to place the order. Please try again.");
        }
    }

    private boolean isShippingInfoValid() {
        return !nameField.getText().trim().isEmpty() &&
               !addressField.getText().trim().isEmpty() &&
               !cityField.getText().trim().isEmpty() &&
               !countryField.getText().trim().isEmpty() &&
               !postalCodeField.getText().trim().isEmpty();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

package application.Controller;

import application.DAO.OrderDAO;
import application.Manager.NavigationManager;
import application.Manager.SessionManager;
import application.Model.CartItem;
import application.Model.userOrder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class orderConfirmationScreenController {

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private TableView<CartItem> orderSummaryTable;

    @FXML
    private TableColumn<CartItem, String> titleColumn;

    @FXML
    private TableColumn<CartItem, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<CartItem, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<CartItem, Double> priceColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private Button returnToDashboardButton;

    private ObservableList<CartItem> orderItems; // Items in the order
    private double totalPrice;
    private String generatedOrderId;

    @FXML
    public void initialize() {
        // Bind columns to CartItem properties
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getBookTitle()));
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());

        // Fetch cart items from SessionManager
        orderItems = FXCollections.observableArrayList(SessionManager.getInstance().getCartItems());
        orderSummaryTable.setItems(orderItems);

        // Calculate and display total price
        updateTotalPrice();

        // Generate Order ID and Date
//        generatedOrderId = OrderDAO.generateOrderId(getCurrentDate());
        orderNumberLabel.setText("Order #: " + generatedOrderId);
        orderDateLabel.setText("Date: " + getCurrentDate());
    }

    private void updateTotalPrice() {
        totalPrice = orderItems.stream().mapToDouble(CartItem::getTotalCost).sum();
        totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return now.format(formatter);
    }

    @FXML
    private void handleConfirmOrder(ActionEvent event) {
        // Save order to database
        String orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        for (CartItem item : orderItems) {
            userOrder order = new userOrder(item.getBook().getBookID(), item.getBook().getBookTitle(),
                    orderDate, item.getPhysicalCopies(), item.getEbookCopies(), totalPrice);
            boolean success = OrderDAO.addOrder(order);

            if (!success) {
                showAlert(Alert.AlertType.ERROR, "Order Failed", "Failed to place the order. Please try again.");
                return;
            }
        }

        // Clear the cart in SessionManager
        SessionManager.getInstance().clearCart();

        // Show success alert
        showAlert(Alert.AlertType.INFORMATION, "Order Placed", "Your order has been placed successfully!");

        // Navigate to Order Confirmation screen
        Stage stage = (Stage) confirmOrderButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/thankYouScreen.fxml"); // Replace with actual FXML file name
    }

    @FXML
    private void handleReturnToDashboard(ActionEvent event) {
        // Navigate back to the dashboard
        Stage stage = (Stage) returnToDashboardButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml"); // Replace with actual FXML file name
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

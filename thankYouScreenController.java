package application.Controller;

import application.Manager.NavigationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class thankYouScreenController {

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label totalItemsLabel;

    @FXML
    private Label totalAmountLabel;
    
    @FXML
    private Button viewOrderButton;
    
    @FXML
    private Button backToDashBoardButton;
    /**
     * Method to initialize the Thank You screen with order details.
     * This method can be called after navigating to this screen and passing order data.
     */
    public void setOrderDetails(String orderNumber, String orderDate, int totalItems, double totalAmount) {
        orderNumberLabel.setText("Order Number: " + orderNumber);
        orderDateLabel.setText("Order Date: " + orderDate);
        totalItemsLabel.setText("Total Items: " + totalItems);
        totalAmountLabel.setText("Total Amount: $" + String.format("%.2f", totalAmount));
    }

    @FXML
    private void handleViewOrderDetails(ActionEvent event) {
        // Navigate to the Order Details screen
        Stage stage = (Stage) viewOrderButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/detailOrderView.fxml"); // Replace with the actual FXML file name
    }

    @FXML
    private void handleReturnToDashboard(ActionEvent event) {
        // Navigate back to the dashboard
        Stage stage = (Stage) orderNumberLabel.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml"); // Replace with the actual FXML file name
    }
}

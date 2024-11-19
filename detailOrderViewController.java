package application.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import application.Manager.NavigationManager;
import application.Model.CartItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class detailOrderViewController {

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label shippingAddressLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private TableView<CartItem> orderedItemsTable;

    @FXML
    private TableColumn<CartItem, String> titleColumn;

    @FXML
    private TableColumn<CartItem, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<CartItem, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<CartItem, Double> priceColumn;

    @FXML
    private Label totalAmountLabel;
    
    @FXML
    private Button printOrderButton;
    
    @FXML
    private Button backToDashBoardButton;

    private ObservableList<CartItem> orderedItems;

    @FXML
    public void initialize() {
        // Bind columns to CartItem properties
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookTitle()));
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());

        // Initialize ordered items (mock data for now)
        orderedItems = FXCollections.observableArrayList(
                new CartItem(new application.Model.Book("Java Programming", "John Doe", 10, true, 500.0, 300.0), 2, false),
                new CartItem(new application.Model.Book("Data Structures", "Jane Smith", 5, true, 600.0, 400.0), 1, true)
        );

        orderedItemsTable.setItems(orderedItems);

        // Display total amount
        updateTotalAmount();

        // Mock data for order details
        populateOrderDetails("O123", "2024-11-18", "John Doe", "1234 Elm Street, Springfield", "Credit Card");
    }

    private void populateOrderDetails(String orderNumber, String orderDate, String customerName, String shippingAddress, String paymentMethod) {
        orderNumberLabel.setText(orderNumber);
        orderDateLabel.setText(orderDate);
        customerNameLabel.setText(customerName);
        shippingAddressLabel.setText(shippingAddress);
        paymentMethodLabel.setText(paymentMethod);
    }

    private void updateTotalAmount() {
        double totalAmount = orderedItems.stream().mapToDouble(CartItem::getTotalCost).sum();
        totalAmountLabel.setText(String.format("$%.2f", totalAmount));
    }

    @FXML
    private void handlePrintOrder(ActionEvent event) {
        // Choose a location to save the order summary
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Order Details");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) printOrderButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write order details to the file
                writer.write("Order Details\n");
                writer.write("=====================\n");
                writer.write("Order Number: " + orderNumberLabel.getText() + "\n");
                writer.write("Order Date: " + orderDateLabel.getText() + "\n");
                writer.write("Customer Name: " + customerNameLabel.getText() + "\n");
                writer.write("Shipping Address: " + shippingAddressLabel.getText() + "\n");
                writer.write("Payment Method: " + paymentMethodLabel.getText() + "\n\n");
                writer.write("Ordered Items:\n");

                for (CartItem item : orderedItems) {
                    writer.write(" - Title: " + item.getBookTitle() +
                                 ", Physical Copies: " + item.getPhysicalCopies() +
                                 ", E-book Copies: " + item.getEbookCopies() +
                                 ", Price: $" + item.getTotalCost() + "\n");
                }

                writer.write("\nTotal Amount: " + totalAmountLabel.getText() + "\n");
                writer.write("=====================\n");

                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Print Order");
                alert.setHeaderText("Order Printed Successfully");
                alert.setContentText("The order details have been saved to:\n" + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                // Show error alert in case of an exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Print Failed");
                alert.setContentText("An error occurred while saving the order details.");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleReturnToDashboard(ActionEvent event) {
        // Navigate back to the dashboard
        Stage stage = (Stage) totalAmountLabel.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml"); // Replace with actual FXML file name
    }
}

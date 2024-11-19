package application.Controller;

import application.Manager.NavigationManager;
import application.Model.userOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;

public class adminViewOrderDetailsController {

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label shippingAddressLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    private TableView<userOrder> orderItemsTable;

    @FXML
    private TableColumn<userOrder, String> bookTitleColumn;

    @FXML
    private TableColumn<userOrder, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<userOrder, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<userOrder, Double> physicalPriceColumn;

    @FXML
    private TableColumn<userOrder, Double> ebookPriceColumn;

    @FXML
    private TableColumn<userOrder, Double> totalItemCostColumn;

    @FXML
    private Label totalPhysicalCopiesLabel;

    @FXML
    private Label totalEbookCopiesLabel;

    @FXML
    private Label totalCostLabel;

    private ObservableList<userOrder> orderItemsList = FXCollections.observableArrayList();

    /**
     * Creates a NavigationManager dynamically based on the current Stage.
     * 
     * @param event The ActionEvent triggering the navigation.
     * @return A new instance of NavigationManager.
     */
    private NavigationManager createNavigationManager(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return new NavigationManager(stage);
    }

    /**
     * Initializes the table and loads order details.
     */
    @FXML
    public void initialize() {
        // Bind columns to userOrder properties
        bookTitleColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());
        physicalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        ebookPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        totalItemCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Load sample order data (replace this with real data)
        loadOrderDetails();
        calculateTotals();
    }

    /**
     * Loads sample order data and populates UI components.
     */
    private void loadOrderDetails() {
        // Set sample data (replace with database or API data)
        orderIdLabel.setText("O001");
        customerNameLabel.setText("John Doe");
        shippingAddressLabel.setText("123 Main St, Springfield, USA");
        paymentMethodLabel.setText("Credit Card");
        orderDateLabel.setText("2024-11-15");
        orderStatusLabel.setText("Processing");

        // Populate order items table (replace with real data)
        orderItemsList.add(new userOrder("O001", "B001", "Book 1", "2024-11-15", 2, 1, 3, 29.99));
        orderItemsList.add(new userOrder("O001", "B002", "Book 2", "2024-11-15", 1, 2, 3, 59.99));
        orderItemsList.add(new userOrder("O001", "B003", "Book 3", "2024-11-15", 0, 3, 3, 39.99));

        orderItemsTable.setItems(orderItemsList);
    }

    /**
     * Calculates totals for physical copies, e-books, and total cost.
     */
    private void calculateTotals() {
        int totalPhysical = 0;
        int totalEbooks = 0;
        double totalCost = 0;

        for (userOrder order : orderItemsList) {
            totalPhysical += order.physicalCopiesProperty().get();
            totalEbooks += order.ebookCopiesProperty().get();
            totalCost += order.totalPriceProperty().get();
        }

        totalPhysicalCopiesLabel.setText(String.valueOf(totalPhysical));
        totalEbookCopiesLabel.setText(String.valueOf(totalEbooks));
        totalCostLabel.setText("$" + totalCost);
    }

    /**
     * Handles updating the status of the order.
     */
    @FXML
    private void handleUpdateStatus(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(orderStatusLabel.getText());
        dialog.setTitle("Update Order Status");
        dialog.setHeaderText("Update the status of the order:");
        dialog.setContentText("Status:");

        dialog.showAndWait().ifPresent(newStatus -> {
            orderStatusLabel.setText(newStatus);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order status updated to: " + newStatus);
            alert.showAndWait();
        });
    }

    /**
     * Handles closing the current order details view.
     */
    @FXML
    private void handleClose(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/viewAllOrders.fxml");
    }
}

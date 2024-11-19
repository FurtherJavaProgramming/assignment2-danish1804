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

public class adminViewAllOrdersController {

    @FXML
    private TableView<userOrder> ordersTableView;

    @FXML
    private TableColumn<userOrder, String> orderIDColumn;

    @FXML
    private TableColumn<userOrder, String> customerNameColumn;

    @FXML
    private TableColumn<userOrder, String> orderDateColumn;

    @FXML
    private TableColumn<userOrder, Integer> totalItemsColumn;

    @FXML
    private TableColumn<userOrder, Double> totalPriceColumn;

    @FXML
    private TableColumn<userOrder, Integer> physicalCopiesOrderedColumn;

    @FXML
    private TableColumn<userOrder, Integer> ebookCopiesOrderedColumn;

    @FXML
    private TableColumn<userOrder, Void> actionsColumn;

    @FXML
    private Button backToDashboardButton;

    private ObservableList<userOrder> orderList = FXCollections.observableArrayList();

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

    @FXML
    public void initialize() {
        // Initialize the table columns
        orderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        orderDateColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        totalItemsColumn.setCellValueFactory(cellData -> cellData.getValue().totalItemsProperty().asObject());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        physicalCopiesOrderedColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesOrderedColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());

        // Add actions column
        initializeActionsColumn();

        // Load sample data
        loadOrderData();
    }

    private void initializeActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View");

            {
                viewButton.setOnAction(event -> {
                    userOrder order = getTableView().getItems().get(getIndex());
                    System.out.println("Viewing order: " + order.orderIDProperty().get());
                    // Add more logic here, such as navigating to a detailed order view
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });
    }

    private void loadOrderData() {
        // Add sample data to the table
        orderList.add(new userOrder("O001", "B001", "Book 1", "2024-11-15", 2, 1, 3, 39.99));
        orderList.add(new userOrder("O002", "B002", "Book 2", "2024-11-16", 1, 0, 1, 19.99));
        orderList.add(new userOrder("O003", "B003", "Book 3", "2024-11-17", 0, 2, 2, 29.99));

        ordersTableView.setItems(orderList);
    }

    /**
     * Handles navigation back to the admin dashboard.
     */
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminDashboard.fxml");
    }
}

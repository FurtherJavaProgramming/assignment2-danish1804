package application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Manager.NavigationManager;
import application.Manager.SessionManager;
import application.Model.userOrder;
import application.DAO.OrderDAO;

public class userOrdersController {

    @FXML
    private TableView<userOrder> ordersTableView;

    @FXML
    private TableColumn<userOrder, String> orderIDColumn;

    @FXML
    private TableColumn<userOrder, String> orderBookIDColumn;

    @FXML
    private TableColumn<userOrder, String> orderBookNameColumn;

    @FXML
    private TableColumn<userOrder, String> orderDateColumn;

    @FXML
    private TableColumn<userOrder, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<userOrder, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<userOrder, Integer> totalItemsColumn;

    @FXML
    private TableColumn<userOrder, Double> totalPriceColumn;

    @FXML
    private Button backToDashboardButton;

    private ObservableList<userOrder> ordersList;

    @FXML
    public void initialize() {
        // Bind columns to Order properties
        orderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty());
        orderBookIDColumn.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty());
        orderBookNameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        orderDateColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());
        totalItemsColumn.setCellValueFactory(cellData -> cellData.getValue().totalItemsProperty().asObject());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Fetch and load orders for the current user
        try {
            String currentUserId = SessionManager.getInstance().getLoggedInUser().getUserId();
            ordersList = FXCollections.observableArrayList(OrderDAO.getOrdersForUser(currentUserId));
            ordersTableView.setItems(ordersList);

            if (ordersList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Orders");
                alert.setHeaderText(null);
                alert.setContentText("No orders found for this user.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Orders");
            alert.setContentText("An error occurred while fetching orders. Please try again later.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        // Navigate back to the dashboard
        Stage stage = (Stage) backToDashboardButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml"); // Replace with actual FXML file name
    }
}

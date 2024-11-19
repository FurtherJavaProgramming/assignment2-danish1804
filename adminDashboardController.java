package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Manager.NavigationManager;

public class adminDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button manageBooksButton;

    @FXML
    private Button viewBookStockButton;

    @FXML
    private Button updateBookStockButton;

    @FXML
    private Button addNewBookButton;

    @FXML
    private Button viewAllOrdersButton;

    @FXML
    private Button managerUsersButton;

    @FXML
    private Button GenerateReportsButton;
    
    @FXML
    private Button removeBookButton;
    
    @FXML
    private Button GenerateSalesReportsButton;
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
    private void handleLogout(ActionEvent event) {
        // Confirm logout action
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("You are about to logout.");
        alert.setContentText("Do you want to proceed?");
        
        if (alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            // Navigate back to the admin login screen
            NavigationManager navigationManager = createNavigationManager(event);
            navigationManager.navigateTo("/application/FXML_Files/adminViews/adminLoginPage.fxml");
        }
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminUpdateBookStock.fxml");
    }
    
    @FXML
    private void handleRemoveBook(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminRemoveBook.fxml");
    }
    
    @FXML
    private void handleViewBookStock(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminViewBookStock.fxml");
    }

    @FXML
    private void handleUpdateBookStock(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminUpdateBookStock.fxml");
    }

    @FXML
    private void handleAddNewBook(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminAddBook.fxml");
    }

    @FXML
    private void handleViewAllOrders(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminViewAllOrders.fxml");
    }
    
    @FXML
    private void handleGenerateSalesReports(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminSalesReport.fxml");
    }

    @FXML
    private void handleManageUsers(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminManageUsers.fxml");
    }

    @FXML
    private void handleGenerateReports(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminGenerateReports.fxml");
    }
    
    public void setAdminName(String adminName) {
        welcomeLabel.setText("Welcome, " + adminName + "!");
    }

}

package application.Controller;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

import application.Manager.*;
import application.Model.User;
import application.Model.userOrder;
import javafx.scene.*;

public class userDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<?> topBooksTable;
    
    
    @FXML
    public void initialize() {
        User loggedInUser = SessionManager.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            welcomeLabel.setText("Welcome, " + loggedInUser.getFirstName() + "!");
        }
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Any unsaved changes will be lost.");
        if (alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            NavigationManager navigationManager = new NavigationManager(stage);
            navigationManager.navigateTo("/application/FXML_FIles/loginPage.fxml");
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) {
        // Navigate to Edit Profile page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/editUserProfile.fxml"); // Replace with the actual file name
    }

    @FXML
    private void handleBrowseBooks(ActionEvent event) {
        // Navigate to Browse Books page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/browseBooks.fxml"); // Replace with the actual file name
    }

    @FXML
    private void handleViewCart(ActionEvent event) {
        // Navigate to View Cart page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/viewCart.fxml"); // Replace with the actual file name
    }

    @FXML
    private void handleViewOrders(ActionEvent event) {
        // Navigate to View Orders page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userOrders.fxml"); // Replace with the actual file name
    }

    @FXML
    private void handleExportOrders(ActionEvent event) {
    	// Navigate to Browse Books page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/exportUserOrders.fxml");
    }
    

}

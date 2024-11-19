package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import application.Manager.*;

public class LoginController {

    @FXML
    private void handleUserLoginPage(ActionEvent event) {
        // Navigate to User Login Page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userLoginScreen.fxml"); // Replace with the actual FXML file name
    }

    @FXML
    private void handleAdminLoginPage(ActionEvent event) {
        // Navigate to Admin Login Page
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminLoginPage.fxml"); // Replace with the actual FXML file name
    }
}

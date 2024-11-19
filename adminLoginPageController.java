package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Manager.NavigationManager;

public class adminLoginPageController {

    @FXML
    private TextField adminUsernameField;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private Button adminLoginButton;

    @FXML
    private Hyperlink adminRegisterLink;

    @FXML
    private Hyperlink adminBackLink;

    /**
     * Handles admin login logic.
     */
    @FXML
    private void handleAdminLogin(ActionEvent event) {
        String username = adminUsernameField.getText();
        String password = adminPasswordField.getText();

        // Mock validation logic (replace with actual database/service validation)
        if ("admin".equals(username) && "password".equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome, Admin!");
            alert.setContentText("You have successfully logged in.");
            alert.showAndWait();

            // Navigate to the Admin Dashboard
            Stage stage = (Stage) adminLoginButton.getScene().getWindow();
            NavigationManager navigationManager = new NavigationManager(stage);
            navigationManager.navigateTo("/application/FXML_Files/adminViews/adminDashboard.fxml"); 
            
           
// Replace with actual FXML file
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        }

    }

    /**
     * Handles navigation to the admin registration screen.
     */
    @FXML
    private void handleAdminRegisterLink(ActionEvent event) {
        Stage stage = (Stage) adminRegisterLink.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminRegistrationScreen.fxml"); // Replace with actual FXML file
    }

    /**
     * Handles navigation back to the choice screen.
     */
    @FXML
    private void handleAdminBackLink(ActionEvent event) {
        Stage stage = (Stage) adminBackLink.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_Files/loginPage.fxml"); // Replace with actual FXML file
    }
}

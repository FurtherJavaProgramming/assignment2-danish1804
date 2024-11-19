package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Manager.NavigationManager;

public class adminRegistrationScreenController {

    @FXML
    private TextField adminFirstNameField;

    @FXML
    private TextField adminLastNameField;

    @FXML
    private TextField adminUsernameField;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private PasswordField adminConfirmPasswordField;

    @FXML
    private DatePicker adminDOBPicker;

    @FXML
    private Button adminRegisterButton;

    @FXML
    private Hyperlink adminBackToLoginLink;

    /**
     * Handles the admin registration process.
     */
    @FXML
    private void handleAdminRegister(ActionEvent event) {
        // Retrieve input values
        String firstName = adminFirstNameField.getText();
        String lastName = adminLastNameField.getText();
        String username = adminUsernameField.getText();
        String password = adminPasswordField.getText();
        String confirmPassword = adminConfirmPasswordField.getText();
        String dob = adminDOBPicker.getValue() != null ? adminDOBPicker.getValue().toString() : null;

        // Input validation
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob == null) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
            return;
        }

        // Mock logic for saving admin details (replace with database logic)
        System.out.println("Admin registered successfully:");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Username: " + username);
        System.out.println("Date of Birth: " + dob);

        // Show success message
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Admin has been registered successfully!");

        // Navigate to Admin Login screen
        handleAdminBackToLogin(event);
    }

    /**
     * Handles navigation back to the admin login screen.
     */
    @FXML
    private void handleAdminBackToLogin(ActionEvent event) {
        Stage stage = (Stage) adminBackToLoginLink.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminLoginPage.fxml");
    }

    /**
     * Utility method to show alert dialogs.
     *
     * @param alertType The type of alert (e.g., INFORMATION, ERROR).
     * @param title     The title of the alert.
     * @param message   The message to display in the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

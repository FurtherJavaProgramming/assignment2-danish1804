package application.Controller;

import java.time.LocalDate;

import application.DAO.UserDAO;
import application.Manager.NavigationManager;
import application.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class userRegistrationScreenController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button userRegisterButton;

    @FXML
    private Hyperlink backToLogin;

    @FXML
    private Label statusLabel;

    /**
     * Handles user registration logic.
     * @param event ActionEvent triggered by clicking the register button.
     */
    @FXML
    private void handleRegisterUser(ActionEvent event) {
        String username = usernameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        LocalDate dob = dobPicker.getValue();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Validate input fields
        if (!isInputValid(username, firstName, lastName, dob, password, confirmPassword)) {
            return;
        }

        // Create a new user object
        User newUser = new User(username, firstName, lastName, dob);

        // Use DAO to register the user
        boolean success = UserDAO.registerUser(newUser);

        if (success) {
            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have registered successfully. Please proceed to login.");
            
            // Navigate to user login page
            navigateToLogin();
        } else {
            // Show failure message
            statusLabel.setText("Registration failed. Please try again.");
        }
    }

    /**
     * Navigates back to the login screen.
     * @param event ActionEvent triggered by clicking the back to login hyperlink.
     */
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        navigateToLogin();
    }

    /**
     * Validates user input fields.
     * @param username The username entered by the user.
     * @param firstName The first name entered by the user.
     * @param lastName The last name entered by the user.
     * @param dob The date of birth selected by the user.
     * @param password The password entered by the user.
     * @param confirmPassword The confirm password entered by the user.
     * @return true if all input is valid, false otherwise.
     */
    private boolean isInputValid(String username, String firstName, String lastName, LocalDate dob, String password, String confirmPassword) {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob == null || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields are required.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return false;
        }

        if (UserDAO.fetchUserByUsername(username) != null) {
            showAlert(Alert.AlertType.ERROR, "Username Taken", "The username is already in use. Please choose another.");
            return false;
        }

        return true;
    }

    /**
     * Displays an alert dialog.
     * @param alertType The type of the alert (e.g., INFORMATION, ERROR).
     * @param title The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navigates to the login page.
     */
    private void navigateToLogin() {
        Stage stage = (Stage) userRegisterButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userLoginScreen.fxml");
    }
}

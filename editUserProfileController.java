package application.Controller;

import application.DAO.UserDAO;
import application.Manager.NavigationManager;
import application.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class editUserProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private Button updateProfileButton;

    @FXML
    private Button backToDashboardButton;

    @FXML
    private Label statusLabel;

    private User currentUser; // Holds the current user data

    /**
     * Initialize method to populate fields with current user data.
     * Replace the mock data with actual data retrieval logic.
     */
    @FXML
    public void initialize() {
        // Mock current user data (Replace this with actual logic to fetch user from database)
        currentUser = UserDAO.fetchUserByUsername("johndoe123"); // Replace with logged-in user's username

        if (currentUser != null) {
            firstNameField.setText(currentUser.getFirstName());
            lastNameField.setText(currentUser.getLastName());
            usernameField.setText(currentUser.getUsername());
            dobPicker.setValue(currentUser.getDateOfBirth());
        } else {
            // Show error if user data cannot be retrieved
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to retrieve user data.");
        }
    }

    /**
     * Handles the update profile logic.
     * @param event The ActionEvent triggered by clicking the update button.
     */
    @FXML
    private void handleUpdateProfile(ActionEvent event) {
        if (isInputValid()) {
            // Update the current user object with new data
            currentUser.setFirstName(firstNameField.getText().trim());
            currentUser.setLastName(lastNameField.getText().trim());
            currentUser.setUsername(usernameField.getText().trim());
            currentUser.setDateOfBirth(dobPicker.getValue());

            // Update the user profile in the database
            boolean success = UserDAO.updateUser(currentUser);

            if (success) {
                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile has been updated successfully.");

                // Optionally, navigate back to the dashboard
                handleBackToDashboard(event);
            } else {
                // Show error alert
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update profile. Please try again.");
            }
        } else {
            // Show error alert for invalid input
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all required fields.");
        }
    }

    /**
     * Handles navigation back to the user dashboard.
     * @param event The ActionEvent triggered by clicking the back button.
     */
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        Stage stage = (Stage) backToDashboardButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml"); // Replace with actual FXML file name
    }

    /**
     * Validates input fields for completeness.
     * @return true if all required fields are filled, false otherwise.
     */
    private boolean isInputValid() {
        return !firstNameField.getText().trim().isEmpty() &&
               !lastNameField.getText().trim().isEmpty() &&
               !usernameField.getText().trim().isEmpty() &&
               dobPicker.getValue() != null;
    }

    /**
     * Displays an alert dialog.
     * @param alertType The type of alert (e.g., INFORMATION, ERROR).
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
}

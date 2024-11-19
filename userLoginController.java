package application.Controller;

import application.DAO.UserDAO;
import application.Manager.NavigationManager;
import application.Manager.SessionManager;
import application.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button userProfileLoginButton;

    @FXML
    private Label errorMessage;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Please fill in all fields.");
            return;
        }

        try {
            // Validate credentials
            if (isValidUser(username, password)) {
                User loggedInUser = UserDAO.fetchUserByUsername(username);
                if (loggedInUser != null) {
                    // Store user in session
                    SessionManager.getInstance().setLoggedInUser(loggedInUser);

                    // Navigate to dashboard
                    Stage stage = (Stage) userProfileLoginButton.getScene().getWindow();
                    NavigationManager navigationManager = new NavigationManager(stage);
                    navigationManager.navigateTo("/application/FXML_FIles/userDashboard.fxml");
                } else {
                    errorMessage.setText("Failed to retrieve user details.");
                }
            } else {
                errorMessage.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            errorMessage.setText("An error occurred. Please try again later.");
            e.printStackTrace();
        }
    }
    /**
     * Validates user credentials using the database.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return true if the credentials are valid, false otherwise.
     */
    private boolean isValidUser(String username, String password) {
    	try {
            return UserDAO.verifyPasswordByUsername(username, password);
        } catch (Exception e) {
            errorMessage.setText("An error occurred. Please try again later.");
            e.printStackTrace();
            return false;
        }
    }
}

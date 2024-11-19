package application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.time.LocalDate;

import application.Manager.NavigationManager;
import application.Model.*;

public class adminManageUsersController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> userIdColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> dobColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private Button updateUserButton;

    @FXML
    private Button viewAllOrdersButton;

    @FXML
    private Button backToDashboardButton;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // Set up table columns
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        // Load initial data into the table
        loadUserData();
    }

    /**
     * Handles the search action.
     */
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();

        if (keyword.isEmpty()) {
            userTableView.setItems(userList); // Reset table view
        } else {
            ObservableList<User> filteredList = FXCollections.observableArrayList();

            for (User user : userList) {
                if (user.getUsername().toLowerCase().contains(keyword) ||
                    user.getFirstName().toLowerCase().contains(keyword) ||
                    user.getLastName().toLowerCase().contains(keyword)) {
                    filteredList.add(user);
                }
            }

            userTableView.setItems(filteredList);
        }
    }

    /**
     * Handles the update user action.
     */
    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to update.");
            return;
        }

        // Update user details
        selectedUser.setFirstName(firstNameField.getText());
        selectedUser.setLastName(lastNameField.getText());
        selectedUser.setDateOfBirth(dobPicker.getValue());

        showAlert(Alert.AlertType.INFORMATION, "Update Successful", "User details updated successfully.");
        userTableView.refresh();
    }

    /**
     * Handles the view all orders action.
     */
    @FXML
    private void handleViewOrders(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminViewOrders.fxml");
    }

    /**
     * Handles the back to dashboard action.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
    }

    /**
     * Loads sample user data into the table view.
     */
    private void loadUserData() {
    	 userList.add(new User("U001", "john_doe", "John", "Doe", LocalDate.of(1990, 5, 20)));
    	 userList.add(new User("U002", "jane_smith", "Jane", "Smith", LocalDate.of(1988, 10, 15)));
    	 userTableView.setItems(userList);
    }

    /**
     * Creates a NavigationManager dynamically based on the current stage.
     */
    private NavigationManager createNavigationManager(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return new NavigationManager(stage);
    }

    /**
     * Utility method to display alerts.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

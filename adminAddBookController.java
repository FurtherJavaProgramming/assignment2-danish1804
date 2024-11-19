package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Manager.NavigationManager;

public class adminAddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField physicalCopiesField;

    @FXML
    private CheckBox ebookAvailableCheckBox;

    @FXML
    private TextField physicalPriceField;

    @FXML
    private TextField ebookPriceField;

    @FXML
    private Button addBookButton;

    @FXML
    private Button cancelButton;

    /**
     * Handles the addition of a new book.
     */
    @FXML
    private void handleAddBook(ActionEvent event) {
        // Retrieve input values
        String title = titleField.getText();
        String author = authorField.getText();
        String physicalCopiesText = physicalCopiesField.getText();
        boolean isEbookAvailable = ebookAvailableCheckBox.isSelected();
        String physicalPriceText = physicalPriceField.getText();
        String ebookPriceText = ebookPriceField.getText();

        // Validate inputs
        if (title.isEmpty() || author.isEmpty() || physicalCopiesText.isEmpty() || physicalPriceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields except 'E-book Price' must be filled.");
            return;
        }

        int physicalCopies;
        double physicalPrice;
        double ebookPrice = 0.0;

        try {
            physicalCopies = Integer.parseInt(physicalCopiesText);
            physicalPrice = Double.parseDouble(physicalPriceText);

            if (isEbookAvailable && !ebookPriceText.isEmpty()) {
                ebookPrice = Double.parseDouble(ebookPriceText);
            } else if (isEbookAvailable && ebookPriceText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "E-book price must be provided if E-book is available.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Ensure 'Physical Copies', 'Physical Price', and 'E-book Price' are valid numbers.");
            return;
        }

        // Mock logic to add the book (Replace with database logic)
        System.out.println("Book added successfully:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Physical Copies: " + physicalCopies);
        System.out.println("E-book Available: " + isEbookAvailable);
        System.out.println("Physical Price: " + physicalPrice);
        System.out.println("E-book Price: " + ebookPrice);

        showAlert(Alert.AlertType.INFORMATION, "Success", "The book has been added successfully!");

        // Clear fields after success
        clearFields();
    }

    /**
     * Handles the cancellation action by navigating back to the admin dashboard.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_Files/adminViews/adminDashboard.fxml");
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        physicalCopiesField.clear();
        ebookAvailableCheckBox.setSelected(false);
        physicalPriceField.clear();
        ebookPriceField.clear();
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

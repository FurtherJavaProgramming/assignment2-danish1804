package application.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import application.DAO.OrderDAO;
import application.Manager.NavigationManager;
import application.Manager.SessionManager;
import application.Model.userOrder;

public class exportUserOrdersController {

    @FXML
    private ListView<String> ordersListView;

    @FXML
    private TextField fileNameField;

    @FXML
    private TextField destinationFolderField;

    @FXML
    private Button exportButton;

    @FXML
    private Button userDashboardButton;

    @FXML
    public void initialize() {
        // Fetch and display user orders
        String currentUserId = SessionManager.getInstance().getLoggedInUser().getUserId();
        List<userOrder> orders = OrderDAO.getOrdersForUser(currentUserId);

        if (orders.isEmpty()) {
            ordersListView.getItems().add("No orders found.");
        } else {
            for (userOrder order : orders) {
                ordersListView.getItems().add(
                    "Order #" + order.getOrderID() +
                    " - Date: " + order.getOrderDate() +
                    " - Total: $" + order.getTotalPrice()
                );
            }
        }
    }

    @FXML
    private void handleBrowseDestination(ActionEvent event) {
        // Open a directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination Folder");

        Stage stage = (Stage) ordersListView.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            destinationFolderField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleExport(ActionEvent event) {
        String fileName = fileNameField.getText().trim();
        String destinationFolder = destinationFolderField.getText().trim();

        if (fileName.isEmpty() || destinationFolder.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please provide a file name and select a destination folder.");
            return;
        }

        // Validate file name
        if (!fileName.matches("[a-zA-Z0-9._-]+")) {
            showAlert(Alert.AlertType.ERROR, "Invalid File Name", "File name contains invalid characters.");
            return;
        }

        File exportFile = new File(destinationFolder, fileName + ".txt");

        try (FileWriter writer = new FileWriter(exportFile)) {
            writer.write("Order Summary\n");
            writer.write("=====================\n");
            List<String> orders = ordersListView.getItems();

            for (String order : orders) {
                writer.write(order + "\n");
            }

            showAlert(Alert.AlertType.INFORMATION, "Export Successful", "Orders have been exported successfully to:\n" + exportFile.getAbsolutePath());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", "An error occurred while exporting the orders.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        // Navigate back to the user dashboard
        Stage stage = (Stage) userDashboardButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("userDashboard.fxml"); // Replace with the actual FXML file name
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

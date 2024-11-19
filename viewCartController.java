package application.Controller;

import application.Model.Book;
import application.Model.CartItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import application.Manager.*;

public class viewCartController {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, String> bookIdColumn;

    @FXML
    private TableColumn<CartItem, String> titleColumn;

    @FXML
    private TableColumn<CartItem, String> authorColumn;

    @FXML
    private TableColumn<CartItem, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<CartItem, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<CartItem, Double> totalCostColumn;

    @FXML
    private TableColumn<CartItem, Void> removeColumn;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Button continueShoppingButton;

    @FXML
    private Button checkOutButton;

    private ObservableList<CartItem> cartItems;

    @FXML
    public void initialize() {
        // Bind columns to CartItem properties
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getBookID()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getBookTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getAuthorName()));
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().ebookCopiesProperty().asObject());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());

        // Initialize mock cart items
        cartItems = FXCollections.observableArrayList(
                new CartItem(new Book("Java Programming", "John Doe", 10, true, 500.0, 300.0), 2, false),
                new CartItem(new Book("Data Structures", "Jane Smith", 5, true, 600.0, 400.0), 1, true)
        );
        cartTable.setItems(cartItems);

        // Add remove button to each row
        addRemoveButtonToTable();

        // Update total cost
        updateTotalCost();
    }

    private void addRemoveButtonToTable() {
        removeColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    CartItem cartItem = getTableView().getItems().get(getIndex());
                    cartItems.remove(cartItem); // Remove the item from the list
                    updateTotalCost(); // Update the total cost
                });
                removeButton.getStyleClass().add("remove-button"); // Optional styling
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
    }

    @FXML
    private void handleContinueShopping(ActionEvent event) {
        // Navigate to BrowseBooks.fxml
        Stage stage = (Stage) continueShoppingButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/browseBooks.fxml"); // Replace with actual FXML file name
    }

    @FXML
    private void handleCheckout(ActionEvent event) {
        // Navigate to Checkout.fxml
        Stage stage = (Stage) checkOutButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/checkoutScreen.fxml"); // Replace with actual FXML file name
    }

    private void updateTotalCost() {
        double totalCost = cartItems.stream().mapToDouble(CartItem::getTotalCost).sum();
        totalCostLabel.setText(String.format("$%.2f", totalCost));
    }
}

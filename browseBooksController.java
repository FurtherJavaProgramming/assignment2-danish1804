package application.Controller;

import application.DAO.BookDAO;
import application.Manager.NavigationManager;
import application.Model.Book;
import application.Model.CartItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class browseBooksController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<Book, Boolean> ebookColumn;

    @FXML
    private TableColumn<Book, Double> physicalPriceColumn;

    @FXML
    private TableColumn<Book, Double> ebookPriceColumn;

    @FXML
    private TableColumn<Book, HBox> addToCartColumn;

    @FXML
    private Button viewCartButton;

    private ObservableList<Book> books;
    private HashMap<String, Integer> cartQuantities = new HashMap<>();

    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList(); // Shared cart list

    @FXML
    public void initialize() {
        // Bind columns to Book properties
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorNameProperty());
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfPhysicalCopiesProperty().asObject());
        ebookColumn.setCellValueFactory(cellData -> cellData.getValue().hasEbookProperty().asObject());
        physicalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().physicalPriceProperty().asObject());
        ebookPriceColumn.setCellValueFactory(cellData -> cellData.getValue().ebookPriceProperty().asObject());
        addToCartColumn.setCellValueFactory(cellData -> createAddToCartButton(cellData.getValue()));

        // Load books from the database
        loadBooksFromDatabase();
    }

    private ObservableValue<HBox> createAddToCartButton(Book book) {
    	Button addToCartButton = new Button("Add to Cart");
        TextField quantityField = new TextField("0");
        quantityField.setPrefWidth(50);
        Label quantityLabel = new Label();

        // Styling for quantityField and quantityLabel
        quantityField.setStyle("-fx-padding: 5px; -fx-background-color: white; -fx-border-color: lightgray;");
        quantityLabel.setStyle("-fx-padding: 5px; -fx-background-color: lightgray; -fx-border-radius: 5px;");

        // Initialize quantity label and field
        updateCartActions(addToCartButton, quantityField, quantityLabel, book);

        addToCartButton.setOnAction(event -> {
            handleAddToCart(book, 1); // Increment by 1
            updateCartActions(addToCartButton, quantityField, quantityLabel, book);
        });

        quantityField.setOnAction(event -> {
            try {
                int enteredQuantity = Integer.parseInt(quantityField.getText());
                handleAddToCart(book, enteredQuantity - cartQuantities.getOrDefault(book.getBookID(), 0));
                updateCartActions(addToCartButton, quantityField, quantityLabel, book);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number.");
            }
        });

        // Wrap actions in an HBox
        HBox actionsContainer = new HBox(5, addToCartButton, quantityField, quantityLabel);
        return new SimpleObjectProperty<>(actionsContainer);
    }

    private void loadBooksFromDatabase() {
        try {
            List<Book> bookList = BookDAO.fetchAllBooks(); // Fetch books from the database
            books = FXCollections.observableArrayList(bookList);
            bookTable.setItems(books);
        } catch (Exception e) {
            showError("Error Loading Books", "Unable to load books from the database. Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();

        for (Book book : books) {
            if (book.getBookTitle().toLowerCase().contains(keyword) || 
                book.getAuthorName().toLowerCase().contains(keyword)) {
                filteredBooks.add(book);
            }
        }

        bookTable.setItems(filteredBooks);
    }

    private void handleAddToCart(Book book, int quantityToAdd) {
        if (quantityToAdd <= 0) return;

        cartQuantities.put(book.getBookID(), cartQuantities.getOrDefault(book.getBookID(), 0) + quantityToAdd);

        // Check if the book is already in the cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getBook().equals(book)) {
                cartItem.addPhysicalCopies(quantityToAdd);
                return;
            }
        }

        // If not in cart, create a new CartItem
        cartItems.add(new CartItem(book, quantityToAdd, false));
    }
    
    
    private void updateCartActions(Button addToCartButton, TextField quantityField, Label quantityLabel, Book book) {
        int quantity = cartQuantities.getOrDefault(book.getBookID(), 0);
        addToCartButton.setText(quantity > 0 ? "Add More" : "Add to Cart");
        quantityField.setText(String.valueOf(quantity));
        quantityLabel.setText("In Cart: " + quantity);
    }

    @FXML
    private void handleViewCart(ActionEvent event) {
        // Navigate to the View Cart screen
        Stage stage = (Stage) viewCartButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/viewCart.fxml"); // Replace with the actual FXML file name
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

package application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import application.Manager.NavigationManager;
import application.Model.Book;

public class adminViewBookStockController {

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<Book, Boolean> ebookAvailableColumn;

    @FXML
    private TableColumn<Book, Double> physicalPriceColumn;

    @FXML
    private TableColumn<Book, Double> ebookPriceColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button editBookButton;

    @FXML
    private Button deleteBookButton;

    @FXML
    private Button backToDashboardButton;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns with data from the Book model
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPhysicalCopies"));
        ebookAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("hasEbook"));
        physicalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("physicalPrice"));
        ebookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ebookPrice"));

        // Load initial data
        loadBookData();
    }

    /**
     * Loads mock book data into the table for testing purposes.
     */
    private void loadBookData() {
        bookList.clear();
        bookList.add(new Book("Java Programming", "John Doe", 10, true, 25.99, 19.99));
        bookList.add(new Book("Python Basics", "Jane Smith", 15, true, 30.99, 25.99));
        bookList.add(new Book("Data Structures", "Alice Brown", 5, false, 20.99, 0.0));
        bookTableView.setItems(bookList);
    }

    /**
     * Handles the refresh button click event to reload book data.
     */
    @FXML
    private void handleRefresh(ActionEvent event) {
        loadBookData();
        showAlert(Alert.AlertType.INFORMATION, "Refresh Complete", "Book stock data has been refreshed.");
    }

    /**
     * Handles adding a new book to the table.
     */
    @FXML
    private void handleAddBook(ActionEvent event) {
    	
    	NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminAddBook.fxml");
        showAlert(Alert.AlertType.INFORMATION, "Add Book", "Add book functionality successfully implemented.");
        // Placeholder for adding book logic
    }

    /**
     * Handles editing the selected book in the table.
     */
    @FXML
    private void handleEditBook(ActionEvent event) {
    	NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminUpdateBookStock.fxml");
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            showAlert(Alert.AlertType.INFORMATION, "Edit Book", "Editing book: " + selectedBook.getBookTitle());
            // Placeholder for editing book logic
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to edit.");
        }
    }

    /**
     * Handles deleting the selected book from the table.
     */
    @FXML
    private void handleDeleteBook(ActionEvent event) {
    	NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminRemoveBook.fxml");
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookList.remove(selectedBook);
            showAlert(Alert.AlertType.INFORMATION, "Delete Book", "Book '" + selectedBook.getBookTitle() + "' has been deleted.");
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to delete.");
        }
    }

    /**
     * Handles the back button to navigate to the admin dashboard.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        NavigationManager navigationManager = createNavigationManager(event);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
    }

    /**
     * Creates a NavigationManager dynamically based on the current Stage.
     */
    private NavigationManager createNavigationManager(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        return new NavigationManager(stage);
    }

    /**
     * Displays an alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

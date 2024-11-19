package application.Controller;

import application.Manager.NavigationManager;
import application.Model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class adminUpdateBookStockController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Book> bookComboBox;

    @FXML
    private TextField physicalCopiesField;

    @FXML
    private CheckBox ebookAvailableCheckBox;

    @FXML
    private TextField physicalPriceField;

    @FXML
    private TextField ebookPriceField;

    @FXML
    private Label validationMessage;

    @FXML
    private Button updateButton;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Load books into ComboBox (mock data or real data from the database)
        loadBooks();

        // Add listener for validation on inputs
        physicalCopiesField.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        physicalPriceField.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        ebookPriceField.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());

        // Set up ComboBox listener to populate fields on selection
        bookComboBox.valueProperty().addListener((observable, oldValue, selectedBook) -> {
            if (selectedBook != null) {
                populateBookDetails(selectedBook);
            }
        });

        // Initially disable the Update button
        updateButton.setDisable(true);
    }

    private void loadBooks() {
        // Mock data; replace with actual data retrieval logic
        books.add(new Book("Book 1", "Author 1", 10, true, 50.0, 30.0));
        books.add(new Book("Book 2", "Author 2", 5, false, 40.0, 0.0));
        books.add(new Book("Book 3", "Author 3", 7, true, 35.0, 20.0));
        bookComboBox.setItems(books);
    }

    private void populateBookDetails(Book book) {
        physicalCopiesField.setText(String.valueOf(book.getNumberOfPhysicalCopies()));
        ebookAvailableCheckBox.setSelected(book.isHasEbook());
        physicalPriceField.setText(String.valueOf(book.getPhysicalPrice()));
        ebookPriceField.setText(String.valueOf(book.getEbookPrice()));
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            validationMessage.setText("Please enter a search keyword.");
            return;
        }

        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        for (Book book : books) {
            if (book.getBookTitle().toLowerCase().contains(keyword) || book.getAuthorName().toLowerCase().contains(keyword)) {
                filteredBooks.add(book);
            }
        }

        if (filteredBooks.isEmpty()) {
            validationMessage.setText("No books found matching the keyword.");
        } else {
            validationMessage.setText("");
            bookComboBox.setItems(filteredBooks);
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Book selectedBook = bookComboBox.getValue();
        if (selectedBook == null) {
            validationMessage.setText("Please select a book to update.");
            return;
        }

        try {
            int physicalCopies = Integer.parseInt(physicalCopiesField.getText());
            double physicalPrice = Double.parseDouble(physicalPriceField.getText());
            double ebookPrice = Double.parseDouble(ebookPriceField.getText());
            boolean hasEbook = ebookAvailableCheckBox.isSelected();

            selectedBook.setNumberOfPhysicalCopies(physicalCopies);
            selectedBook.setPhysicalPrice(physicalPrice);
            selectedBook.setEbookPrice(ebookPrice);
            selectedBook.setHasEbook(hasEbook);

            validationMessage.setText("Book details updated successfully.");
        } catch (NumberFormatException e) {
            validationMessage.setText("Invalid input! Please check the numbers.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            NavigationManager navigationManager = new NavigationManager(stage);
            navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
        }
    }

    private void validateInputs() {
        String physicalCopies = physicalCopiesField.getText();
        String physicalPrice = physicalPriceField.getText();
        String ebookPrice = ebookPriceField.getText();

        boolean valid = true;

        try {
            if (!physicalCopies.isEmpty()) Integer.parseInt(physicalCopies);
            if (!physicalPrice.isEmpty()) Double.parseDouble(physicalPrice);
            if (!ebookPrice.isEmpty()) Double.parseDouble(ebookPrice);
        } catch (NumberFormatException e) {
            valid = false;
        }

        updateButton.setDisable(!valid);
        validationMessage.setText(valid ? "" : "Please enter valid numbers.");
    }
}

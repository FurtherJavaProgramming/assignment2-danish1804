package application.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import application.Manager.NavigationManager;
import application.Model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class adminRemoveBooksController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Boolean> selectColumn;

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

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure columns
        configureColumns();

        // Load initial book data
        loadBookData();

        // Bind table data
        bookTableView.setItems(bookList);
    }

    private void configureColumns() {
        // Configure selection column with CheckBox
        selectColumn.setCellValueFactory(param -> {
            BooleanProperty selected = new SimpleBooleanProperty();
            selected.addListener((obs, wasSelected, isSelected) -> param.getValue().setSelected(isSelected));
            return selected;
        });
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));

        // Configure other columns
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPhysicalCopies"));
        ebookAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("hasEbook"));
    }

    private void loadBookData() {
        // Mock data for demonstration (replace with actual data from database or service)
        bookList.addAll(
                new Book("Book One", "Author A", 5, true, 25.0, 15.0),
                new Book("Book Two", "Author B", 10, false, 30.0, 0.0),
                new Book("Book Three", "Author C", 7, true, 20.0, 10.0)
        );
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            bookTableView.setItems(bookList); // Reset to full list if search field is empty
        } else {
            ObservableList<Book> filteredList = bookList.stream()
                    .filter(book -> book.getBookTitle().toLowerCase().contains(keyword) ||
                                    book.getAuthorName().toLowerCase().contains(keyword))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            bookTableView.setItems(filteredList);
        }
    }

    @FXML
    private void handleRemoveSelected() {
        // Collect selected books
        List<Book> selectedBooks = bookList.stream()
                .filter(Book::isSelected)
                .collect(Collectors.toList());

        if (selectedBooks.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select books to remove.");
            return;
        }

        // Confirm removal
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Removal");
        confirmationAlert.setHeaderText("You are about to remove the selected books.");
        confirmationAlert.setContentText("Do you want to proceed?");
        if (confirmationAlert.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            bookList.removeAll(selectedBooks);
            bookTableView.refresh(); // Update the table view
            showAlert(Alert.AlertType.INFORMATION, "Success", "Selected books have been removed.");
        }
    }

    @FXML
    private void handleBack() {
        // Navigate back to the admin dashboard
        Stage stage = (Stage) bookTableView.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

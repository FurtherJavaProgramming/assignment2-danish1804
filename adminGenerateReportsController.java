package application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import application.Manager.NavigationManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class adminGenerateReportsController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea salesReportArea;

    @FXML
    private TextField topBooksCountField;

    @FXML
    private TableView<BookReport> topSellingBooksTable;

    @FXML
    private TableColumn<BookReport, Integer> rankColumn;

    @FXML
    private TableColumn<BookReport, String> bookTitleColumn;

    @FXML
    private TableColumn<BookReport, String> authorColumn;

    @FXML
    private TableColumn<BookReport, Integer> copiesSoldColumn;

    private NavigationManager navigationManager;

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        // Initialize the table columns
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesSoldColumn.setCellValueFactory(new PropertyValueFactory<>("copiesSold"));

        // Set the placeholder for the table
        topSellingBooksTable.setPlaceholder(new Label("No data available. Generate the report to view results."));
    }

    /**
     * Handle generating the sales report.
     */
    @FXML
    private void handleGenerateSalesReport() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            showError("Invalid Input", "Please select both start and end dates.");
            return;
        }

        if (startDate.isAfter(endDate)) {
            showError("Invalid Date Range", "Start date cannot be after end date.");
            return;
        }

        // Mock data for the sales report (Replace with real data retrieval logic)
        StringBuilder report = new StringBuilder();
        report.append("Sales Report from ").append(startDate).append(" to ").append(endDate).append("\n\n");
        report.append("1. Book: Effective Java | Copies Sold: 15 | Revenue: $450.00\n");
        report.append("2. Book: Clean Code      | Copies Sold: 10 | Revenue: $300.00\n");
        report.append("3. Book: Java Concurrency in Practice | Copies Sold: 5 | Revenue: $125.00\n");

        salesReportArea.setText(report.toString());
    }

    /**
     * Handle generating the top-selling books report.
     */
    @FXML
    private void handleGenerateTopSellingReport() {
        String input = topBooksCountField.getText();
        int topCount;

        try {
            topCount = Integer.parseInt(input);
            if (topCount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter a valid positive integer for the number of top books.");
            return;
        }

        // Mock data for the top-selling books report (Replace with real data retrieval logic)
        List<BookReport> reportData = new ArrayList<>();
        reportData.add(new BookReport(1, "Effective Java", "Joshua Bloch", 15));
        reportData.add(new BookReport(2, "Clean Code", "Robert C. Martin", 10));
        reportData.add(new BookReport(3, "Java Concurrency in Practice", "Brian Goetz", 5));

        // Update the table with the report data
        ObservableList<BookReport> tableData = FXCollections.observableArrayList(reportData.subList(0, Math.min(topCount, reportData.size())));
        topSellingBooksTable.setItems(tableData);
    }

    /**
     * Handle navigation back to the admin dashboard.
     */
    @FXML
    private void handleBack() {
        Stage stage = (Stage) topBooksCountField.getScene().getWindow();
        navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
    }

    /**
     * Show an error dialog.
     *
     * @param title   The title of the dialog.
     * @param message The message to display.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Book Report class for TableView data.
     */
    public static class BookReport {
        private final Integer rank;
        private final String bookTitle;
        private final String author;
        private final Integer copiesSold;

        public BookReport(Integer rank, String bookTitle, String author, Integer copiesSold) {
            this.rank = rank;
            this.bookTitle = bookTitle;
            this.author = author;
            this.copiesSold = copiesSold;
        }

        public Integer getRank() {
            return rank;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getAuthor() {
            return author;
        }

        public Integer getCopiesSold() {
            return copiesSold;
        }
    }
}

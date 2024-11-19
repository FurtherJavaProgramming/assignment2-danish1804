package application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.Manager.NavigationManager;

public class adminSalesReportController {

    @FXML
    private ComboBox<String> reportTypeSelector;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField filterField;

    @FXML
    private ComboBox<String> copyTypeSelector;

    @FXML
    private TableView<ReportRow> reportTableView;

    @FXML
    private Label errorMessage;
    @FXML
    private Button backToDashboardButton;
    @FXML
    private VBox root;

    @FXML
    public void initialize() {
        // Populate the Report Type ComboBox
        ObservableList<String> reportTypes = FXCollections.observableArrayList(
                "Sales by Date Range", "Sales by Book", "Sales by Author", "Sales by Category"
        );
        reportTypeSelector.setItems(reportTypes);

        // Populate the Copy Type Selector
        ObservableList<String> copyTypes = FXCollections.observableArrayList(
                "All Copies", "Physical Copies Only", "Ebooks Only"
        );
        copyTypeSelector.setItems(copyTypes);

        // Initialize TableView
        setupTableView();

        // Clear any previous error messages
        errorMessage.setVisible(false);
    }

    private void setupTableView() {
        // Dynamically create columns for the TableView
        TableColumn<ReportRow, String> column1 = new TableColumn<>("Book Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<ReportRow, Integer> column2 = new TableColumn<>("Copies Sold");
        column2.setCellValueFactory(new PropertyValueFactory<>("copiesSold"));

        TableColumn<ReportRow, Double> column3 = new TableColumn<>("Revenue");
        column3.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        reportTableView.getColumns().addAll(column1, column2, column3);
    }

    @FXML
    private void handleGenerateReport() {
        errorMessage.setVisible(false); // Clear previous error

        // Validate inputs
        if (reportTypeSelector.getValue() == null) {
            displayError("Please select a report type.");
            return;
        }

        String selectedReportType = reportTypeSelector.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String filter = filterField.getText();
        String copyType = copyTypeSelector.getValue();

        // Validate Date Range for "Sales by Date Range" Report
        if (selectedReportType.equals("Sales by Date Range")) {
            if (startDate == null || endDate == null) {
                displayError("Please select both start and end dates.");
                return;
            }
            if (startDate.isAfter(endDate)) {
                displayError("Start date cannot be after end date.");
                return;
            }
        }

        // Fetch the data based on the report type
        List<ReportRow> reportData = generateReportData(selectedReportType, startDate, endDate, filter, copyType);

        // Update TableView with new data
        ObservableList<ReportRow> observableData = FXCollections.observableArrayList(reportData);
        reportTableView.setItems(observableData);

        if (reportData.isEmpty()) {
            displayError("No data found for the selected criteria.");
        }
    }

    private List<ReportRow> generateReportData(String reportType, LocalDate startDate, LocalDate endDate, String filter, String copyType) {
        // Replace this mock data with actual data fetching logic from your database or service
        List<ReportRow> mockData = new ArrayList<>();
        if ("Sales by Book".equals(reportType)) {
            mockData.add(new ReportRow("Book A", 50, 500.0));
            mockData.add(new ReportRow("Book B", 30, 300.0));
        } else if ("Sales by Date Range".equals(reportType)) {
            mockData.add(new ReportRow("Book C", 20, 200.0));
        }
        return mockData;
    }
    
    @FXML
    private void handleBack() {
        // Navigate back to the admin dashboard
        Stage stage = (Stage) backToDashboardButton.getScene().getWindow();
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateTo("/application/FXML_FIles/adminViews/adminDashboard.fxml");
    }

    private void displayError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    // Inner class for representing rows in the TableView
    public static class ReportRow {
        private final String bookTitle;
        private final int copiesSold;
        private final double revenue;

        public ReportRow(String bookTitle, int copiesSold, double revenue) {
            this.bookTitle = bookTitle;
            this.copiesSold = copiesSold;
            this.revenue = revenue;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public int getCopiesSold() {
            return copiesSold;
        }

        public double getRevenue() {
            return revenue;
        }
    }
}

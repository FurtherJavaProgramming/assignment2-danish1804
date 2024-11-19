package application.Manager;


import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class ApplicationManager {
    private Stage primaryStage;
    private NavigationManager navigationManager;

    // Constructor
    public ApplicationManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        // Set up the root container and initial scene
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        // Attach the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Reading Room");

        // Initialize NavigationManager
        navigationManager = new NavigationManager(primaryStage);

        // Navigate to the login screen
        navigationManager.navigateTo("/application/FXML_FIles/loginPage.fxml");

        // Show the stage
        primaryStage.show();
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }
}

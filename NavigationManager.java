package application.Manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class NavigationManager {
    private Stage stage;

    // Shared parameter storage for passing data between screens
    private static Map<String, Object> parameters = new HashMap<>();

    // Constructor to initialize the stage
    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    // Method to navigate to a new FXML page
    public void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = getLoader(fxmlPath);
            Parent root = loader.load();
            updateScene(root);
        } catch (Exception e) {
            System.err.println("Failed to navigate to: " + fxmlPath);
            e.printStackTrace();
        }
    }

    // Method to navigate to a page and pass parameters
    public void navigateToWithParameter(String fxmlPath, Map<String, Object> params) {
        parameters.clear();
        if (params != null) {
            parameters.putAll(params);
        }
        navigateTo(fxmlPath);
    }

    // Method to get a specific parameter
    public static Object getParameter(String key) {
        return parameters.get(key);
    }

    // Method to pass data and return the controller for interaction
    public <T> T navigateToWithController(String fxmlPath) {
        try {
            FXMLLoader loader = getLoader(fxmlPath);
            Parent root = loader.load();
            updateScene(root);
            return loader.getController(); // Returns the controller for further interaction
        } catch (Exception e) {
            System.err.println("Failed to navigate to with controller: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to get an FXMLLoader
    private FXMLLoader getLoader(String fxmlPath) throws Exception {
        return new FXMLLoader(getClass().getResource(fxmlPath));
    }

    // Helper method to update the scene
    private void updateScene(Parent root) {
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.show();
    }
}

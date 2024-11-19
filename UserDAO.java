package application.DAO;

import application.Manager.DatabaseManager;
import application.Model.User;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

    private static final String TABLE_NAME = "Users";
    private static final String INSERT_USER_SQL =
            "INSERT INTO " + TABLE_NAME + " (userId, username, firstName, lastName, dateOfBirth, role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FETCH_USER_SQL =
            "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
    private static final String UPDATE_USER_SQL =
            "UPDATE " + TABLE_NAME + " SET firstName = ?, lastName = ?, dateOfBirth = ?, role = ? WHERE userId = ?";
    private static final String DELETE_USER_SQL =
            "DELETE FROM " + TABLE_NAME + " WHERE userId = ?";

    /**
     * Registers a new user in the database.
     *
     * @param user The User object to register.
     * @return true if the registration was successful, false otherwise.
     */
    public static boolean registerUser(User user) {
        String generatedUserId = generateUserId();
        user.setUserId(generatedUserId);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getDateOfBirth().toString());
            stmt.setString(6, user.roleProperty().get());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error while registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches a user from the database by username.
     *
     * @param username The username to search for.
     * @return The User object, or null if not found.
     */
    public static User fetchUserByUsername(String username) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FETCH_USER_SQL)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("userId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                String role = rs.getString("role");

                User user = new User(username, firstName, lastName, dateOfBirth);
                user.setUserId(userId);
                user.setRole(role);
                return user;
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching user: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates user details in the database.
     *
     * @param user The updated User object.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateUser(User user) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_SQL)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getDateOfBirth().toString());
            stmt.setString(4, user.roleProperty().get());
            stmt.setString(5, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error while updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The ID of the user to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public static boolean deleteUser(String userId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER_SQL)) {

            stmt.setString(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if the given password matches the password stored in the database for the given userId.
     * 
     * @param userId The ID of the user.
     * @param inputPassword The password provided for verification.
     * @return true if the password matches, false otherwise.
     */
    /**
     * Verifies if the provided username and password match a user in the database.
     *
     * @param username The username to validate.
     * @param inputPassword The password provided for validation.
     * @return true if the credentials are valid, false otherwise.
     */
    public static boolean verifyPasswordByUsername(String username, String inputPassword) {
        String query = "SELECT password FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the username parameter
            stmt.setString(1, username);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Fetch the stored hashed password
                String storedHashedPassword = rs.getString("password");

                // Compare the hashed input password with the stored hashed password
                // Replace hashPassword with your hashing mechanism
                return storedHashedPassword.equals(hashPassword(inputPassword));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * A mock hashing function for demonstration purposes.
     * Replace this with your actual password hashing function, e.g., BCrypt, SHA-256, etc.
     * 
     * @param password The plain-text password to hash.
     * @return The hashed password.
     */
    private static String hashPassword(String password) {
        // Replace this logic with the real password hashing algorithm
        return Integer.toHexString(password.hashCode());
    }

    /**
     * Generates a unique user ID.
     *
     * @return A unique user ID.
     */
    private static String generateUserId() {
        long timestamp = System.currentTimeMillis();
        int randomSuffix = (int) (Math.random() * 1000); // Add some randomness
        return "USER-" + timestamp + "-" + randomSuffix;
    }
}

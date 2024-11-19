package application.DAO;

import application.Manager.DatabaseManager;
import application.Model.userOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // SQL queries
    private static final String INSERT_ORDER_SQL = 
        "INSERT INTO Orders (orderID, bookID, bookName, orderDate, physicalCopies, ebookCopies, totalItems, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FETCH_ORDER_SQL = 
        "SELECT * FROM Orders WHERE orderID = ?";
    private static final String FETCH_ALL_ORDERS_SQL = 
        "SELECT * FROM Orders";
    private static final String UPDATE_ORDER_SQL = 
        "UPDATE Orders SET bookID = ?, bookName = ?, orderDate = ?, physicalCopies = ?, ebookCopies = ?, totalItems = ?, totalPrice = ? WHERE orderID = ?";
    private static final String DELETE_ORDER_SQL = 
        "DELETE FROM Orders WHERE orderID = ?";
    private static final String GET_ORDERS_FOR_USER_SQL =
            "SELECT * FROM Orders WHERE userId = ?";
    /**
     * Adds a new order to the database.
     * @param order The userOrder object to add.
     * @return true if the order was added successfully, false otherwise.
     */
    public static boolean addOrder(userOrder order) {
    	String generateOrderId = generateOrderId(order.getOrderDate());
        order.setOrderID(generateOrderId);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER_SQL)) {

            stmt.setString(1, order.getOrderID());
            stmt.setString(2, order.getBookID());
            stmt.setString(3, order.getBookName());
            stmt.setString(4, order.getOrderDate());
            stmt.setInt(5, order.getPhysicalCopies());
            stmt.setInt(6, order.getEbookCopies());
            stmt.setInt(7, order.getTotalItems());
            stmt.setDouble(8, order.getTotalPrice());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
        	System.err.println("Error while adding new Order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches a specific order by its orderID.
     * @param orderID The ID of the order to fetch.
     * @return The userOrder object if found, otherwise null.
     */
    public static userOrder fetchOrderById(String orderID) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FETCH_ORDER_SQL)) {

            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches all orders from the database.
     * @return A list of userOrder objects.
     */
    public static List<userOrder> fetchAllOrders() {
        List<userOrder> orders = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FETCH_ALL_ORDERS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Updates an existing order in the database.
     * @param order The updated userOrder object.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateOrder(userOrder order) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ORDER_SQL)) {

            stmt.setString(1, order.getBookID());
            stmt.setString(2, order.getBookName());
            stmt.setString(3, order.getOrderDate());
            stmt.setInt(4, order.getPhysicalCopies());
            stmt.setInt(5, order.getEbookCopies());
            stmt.setInt(6, order.getTotalItems());
            stmt.setDouble(7, order.getTotalPrice());
            stmt.setString(8, order.getOrderID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an order from the database.
     * @param orderID The ID of the order to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public static boolean deleteOrder(String orderID) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_ORDER_SQL)) {

            stmt.setString(1, orderID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Fetches all orders for a specific user by their user ID.
     *
     * @param userId The ID of the user whose orders are to be fetched.
     * @return A list of userOrder objects representing the user's orders.
     */
    public static List<userOrder> getOrdersForUser(String userId) {
        List<userOrder> orders = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ORDERS_FOR_USER_SQL)) {

            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bookID = rs.getString("bookId");
                    String bookName = rs.getString("bookName");
                    String orderDate = rs.getString("orderDate");
                    int physicalCopies = rs.getInt("physicalCopies");
                    int ebookCopies = rs.getInt("ebookCopies");
                    double totalPrice = rs.getDouble("totalPrice");

                    // Initialize the userOrder without orderID
                    userOrder order = new userOrder(bookID, bookName, orderDate, physicalCopies, ebookCopies, totalPrice);

                    // Set the orderID explicitly
                    order.setOrderID(rs.getString("orderId"));

                    // Add to the list
                    orders.add(order);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching orders for user: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Maps a ResultSet row to a userOrder object.
     * @param rs The ResultSet containing order data.
     * @return A userOrder object.
     * @throws SQLException if a database access error occurs.
     */
    private static userOrder mapResultSetToOrder(ResultSet rs) throws SQLException {
        String orderID = rs.getString("orderID");
        String bookID = rs.getString("bookID");
        String bookName = rs.getString("bookName");
        String orderDate = rs.getString("orderDate");
        int physicalCopies = rs.getInt("physicalCopies");
        int ebookCopies = rs.getInt("ebookCopies");
        double totalPrice = rs.getDouble("totalPrice");
        
        userOrder newOrder = new userOrder(bookID, bookName, orderDate, physicalCopies, ebookCopies, totalPrice);
        newOrder.setOrderID(orderID);
        return newOrder;
    }
        
      private static String generateOrderId(String bookName) {
    	    String initials = bookName.replaceAll("[^A-Za-z]", "").toUpperCase();
    	    long timestamp = System.currentTimeMillis();
    	    return "ORD-" + (int) (Math.random() * 100000)+initials + "-" + timestamp;

    	 
    }
}

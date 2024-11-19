package application.DAO;

import application.Manager.DatabaseManager;
import application.Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private static final String TABLE_NAME = "Books";
    private static final String INSERT_BOOK_SQL = 
        "INSERT INTO " + TABLE_NAME + " (bookID, bookTitle, authorName, numberOfPhysicalCopies, hasEbook, physicalPrice, ebookPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FETCH_BOOK_SQL = 
        "SELECT * FROM " + TABLE_NAME + " WHERE bookID = ?";
    private static final String FETCH_ALL_BOOKS_SQL = 
        "SELECT * FROM " + TABLE_NAME;
    private static final String UPDATE_BOOK_SQL = 
        "UPDATE " + TABLE_NAME + " SET bookTitle = ?, authorName = ?, numberOfPhysicalCopies = ?, hasEbook = ?, physicalPrice = ?, ebookPrice = ? WHERE bookID = ?";
    private static final String DELETE_BOOK_SQL = 
        "DELETE FROM " + TABLE_NAME + " WHERE bookID = ?";

    /**
     * Adds a new book to the database.
     *
     * @param book The Book object to add.
     * @return true if the book was added successfully, false otherwise.
     */
    public static boolean addBook(Book book) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BOOK_SQL)) {

            stmt.setString(1, book.getBookID());
            stmt.setString(2, book.getBookTitle());
            stmt.setString(3, book.getAuthorName());
            stmt.setInt(4, book.getNumberOfPhysicalCopies());
            stmt.setBoolean(5, book.isHasEbook());
            stmt.setDouble(6, book.getPhysicalPrice());
            stmt.setDouble(7, book.getEbookPrice());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches a book by its ID from the database.
     *
     * @param bookID The ID of the book to fetch.
     * @return The Book object, or null if not found.
     */
    public static Book fetchBookById(String bookID) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FETCH_BOOK_SQL)) {

            stmt.setString(1, bookID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBook(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching book: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    

    /**
     * Fetches all books from the database.
     *
     * @return A list of Book objects.
     */
    public static List<Book> fetchAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FETCH_ALL_BOOKS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all books: " + e.getMessage());
            e.printStackTrace();
        }

        return books;
    }
    
    public static List<Book> searchBooks(String keyword) throws SQLException {
        String query = "SELECT * FROM Books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }


    /**
     * Updates a book in the database.
     *
     * @param book The updated Book object.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateBook(Book book) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_BOOK_SQL)) {

            stmt.setString(1, book.getBookTitle());
            stmt.setString(2, book.getAuthorName());
            stmt.setInt(3, book.getNumberOfPhysicalCopies());
            stmt.setBoolean(4, book.isHasEbook());
            stmt.setDouble(5, book.getPhysicalPrice());
            stmt.setDouble(6, book.getEbookPrice());
            stmt.setString(7, book.getBookID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error while updating book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a book from the database.
     *
     * @param bookID The ID of the book to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public static boolean deleteBook(String bookID) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BOOK_SQL)) {

            stmt.setString(1, bookID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error while deleting book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a Book object.
     *
     * @param rs The ResultSet containing book data.
     * @return A Book object.
     * @throws SQLException if an error occurs while reading the ResultSet.
     */
    private static Book mapResultSetToBook(ResultSet rs) throws SQLException {
        String bookID = rs.getString("bookID");
        String bookTitle = rs.getString("bookTitle");
        String authorName = rs.getString("authorName");
        int physicalCopies = rs.getInt("numberOfPhysicalCopies");
        boolean hasEbook = rs.getBoolean("hasEbook");
        double physicalPrice = rs.getDouble("physicalPrice");
        double ebookPrice = rs.getDouble("ebookPrice");

        Book book = new Book(bookTitle, authorName, physicalCopies, hasEbook, physicalPrice, ebookPrice);
        book.setBookID(bookID);

        return book;
    }

    /**
     * Generates a unique book ID based on the author's name.
     *
     * @param authorName The name of the author.
     * @return A unique book ID.
     */
   
}

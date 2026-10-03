package util;

/**
 * DataAccessException - Wraps persistence failures at the repository boundary.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String operation, Exception cause) {
        super("Database operation failed: " + operation, cause);
    }
}
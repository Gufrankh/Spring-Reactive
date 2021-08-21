package bookstore.exception;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 05-08-2021 20:13
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

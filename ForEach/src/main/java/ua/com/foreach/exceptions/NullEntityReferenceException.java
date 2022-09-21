package ua.com.foreach.exceptions;


public class NullEntityReferenceException extends RuntimeException{
    public NullEntityReferenceException() {
        super();
    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}

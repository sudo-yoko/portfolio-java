package main.java.com.example.domain;
public class ClipException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClipException(String message) {
        super(message);
    }
}
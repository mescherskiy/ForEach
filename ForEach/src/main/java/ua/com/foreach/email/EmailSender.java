package ua.com.foreach.email;

public interface EmailSender {
    void send(String to, String email, String subject);
}

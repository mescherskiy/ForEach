package ua.com.foreach.services;

public interface EmailSender {
    void send(String to, String email, String subject);
}

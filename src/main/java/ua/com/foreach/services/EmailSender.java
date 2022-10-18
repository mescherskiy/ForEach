package ua.com.foreach.services;

public interface EmailSender {
    void send(String to, String email, String subject);

    String buildEmailWithLink(String name, String mailCaption, String mailText, String link);

    String buildEmailWithoutLink(String name, String mailCaption, String mailText);
}

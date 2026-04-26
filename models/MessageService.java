package models;

public class MessageService {

    public void sendMessage(User from, User to, String text) {
        if (from == null || to == null) return;

        Message message = new Message(from, to, text);
        System.out.println("Message sent: " + message.getText());
    }
}
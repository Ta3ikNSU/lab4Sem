import javax.naming.Name;

public class Message {
    public enum MessageType{
        LOGIN, MESSAGE, INFO, LOGOUT;
    }

    MessageType type;
    String body;
    String name;

    public Message(MessageType type, String body, String name) {
        this.type = type;
        this.body = body;
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }
}

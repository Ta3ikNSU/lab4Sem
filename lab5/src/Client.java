public class Client {
    public static String ip = "localhost";
    public static final int PORT  = 3443;
    public static void main(String[] args){
        new ClientHandler(ip, PORT);
    }
}

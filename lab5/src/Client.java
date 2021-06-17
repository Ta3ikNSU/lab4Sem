public class Client {
    public static String ip = "84.237.55.198";
    public static final int PORT  = 8080;
    public static void main(String[] args){
        new ClientHandler(ip, PORT);
    }
}

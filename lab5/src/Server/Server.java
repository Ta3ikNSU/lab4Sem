package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static final int PORT = 8888;
    private static int connectToLastGame = 0;
    public static ArrayList<Game> games  = new ArrayList<>();

    public static void main(String[] args) {
        new Server().start();
    }

    private void start(){
        try (ServerSocket server = new ServerSocket(PORT)) {
            if(connectToLastGame == 0){
                Socket socket = server.accept();
                games.add(new Game(this, server));
                connectToLastGame = 1;
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

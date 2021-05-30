package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Game{
    private ConnectionHandler [] connectionHandlers= new ConnectionHandler[2];

    private Model model;

    private Server server;
    private ServerSocket serverSocket;

    private int connectedPlayers;

    public Game(Server server, ServerSocket serverSocket){
        this.server = server;
        this.serverSocket = serverSocket;
    }

    public void startGame(){
        for(int i = 0; i < 2; i++){

        }
    }
}

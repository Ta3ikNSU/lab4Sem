package Server;

import mvc.Sign;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Sign sign;
    private Game game;
    public ConnectionHandler(Socket socket, Sign sign, Game game) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.sign = sign; // Знак игрока с этого коннекта
        this.game = game; // Привязываем коннект к конкретной игре
        start();
    }
    @Override
    public void run() {

    }
}

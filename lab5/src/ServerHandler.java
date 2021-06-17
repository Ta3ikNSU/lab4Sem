import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Gson gson = new Gson();
    private String nickname;

    public ServerHandler(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }
    @Override
    public void run() {
        Message getMessage;
        String json;
        while(true){
            try {
                getMessage = gson.fromJson(in.readUTF(), Message.class);
                if(getMessage.getType() == Message.MessageType.LOGIN){
                    sendAll(gson.toJson(new Message(Message.MessageType.INFO, "Say hello to " + getMessage.getBody())));
                    System.out.println("Say hello to " + getMessage.getBody());
                }

                if(getMessage.getType() == Message.MessageType.MESSAGE){
                    sendAll(gson.toJson(new Message(Message.MessageType.MESSAGE, getMessage.getBody())));
                    System.out.println(getMessage.getBody());
                }

                if (getMessage.getType() == Message.MessageType.LOGOUT) {
                    Server.serverList.remove(this);
                    this.socket.close();
                    in.close();
                    out.close();
                    sendAll(gson.toJson(new Message(Message.MessageType.INFO, "Say goodbye to " + getMessage.getBody())));
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAll(String json) {
        for (ServerHandler vr : Server.serverList) {
            vr.send(json);
        }
    }
    private void send(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getNickname() {
        return nickname;
    }
}

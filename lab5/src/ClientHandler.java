import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private final Object synchronObj = new Object();
    private final Object synchronObj1 = new Object();
    Gson gson = new Gson();
    JFrame chatFrame;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private BufferedReader inputUser;
    private String name = "";
    private String text = "";
    private JTextArea jtaTextAreaMessage;

    public ClientHandler(String ipHost, int port) {
        chatFrame = new JFrame();
        chatFrame.setSize(new Dimension(600, 400));
        chatFrame.setBounds(600, 300, 600, 500);
//        chatFrame.setResizable(false);
        chatFrame.setTitle("Chat");
        chatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        chatFrame.add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        chatFrame.add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Отправить");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        JTextField jtfMessage;
        jtfMessage = new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        chatFrame.pack();
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtfMessage.getText().trim().isEmpty()) {
                    synchronized (synchronObj1) {
                        synchronObj1.notify();
                    }
                    text = jtfMessage.getText();
                    jtfMessage.grabFocus();
                }
            }
        });

        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });


        try {
            this.socket = new Socket(ipHost, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ReadMsg().start();
        new WriteMsg().start();
        getNickname();
        chatFrame.setVisible(true);
    }

    private void getNickname() {
        JFrame frame = new JFrame();
        JTextField jTextField = new JTextField(30);
        jTextField.addActionListener(e -> {
            //name = jTextField.getName();
            name = e.getActionCommand();
            if (!name.equals("")) {
                frame.removeAll();
                frame.setVisible(false);
                synchronized (synchronObj) {
                    synchronObj.notify();
                }
                return;
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 100));
        frame.getContentPane().add(jTextField);
        frame.setResizable(false);
        frame.setName("Logging. Enter your name");
        frame.pack();
        frame.setVisible(true);
        synchronized (synchronObj) {
            try {
                synchronObj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                outputStream.writeUTF(gson.toJson(new Message(Message.MessageType.LOGIN, name)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void offHandler() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String input;
            try {
                while (true) {
                    input = inputStream.readUTF();
                    jtaTextAreaMessage.append(input + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class WriteMsg extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (synchronObj1) {
                        synchronObj1.wait();
                    }
                    if (!text.equals("/exit")) {
                        Message msg = new Message(Message.MessageType.MESSAGE, text);
                        outputStream.writeUTF(gson.toJson(msg));
                        System.out.println(text);
                    } else {
                        outputStream.writeUTF(gson.toJson(new Message(Message.MessageType.LOGOUT, "")));
                        offHandler();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

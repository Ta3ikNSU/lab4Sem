package mvc;

import Server.GameStatus;
import Server.Model;
import observation.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Render extends Component implements Observer {
    private JFrame frame;
    private Model model;
    private JLabel[] jLabelArray = new JLabel[9];

    public Render(Model model) {
        frame = new JFrame("TicTacToe");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                jLabelArray[i * 3 + j] = new JLabel();
                jLabelArray[i * 3 + j].setBorder(BorderFactory.createLineBorder(Color.PINK));
                frame.getContentPane().add(jLabelArray[i * 3 + j]);
            }
        }
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void printField(Model model) {
        GameField field = model.field;
        if(model.gameStatus == GameStatus.IN_PROCESS) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field.getCell(new Point(i, j)) == Cell.Cross) {
                        jLabelArray[j * 3 + i].setText("x");
                    } else if (field.getCell(new Point(i, j)) == Cell.Zero) {
                        jLabelArray[j * 3 + i].setText("o");
                    }
                }
            }
        } else if (model.gameStatus == GameStatus.o_WIN) {
            try {
                Image img = ImageIO.read(new File("src/mvc/owin.jpg"));
                frame.getGraphics().drawImage(img, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (model.gameStatus == GameStatus.x_WIN) {
            try {
                Image img = ImageIO.read(new File("src/mvc/xwin.jpg"));
                frame.getGraphics().drawImage(img, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Image img = ImageIO.read(new File("src/mvc/draw.jpg"));
                frame.getGraphics().drawImage(img, 0, 0, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}

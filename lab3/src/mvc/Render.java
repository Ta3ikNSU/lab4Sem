package mvc;

import observation.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Render extends Component implements Observer {
    private JFrame frame;
    private Model model;

    public Render(Model model) {
        frame = new JFrame("AgarIO");
        frame.setPreferredSize(new Dimension(900, 900));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void printField(Model model) {
        frame.removeAll();
        if(model.gameStatus == GameStatus.IN_PROCESS) {
            ArrayList<Enemies> ens = model.getEnemies();
            ArrayList<Dot> dots= model.getDots();

            for(var en : ens){
                en.paint(frame.getGraphics());
            }
            for(var dot : dots){
                dot.paint(frame.getGraphics());
            }

            model.getPlayer().paint(frame.getGraphics());
            System.out.println("drawed");
        } else if (model.gameStatus == GameStatus.WIN) {
            try {
                Image img = ImageIO.read(new File("src/mvc/win.jpg"));
                frame.getGraphics().drawImage(img, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Image img = ImageIO.read(new File("src/mvc/lose.jpg"));
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

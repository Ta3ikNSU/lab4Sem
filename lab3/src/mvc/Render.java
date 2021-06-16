package mvc;

import observation.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Render extends JFrame implements Observer {
    private Model model;

    public Render(Model model) {
        super();
        this.setBackground(Color.BLACK);
        this.setName("AgarIO");
        this.setPreferredSize(new Dimension(900, 900));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        g.clearRect(0,0,900,900);
        if(model.gameStatus == GameStatus.IN_PROCESS) {
            Vector<Enemies> ens = model.getEnemies();
            Vector<Dot> dots= model.getDots();

            for (int i = 0; i < ens.size(); i++) {
                Enemies en = ens.get(i);
                g.setColor(en.getColor());
                g.drawString("ENEMY", en.getX(), en.getY());
                g.fillOval(en.getX(), en.getY(), en.getSizePoint(), en.getSizePoint());
            }
            for (int i = 0; i < dots.size(); i++) {
                Dot dot = dots.get(i);
                g.setColor(dot.getColor());
                g.drawString("FOOD", dot.getX(), dot.getY());
                g.fillOval(dot.getX(), dot.getY(), dot.getSizePoint(), dot.getSizePoint());
            }

            Blob blob = model.getPlayer();
            blob.cordUpdate();
            g.setColor(blob.getColor());
            g.drawString("YOU", blob.getX(), blob.getY());
            g.fillOval(blob.getX(), blob.getY(), blob.getSizePoint(), blob.getSizePoint());
        } else if (model.gameStatus == GameStatus.WIN) {
            try {
                Image img = ImageIO.read(new File("src/mvc/win.jpg"));
                g.drawImage(img, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Image img = ImageIO.read(new File("src/mvc/lose.jpg"));
               g.drawImage(img, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printField(Model model) {
        this.model = model;
        repaint();
    }
}

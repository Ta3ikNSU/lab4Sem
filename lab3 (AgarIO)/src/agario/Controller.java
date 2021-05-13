package agario;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Controller {
    static int score = 30;
    static int xDis = 0;
    static int yDis = 0;
    public final ArrayList<Dot> dots = new ArrayList<>();
    final int height = 900;
    final int width = 900;
    ArrayList<Enemies> enemies = new ArrayList<>();
    Blob b = new Blob(50, 50, 10);
    MyFrame mf = new MyFrame("AgarIO");
    Label l = new Label("10");
    int mouseX = 0;
    int mouseY = 0;

    public static void main(String[] args) {
        new Controller().startGame();
    }

    public void startGame() {
        mf.addMouseMotionListener(new MyMouseMoveListener());
        l.setForeground(Color.WHITE);
        mf.add(l, BorderLayout.NORTH);
        Refresh rf = new Refresh();
        Thread t = new Thread(rf);
        t.start();
        int timeCounter = 0;
        int alpha = 0;
        int factor = 1;
        while (true) {
            try {
                if (enemies.size() < 2) {
                    if (enemies.size() == 0) {
                        Enemies newEn = new Enemies(b.getX() + 500 % 900, b.getY(), b.size + b.size / 10);
                        enemies.add(newEn);
                    }
                    Enemies newEn = new Enemies(b.getX(), b.getY() + 500 % 900, b.size + b.size / 10);
                    enemies.add(newEn);
                }
                if (score > 2500) {
                    System.out.println("Your win");
                    mf.repaint();
                    break;
                }
                if (score == 0) {
                    System.out.println("Your lose");
                    mf.repaint();
                    break;
                }
                ;
                Random r = new Random();
                sleep(r.nextInt(40));
                double easingAmount = 20;
                b.setX((int) (b.getX() + xDis / easingAmount));
                b.setY((int) (b.getY() + yDis / easingAmount));

                if(timeCounter == 0){
                    alpha = r.nextInt(360);
                    factor = r.nextInt(5);
                    timeCounter = r.nextInt(20)+10;
                } else {
                    timeCounter--;
                }

                var en = enemies.get(0);
                {
                    en.setX((int) (en.getX() + Math.cos(alpha * factor / (2 * 3.14)) * 3));
                    en.setY((int) (en.getY() + Math.sin(alpha * factor / (2 * 3.14)) * 3));
                    en.cordRedactor();
                }
                en = enemies.get(1);
                {
                    en.setX((int) (en.getX() + Math.cos(alpha / (2 * 3.14)) * 3));
                    en.setY((int) (en.getY() + Math.sin(alpha / (2 * 3.14)) * 3));
                    en.cordRedactor();
                }
                b.cordRedactor();
                if (r.nextInt(20) == 5) {
                    int randX = r.nextInt(800) + 50;
                    int randY = r.nextInt(800) + 50;
                    int randSize = r.nextInt(50)+2;
                    Dot d = new Dot(randX, randY, randSize);
                    synchronized (dots) {
                        dots.add(d);
                    }
                    mf.repaint();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    class Refresh implements Runnable {

        public void run() {
            while (true) {
                Random ran = new Random();
                try {
                    sleep(ran.nextInt(20));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Rectangle r = new Rectangle(b.getX(), b.getY(), b.getSizePoint(), b.getSizePoint());

                synchronized (enemies) {
                    Iterator<Enemies> it = enemies.iterator();
                    while (it.hasNext()) {
                        Enemies e = it.next();
                        Rectangle r2 = new Rectangle(e.getX(), e.getY(), e.getSizePoint(), e.getSizePoint());
                        if (r2.intersects(r)) {
                            if (b.getSizePoint() < e.getSizePoint()) {
                                score = 0;
                                return;
                            } else {
                                score += e.getSizePoint();
                                it.remove();
                            }
                        }
                        Iterator<Dot> itDots = dots.iterator();
                        while (itDots.hasNext()) {
                            Dot d = itDots.next();
                            Rectangle r1 = new Rectangle(d.getX(), d.getY(), d.getSizePoint(), d.getSizePoint());
                            if (r1.intersects(r2)) {
                                e.setSize(e.getSizePoint() + d.getSizePoint() / 10);
                                itDots.remove();
                            }
                        }
                    }
                }
                synchronized (dots) {
                    Iterator<Dot> it = dots.iterator();
                    while (it.hasNext()) {
                        Dot d = it.next();
                        Rectangle r1 = new Rectangle(d.getX(), d.getY(), d.getSizePoint(), d.getSizePoint());
                        if (r1.intersects(r)) {
                            score += d.getSizePoint();
                            b.setSize(b.getSizePoint() + d.getSizePoint() / 10);
                            it.remove();
                            l.setText(String.valueOf(score));
                        }
                    }
                }
                mf.repaint();
            }

        }
    }

    class MyMouseMoveListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent m) {
            mouseX = m.getX();
            mouseY = m.getY();
            xDis = mouseX - b.getX();
            yDis = mouseY - b.getY();
        }
    }

    class MyFrame extends Frame {
        MyFrame(String s) {
            super(s);
            setBounds(0, 0, width, height);
            setBackground(Color.DARK_GRAY);
            setResizable(false);
            setVisible(true);
        }

        public void paint(Graphics g) {
            if(score == 0){
                try {
                    Image img = ImageIO.read(new File("src/agario/lose.jpg"));
                    g.drawImage(img, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
            if(score > 2500){
                try {
                    Image img = ImageIO.read(new File("src/agario/win.jpg"));
                    g.drawImage(img, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                b.paint(g);
                synchronized (enemies) {
                    Iterator<Enemies> i = enemies.iterator();
                    while (i.hasNext()) {
                        Enemies e = i.next();
                        e.paint(g);
                    }
                }
                synchronized (dots) {
                    Iterator<Dot> i = dots.iterator();
                    while (i.hasNext()) {
                        Dot d = i.next();
                        d.paint(g);
                    }
                }
            }
        }
    }
}
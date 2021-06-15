package mvc;

import java.awt.*;

public class Enemies extends Entity {


    Enemies(int x, int y, int size) {
        super(x, y, size);
        this.color = Color.RED;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawString("ENEMY", x , y);
        g.fillOval(x, y, size, size);
    }
}
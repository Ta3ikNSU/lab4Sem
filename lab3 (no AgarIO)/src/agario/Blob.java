package agario;

import java.awt.*;

public class Blob extends Entity{

    Blob(int x, int y, int size) {
        super(x, y, size);
        this.color = Color.CYAN;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawString("YOU", x , y);
        g.fillOval(x, y, size, size);
    }
}
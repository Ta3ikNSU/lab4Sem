package mvc;

import java.awt.*;

public class Enemies extends Entity {
    private int speedX;
    private int speedY;

    Enemies(int x, int y, int size) {
        super(x, y, size);
        this.color = Color.RED;
        speedX = 1;
        speedY = 1;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}
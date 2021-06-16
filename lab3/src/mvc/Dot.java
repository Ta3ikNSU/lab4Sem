package mvc;

import java.awt.*;
import java.util.Random;

public class Dot extends Entity {

    Dot(int x, int y, int size) {
        super(x, y, size);
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        this.color = new Color(red, green, blue);
    }
}

package mvc;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int size;
    protected Color color;

    public int getX(){
        return x;
    }
    public void setX(int newX){
        x = newX;
    }
    public int getY(){
        return y;
    }
    public void setY(int newY){
        y = newY;
    }
    public int getSizePoint(){
        return this.size;
    }
    public void setSize(int newSize){
        this.size = newSize;
    }

    protected abstract void paint(Graphics g);

    protected void cordUdpate(){
        if (this.x < 0) this.x = this.size / 2;
        if (this.y < 38) this.y = 38 + this.size / 2;
        if (this.x + this.size > 895)  this.x = (895 - this.size);
        if (this.y + this.size > 895) this.y = (895 - this.size);
    }

    protected Entity(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }
}

package Objects;

import java.awt.*;
import java.util.TimerTask;

/**
 * Created by Nilj on 15-01-13.
 */
public abstract class MapObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;


    public MapObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this. height = height;
    }

    public int leftX() {
        return x;
    }

    public int rightX() {
        return x + width;
    }

    public int topY() {
        return y;
    }

    public int bottomY() {
        return y + height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void printCoordinates() {
        System.out.println("Object at:" + x + ":" + y);
    }
}

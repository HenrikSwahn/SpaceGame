package Objects;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by Nilj on 15-01-13.
 */
public abstract class MovingObjects extends MapObject{
    protected int xa;
    protected int ya;

    public MovingObjects(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.xa = 0;
        this.ya = 0;
    }

    public int getYa() {
        return ya;
    }

    public void setYa(int ya) {
        this.ya = ya;
    }

    public int getXa() {
        return xa;
    }

    public void setXa(int xa) {
        this.xa = xa;
    }

    public abstract void move();
    public abstract BufferedImage getTexture();
}

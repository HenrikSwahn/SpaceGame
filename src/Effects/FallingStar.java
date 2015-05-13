package Effects;

import Objects.MovingObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;

/**
 * Created by Nilj on 15-01-14.
 */
public class FallingStar extends MovingObjects {

    BufferedImage texture;

    public FallingStar(int x, int y, int width, int height) {
        super(x,y,width,height);
        ya = 5;

        try {
            texture = ImageIO.read(new File("big-circle.png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void move() {
        y = y + ya;
    }
    public BufferedImage getTexture() {
        return texture;
    }
}

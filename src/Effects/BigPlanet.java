package Effects;

import Objects.MovingObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nilj on 15-01-14.
 */
public class BigPlanet extends MovingObjects {
    BufferedImage texture;

    public BigPlanet(int x, int y, int width, int height) {
        super(x,y,width,height);
        try{
            texture = ImageIO.read(new File("BigPlanet.png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        ya = 3;
    }
    public BufferedImage getTexture() {
        return texture;
    }

    @Override
    public void move() {
        y = y + ya;
    }
}

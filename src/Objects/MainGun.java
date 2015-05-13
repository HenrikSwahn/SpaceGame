package Objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nilj on 15-01-14.
 */
public class MainGun extends MovingObjects {

    BufferedImage texture;
    private Player p;
    private Enemy e;
    public MainGun(int x, int y, int width, int height, Player p, Enemy e) {
        super(x,y,width,height);

        try {
            texture = ImageIO.read(new File("redLaserRay.png"));
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        this.p = p;
        this.e = e;
    }
    @Override
    public void move() {
        if(p != null) {
            ya = -5;
        }else if(e != null) {
            ya = +5;
        }
        y = y + ya;
    }
    @Override
    public BufferedImage getTexture() {
        return texture;
    }
}

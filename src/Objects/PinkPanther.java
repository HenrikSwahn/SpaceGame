package Objects;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nilj on 15-01-15.
 */
public class PinkPanther extends Enemy {

    private BufferedImage textures;

    public PinkPanther(int x, int y, int width, int height,int health, int posX, int posY, Game game, int dir) {
        super(x,y,width,height,health,posX,posY,game,dir);

        try {
            textures = ImageIO.read(new File("EnemyShip.png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getTexture() {
        return textures;
    }
}

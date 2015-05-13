package Effects;

import Objects.StationaryObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nilj on 15-01-15.
 */
public class Explosion extends StationaryObjects {

    private BufferedImage[][] explosion;
    private int col;
    private int row;
    private boolean removeExplosion;

    public Explosion(int x, int y, int width, int height) {
        super(x,y,width,height);

        col = 0;
        row = 0;
        try {
            BufferedImage img = ImageIO.read(new File("explosion.png"));
            explosion = new BufferedImage[9][9];
            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    explosion[i][j] = img.getSubimage(j*100,i*100,100,100);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        removeExplosion = false;
    }
    public BufferedImage getTexture() {
        BufferedImage toReturn = explosion[row][col];
        col++;
        if (col == 9) {
            row++;
            col = 0;
        }
        if (row == 9) {
            removeExplosion = true;
        }
        return toReturn;
    }
    public boolean getRemoveExplosion() {
        return removeExplosion;
    }
}

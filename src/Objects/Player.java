package Objects;
import Effects.Sound;
import Main.Game;


import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

//SOund
import java.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by Nilj on 15-01-13.
 */
public class Player extends MovingObjects {

    private Game game;
    private boolean rightSideHit;
    private boolean leftSideHit;
    private boolean topSideHit;
    private boolean downSideHit;
    private BufferedImage[] shipGraphics;
    private int imageCounter;
    private boolean isFlinching;
    private boolean isVisible;
    private int health;
    private Sound sound;
    private int ammo;

    int delay = 2000;
    int period = 400;
    Timer timer;

    public Player(int x, int y, int width, int height,int health, Game game) {
        super(x,y,width,height);
        this.game = game;
        this.rightSideHit = false;
        this.leftSideHit = false;
        this.downSideHit = false;
        this.topSideHit = false;
        this.isFlinching = false;
        this.isVisible = true;
        this.health = health;
        this.ammo = 10;

        try {
            BufferedImage img = ImageIO.read(new File("playerShip1Sprite.png"));

            shipGraphics = new BufferedImage[5];
            for(int i = 0; i < 5; i++) {
                shipGraphics[i] = img.getSubimage(i*110,0,110,125);
            }
            imageCounter = 0;
        }catch(Exception e) {
            e.printStackTrace();
        }
        sound = new Sound();
        timer  = new Timer();
    }

    @Override
    public void move() {
        if(x + xa > 0 && x + xa < game.getWidth() - width) {
            if(rightSideHit) {
                xa = 0;
            }
            if(leftSideHit) {
                xa = 0;
            }
            x = x + xa;
        }
        if(y + ya > 0 && y + ya < (game.getHeight() - 100) - height) {
            if(downSideHit) {
                ya = 0;
            }
            if(topSideHit) {
                ya = 0;
            }
            if(ya == 0 && !downSideHit && !topSideHit && bottomY() < 800){
                ya = 1;
            }
            y = y + ya;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!isFlinching()) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                xa = +3;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                xa = -3;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ya = -3;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                ya = +3;
            }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                if(ammo > 0) {
                    timer.cancel();
                    timer = new Timer();
                    game.addProjectile(null);
                    sound.playLaser();
                    ammo--;
                    timer.scheduleAtFixedRate(new TimerTask() {
                                                  @Override
                                                  public void run() {
                                                      if (ammo < 10) {
                                                          ammo++;
                                                      }
                                                  }
                                              }, delay, period
                    );
                }
            }
        }else {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                xa = +1;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                xa = -1;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ya = -1;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                ya = +1;
            }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                if(ammo > 0) {
                    timer.cancel();
                    timer = new Timer();
                    game.addProjectile(null);
                    sound.playLaser();
                    ammo--;
                    timer.scheduleAtFixedRate(new TimerTask() {
                                                  @Override
                                                  public void run() {
                                                      if (ammo < 10) {
                                                          ammo++;
                                                      }
                                                  }
                                              }, delay, period
                    );
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            xa = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            ya = 0;
        }
    }
    public BufferedImage getTexture() {
        BufferedImage img = shipGraphics[imageCounter++];
        if(imageCounter == 5) imageCounter = 0;
        return img;
    }
    public boolean lowerLifeWithAmount(int amount) {
        health -= amount;
        startFlinching();
        x = 500;
        y = 800 - height;
        xa = 0;
        ya = 0;
        return health < 0;
    }
    public void setVisible() {
        if(isVisible) {
            isVisible = false;
        }else {
            isVisible = true;
        }
    }
    public boolean isVisible() {
        return isVisible;
    }
    public boolean isFlinching() {
        return isFlinching;
    }
    private void startFlinching() {

        isFlinching = true;
        int delay = 0;
        int period = 60;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      int times;
                                      @Override
                                      public void run() {
                                          times++;
                                          if(times <= 60) {
                                              setVisible();
                                          }else {
                                              isVisible = true;
                                              isFlinching = false;
                                              cancel();
                                          }
                                      }

                                  },delay,period
        );
    }
    private void scheduleReloder() {

    }
    public int getHealth() {
        return health;
    }
    public int getAmmo() {
        return ammo;
    }
}

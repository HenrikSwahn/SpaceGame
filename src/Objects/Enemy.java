package Objects;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nilj on 15-01-13.
 */
public abstract class Enemy extends MovingObjects {
    public enum Direction {
        DOWN, RIGHT, UP, LEFT;
    }
    private Game game;
    private Direction dir;
    private int counter;

    //Final position variables
    private boolean notInPosition;
    private int posX;
    private int posY;
    private boolean isVisible;
    private boolean isFlinching;
    private boolean shootingPosition;
    private int health;

    public Enemy(int x, int y, int width, int height, int health, int posX, int posY, Game game, int dir) {
        super(x, y, width, height);
        this.game = game;
        this.posX = posX;
        this.posY = posY;

        if (dir == 1) {
            this.dir = Direction.DOWN;
        } else if (dir == 2) {
            this.dir = Direction.UP;
        }
        counter = 200;
        notInPosition = true;
        ya = 1;
        shootingPosition = false;
        isFlinching = false;
        this.health = health;

        int delay = 1000;
        int period = 2000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          shoot();
                                          }
                                      },delay,period
        );
        isVisible = true;
    }
    private void shoot() {
        if(shootingPosition) {
            if (!isFlinching) {
                Random r = new Random();
                int high = 10;
                int low = 1;
                int val = r.nextInt(high - low) - low;
                System.out.println(val);
                if (val >= 3 && val <= 7) {
                    game.addProjectile(this);
                }
            }
        }
    }
    @Override
    public void move() {
        if (notInPosition) {
            if (x < posX) {
                xa = +1;
            } else if (x > posX) {
                xa = -1;
            } else {
                notInPosition = false;
            }
            x = x + xa;
        } else {
            if(dir == Direction.DOWN) {
                ya = +1;
                xa = 0;
            }else if(dir == Direction.UP) {
                ya = -1;
                xa = 0;
            }
            if(topY() == 30 && dir == Direction.UP) {
                dir = Direction.LEFT;
                ya = 0;
                xa = -1;
            }else if(leftX() == (posX - 750) && dir == Direction.LEFT) {
                dir = Direction.DOWN;
                xa = 0;
                ya = +1;
            }else if(topY() == 230 && dir == Direction.DOWN) {
                dir = Direction.RIGHT;
                xa = +1;
                ya = 0;
                shootingPosition = true;
            }else if(rightX() == (posX + 750) && dir == Direction.RIGHT) {
                dir = Direction.UP;
                xa = 0;
                ya = -1;
                shootingPosition = false;
            }else if(rightX() == posX && dir == Direction.RIGHT) {
                dir = Direction.UP;
                xa = 0;
                ya = -1;
                shootingPosition = false;
            }else if(leftX() == posX && dir == Direction.LEFT) {
                dir = Direction.DOWN;
                xa = 0;
                ya = +1;
            }
            y = y + ya;
            x = x + xa;
        }
    }
    public void printposX() {
        System.out.println(posX);
    }
    public boolean lowerLifeWithAmount(int amount) {
        health -= amount;
        startFlinching();
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
                                          if(times <= 30) {
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
}

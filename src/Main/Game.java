package Main;

import Levels.Level1;
import Objects.Enemy;
import Effects.FallingStar;
import Objects.Player;
import Effects.BigPlanet;
import Objects.MainGun;
import Effects.Explosion;
import Effects.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.Timer;

/**
 * Created by Nilj on 15-01-13.
 */
public class Game extends JPanel {

    private Player p;
    private BufferedImage background;
    private FallingStar[] stars;
    private int counter;
    private BigPlanet planet;
    private ArrayList<Enemy> enemies;
    private ArrayList<MainGun> activeProjectiles;
    private Explosion exp;
    private Sound sound;


    //GameStates
    private boolean menuState;
    private boolean level1State;

    //Menu
    private String[] menu;
    private int choice;

    //Levels
    Level1 lv1;

    public Game() {
        menuState = false;
        level1State = false;
        sound = new Sound();
        loadMenu();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(menuState) {
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        choice++;
                    }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                        choice--;
                    }
                    if(choice == -1) {
                        choice = menu.length-1;
                    }else if(choice == menu.length) {
                        choice = 0;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if(choice == 0) {
                            loadGame();
                        }
                    }
                }else {
                    p.keyPressed(e);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(!menuState) {
                    p.keyReleased(e);
                }
            }
        });
        setFocusable(true);
    }
    private void loadMenu() {
        menuState = true;
        menu = new String[2];
        menu[0] = "Play";
        menu[1] = "Controls";
        choice = 0;
        try {
            background = ImageIO.read(new File("Space 3.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sound.playMenuMusic();
    }
    private void loadGame() {
        p = new Player(500, 500, 110, 125,3,this);
        lv1 = new Level1(this);
        enemies = new ArrayList<Enemy>(lv1.getNrOfEnemies());
        while(lv1.getHasMoreEnemies()) {
            enemies.add(lv1.getEnemy());
        }
        stars = new FallingStar[5];
        counter = 0;
        activeProjectiles = new ArrayList<MainGun>();
        scheduleEvents();
        sound.playMenuMusic();
        menuState = false;
        level1State = true;
    }
    private void scheduleEvents() {
        scheduleStars();
        schedulePlanets();
    }
    private void scheduleStars() {
        int delay = 1000;
        int period = 2000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          if (counter < 5) {
                                              addStar();
                                          }
                                      }
                                  }, delay, period
        );
    }
    private void schedulePlanets() {
        int delay = 1000;
        int period = 10000;
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                planet = new BigPlanet(600, -300, 100, 100);
            }
        }, delay, period);
    }
    public void run() {
        boolean gameOver = false;
        while(!gameOver) {
            if(!menuState) {
                p.move();
                //p.doesIntersect(enemies);

                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    e.move();
                }
                for (int i = 0; i < counter; i++) {
                    if (stars[i].topY() > getHeight()) {
                        stars[i].setY(-100);
                        Random r = new Random();
                        int posHigh = 1260;
                        int posLow = 320;
                        int posRand = r.nextInt(posHigh - posLow) + posLow;
                        stars[i].setX(posRand);
                    }
                    stars[i].move();
                }
                if (planet != null) {
                    planet.move();
                    if (planet.topY() > getHeight()) {
                        planet = null;
                    }
                }
                for (int i = 0; i < activeProjectiles.size(); i++) {
                    MainGun m = activeProjectiles.get(i);
                    m.move();
                    if (m.getYa() == +5) {
                        if(m.getBounds().intersects(p.getBounds())) {
                            if(!p.isFlinching()) {
                                activeProjectiles.remove(i);
                                if (p.lowerLifeWithAmount(1)) {
                                    exp = new Explosion(p.leftX(), p.topY(), 100, 100);
                                    p.setVisible();
                                    gameOver = true;
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < enemies.size(); j++) {
                            Enemy e = enemies.get(j);
                            if (m.getBounds().intersects(e.getBounds())) {
                                if(!e.isFlinching()) {
                                    activeProjectiles.remove(i);
                                    if (e.lowerLifeWithAmount(1)) {
                                        exp = new Explosion(e.leftX(), e.topY(), 100, 100);
                                        enemies.remove(j);
                                        sound.playExplosion();
                                    }
                                }
                            }
                        }
                    }
                }
                if (enemies.size() == 0) gameOver = true;
                if (exp != null) {
                    if (exp.getRemoveExplosion()) exp = null;
                }
            }
            repaint();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(gameOver) {
            System.exit(1);
        }
    }
    private void addStar() {
        Random r = new Random();
        int negLow = -200;
        int negHigh = -10;
        int negRand = r.nextInt(negHigh - negLow) + negLow;

        int posHigh = 1260;
        int posLow = 320;
        int posRand = r.nextInt(posHigh - posLow) + posLow;
        stars[counter] = new FallingStar(posRand, negRand,5,5);
        counter++;
    }
    public void addProjectile(Enemy e) {
        if(e == null) {
            activeProjectiles.add(new MainGun((p.leftX() + (p.getWidth() / 2) - 2), (p.topY() + 2), 10, 30, p,null));
        }else {
            activeProjectiles.add(new MainGun((e.leftX() + (e.getWidth() / 2) - 2), (e.bottomY() -2), 10, 30, null,e));
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBackground(g2d);

        if(!menuState) {
            drawBackGroundObjects(g2d);
            drawPlayer(g2d);
            drawProjectiles(g2d);
            drawEnemies(g2d);
            drawGUI(g2d);
        }else {
            drawMenu(g2d);
        }
    }
    private void drawPlayer(Graphics2D g2d) {
        BufferedImage img = p.getTexture();
        if(p.isVisible()) {
            if (p.getXa() == +3) {
                double rotation = Math.toRadians(45);
                double locationX = p.getWidth() / 2;
                double locationY = p.getHeight() / 2;
                AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage(op.filter(img, null), p.leftX(), p.topY(), null);
            } else if (p.getXa() == -3) {
                double rotation = Math.toRadians(305);
                double locationX = p.getWidth() / 2;
                double locationY = p.getHeight() / 2;
                AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage(op.filter(img, null), p.leftX(), p.topY(), null);
            } else {
                g2d.drawImage(p.getTexture(), p.leftX(), p.topY(), p.getWidth(), p.getHeight(), null);
            }
        }
    }
    private void drawEnemies(Graphics2D g2d) {
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if(e.isVisible()) {
                g2d.drawImage(e.getTexture(), e.leftX(), e.topY(), e.getWidth(), e.getHeight(), null);
            }
        }
        if(exp != null) {
            g2d.drawImage(exp.getTexture(),exp.leftX(), exp.topY(),exp.getWidth(),exp.getHeight(),null);
        }
    }
    private void drawBackground(Graphics2D g2d) {
        g2d.drawImage(background,0,0,1280,960,null);
    }
    private void drawBackGroundObjects(Graphics g2d) {
        if(planet != null) {
            g2d.drawImage(planet.getTexture(), planet.leftX(), planet.topY(), planet.getWidth(), planet.getHeight(), null);
        }
        for(int i = 0; i < counter; i++) {
            if(stars[i].bottomY() > 0) {
                g2d.drawImage(stars[i].getTexture(), stars[i].leftX(), stars[i].topY(),
                        stars[i].getWidth(), stars[i].getHeight(),null);
            }
        }
    }
    private void drawProjectiles(Graphics2D g2d) {
        for(int i = 0; i < activeProjectiles.size(); i++) {
            MainGun m = activeProjectiles.get(i);
            if(m.getYa() == +5) {
                g2d.drawImage(m.getTexture(),m.leftX(),m.bottomY(),m.getWidth(),m.getHeight(),null);
            }else if(m.getYa() == -5) {
                g2d.drawImage(m.getTexture(),m.leftX(),m.topY(),m.getWidth(),m.getHeight(),null);
            }
        }
    }
    private void drawMenu(Graphics2D g2d) {
        if(choice == 0) {
            g2d.setFont(new Font("Tahoma", Font.BOLD, 40));
            g2d.setColor(Color.WHITE);
            g2d.drawString(menu[0], 100, 100);
            g2d.setFont(new Font("Tahoma", Font.BOLD, 30));
            g2d.setColor(Color.GRAY);
            g2d.drawString(menu[1],100,150);
        }else if(choice == 1){
            g2d.setFont(new Font("Tahoma", Font.BOLD, 30));
            g2d.setColor(Color.GRAY);
            g2d.drawString(menu[0], 100, 100);
            g2d.setFont(new Font("Tahoma", Font.BOLD, 40));
            g2d.setColor(Color.WHITE);
            g2d.drawString(menu[01],100,150);
        }
    }
    private void drawGUI(Graphics2D g2d) {
        g2d.setFont(new Font("Tahoma", Font.BOLD, 30));
        g2d.setColor(Color.WHITE);
        g2d.drawString(Integer.toString(p.getHealth()), 30,700);
        g2d.drawString(Integer.toString(p.getAmmo()), 60,700);
    }
}

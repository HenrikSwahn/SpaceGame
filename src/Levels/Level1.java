package Levels;

import Main.Game;
import Objects.PinkPanther;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Nilj on 15-01-15.
 */
public class Level1 {

    private ArrayList<PinkPanther> panthers1;
    private ArrayList<PinkPanther> panthers2;

    private int nrOfEnemies;


    public Level1(Game game) {
        panthers1 = new ArrayList<PinkPanther>();
        panthers2 = new ArrayList<PinkPanther>();

        nrOfEnemies = 0;

        int width = 60;
        int height = 40;


        //panthers1 variables
        int panthers1PosX = 30;
        int panthers1PosY = 230;
        int panthers1StartX = -480;

        //panthers2 variables
        int panthers2PosX = 1190;
        int panthers2PosY = 230;
        int panthers2StartX = 1700;

        for (int i = 0; i < 6; i++) {

            //Handle panther1
            panthers1.add(new PinkPanther(
                    panthers1StartX,
                    130,
                    width,
                    height,
                    3,
                    panthers1PosX,
                    panthers1PosY,
                    game,
                    1));

            nrOfEnemies++;
            System.out.println(panthers1StartX);
            panthers1StartX += 100;
            panthers1PosX += 100;

            //Handle panthers2
            panthers2.add(new PinkPanther(
                    panthers2StartX,
                    130,
                    width,
                    height,
                    3,
                    panthers2PosX,
                    panthers2PosY,
                    game,
                    2));

            panthers2PosX -= 90;
            panthers2StartX -= 90;
            nrOfEnemies++;
        }
    }
    public int getNrOfEnemies() {
        return nrOfEnemies;
    }
    public PinkPanther getEnemy() {
        if(!panthers1.isEmpty()) {
            nrOfEnemies--;
            return panthers1.remove(0);
        }else if(!panthers2.isEmpty()) {
            nrOfEnemies--;
            return panthers2.remove(0);
        }
        return null;
    }
    public boolean getHasMoreEnemies() {
        return nrOfEnemies > 0;
    }
}

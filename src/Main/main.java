package Main;

import Main.Game;

import javax.swing.JFrame;

/**
 * Created by Nilj on 15-01-13.
 */
public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,960);
        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.run();
    }
}

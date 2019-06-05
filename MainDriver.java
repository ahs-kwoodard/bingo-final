
/**
 * Kai'lani Woodard
 * Bingo Main Class
 * Programming III - AP CS
 * 6-04-2019
 */

import java.io.*;

public class MainDriver {

    public static void main(String[] args) throws IOException {

        Bingo game = new Bingo();
        game.write("input.txt");
        game.read("input.txt");
        int x = game.playGame();
        System.out.println("the winning number is " + x);
    }
}


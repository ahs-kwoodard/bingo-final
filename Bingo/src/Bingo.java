
/**
 * Kai'lani Woodard
 * Bingo Class
 * Programming III - AP CS
 * 6-04-2019
 */

import java.util.*;
import java.io.*;

public class Bingo {

    private int[][] card;       //Bingo card configuration
    private int[] stream;       //list of 75 integers
    private boolean[][] marks;  //simulates placing chips on a Bingo card

    public Bingo() {
        card = new int[5][5];
        stream = new int[75];
        marks = new boolean[5][5];
    }

    /**
     * This method writes a random Bingo card configuration and a stream of
     * random number between 1 and 75 to the output file.
     *
     * The first column in the table contains only integers between 1 and 15,
     * the second column numbers are all between 16 and 30, the third are 31 to
     * 45, the fourth 46-60, and the fifth 61-75.
     *
     * There are no duplicate numbers on a Bingo card.
     */
    
    public static int getRandomInt (int min, int max) {
        return (int) (Math.random()*((max=min)+1))+min;
    }
    
    public static int getRandomInt (int min, int max, ArrayList<Integer> nums) {
        int randomInt = (int) (Math.random()*((max-min)+1))+min;
        
        for (int i = 0; i < nums.size(); i++) {
            while (randomInt == nums.get(i)) {
                randomInt = (int) (Math.random()*((max-min)+1))+min;
            }
        }
        return randomInt;
    }
    
    public void write(String outputFile) throws IOException {
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        
        int[][] generatedCard = new int[5][5];
        ArrayList<Integer> generatedList = new ArrayList<>();
        
        for (int c = 0; c < card[0].length - 1; c++) {
            for (int r = 0; r < card.length - 1; r++) {
                if (c == 0) {
                    generatedCard[r][c] = getRandomInt(1, 15, generatedList);
                } else if (c == 1) {
                    generatedCard[r][c] = getRandomInt(16, 30, generatedList);
                } else if (c == 2) {
                    generatedCard[r][c] = getRandomInt(31, 45, generatedList);
                    if (r == 2 && c == 2)
                        generatedCard[r][c] = 0;
                } else if (c == 3) {
                    generatedCard[r][c] = getRandomInt(46, 60, generatedList);
                } else if (c == 4) {
                    generatedCard[r][c] = getRandomInt(61, 75, generatedList);
                }
                
                generatedList.add(card[r][c]);
                
                if(c == 0 && r > 0)
                    bw.newLine();
                
                bw.write(generatedCard[r][c] + " ");
            }
        }
        
        bw.newLine();
            
        ArrayList<Integer> generatedStreamList = new ArrayList<>();
        int[] generatedStream = new int[75];
        
        for (int i = 0; i < stream.length; i++) {
            generatedStreamList.add(i+1);
        }
        
        shuffle(generatedStreamList);
        
        for (int j = 0; j < generatedStream.length; j++) {
            generatedStream[j] = generatedStreamList.get(j);
            bw.write(generatedStream[j] + " ");
        }
        
        bw.close();
        
    }

    /**
     * Shuffles the list of numbers
     */
    public void shuffle(ArrayList<Integer> list) {
        int index;
        int prevIndex;
        
        for (int k = 0; k < list.size(); k++) {
            index = getRandomInt(1, 75);
            prevIndex = list.get(k);
            list.set(k, list.get(index));
            list.set(index, prevIndex);
        }
        
    }

    /**
     * This method reads a given inputFile that contains a Bingo card
     * configuration and a stream of numbers between 1 and 75. . A Bingo card
     * configuration is stored in the card array. A list of 75 integers is
     * stored in the stream array.
     */
    public void read(String inputFile) throws IOException {
        File file = new File(inputFile);
        Scanner reader = new Scanner(file);
        
        for(int r = 0; r < card.length; r++)
            for(int c = 0; c < card[0].length; c++)
                card[r][c] = reader.nextInt();
        
        for(int i = 0; i < stream.length; i++)
            stream[i] = reader.nextInt();
        
        reader.close();
    }

    /**
     * This method returns the first integer from the stream array that gives
     * you the earliest winning condition.
     *
     * - all the spots in a column are marked - all the spots in a row are
     * marked - all the spots in either of the two diagonals are marked - all
     * four corner squares are marked
     */
    public int playGame() {
        int calledNum = 0;
        int numIndex = 0;
        
        boolean winnerWinnerChickenDinner = false;
        
        for (int r = 0; r < card.length; r++) {
            for (int c = 0; c < card[0].length; c++) {
                if(r == 2 && c == 2)
                    marks[r][c] = true;
                else
                    marks[r][c] = false;
            }
        }
        
        while (!winnerWinnerChickenDinner) {
            calledNum = stream[numIndex];
            
            for (int r = 0; r < card.length; r++) {
                for (int c = 0; c < card[0].length; c++) {
                    if(card[r][c] == calledNum)
                        marks[r][c] = true;
                }
            }
            
            /** Possible ways to win are:
             * Postage Stamp??, Across, Down, Diagonal (both ways)
             */
            
            int across = 0,
                down = 0,
                forwardDiagonal = 0,
                backwardDiagonal = 0;
            
            if(marks[0][0] && marks[4][4] && marks[4][0] && marks[0][4])
                winnerWinnerChickenDinner = true;
            
            for (int r = 0; r < card.length; r++) {
                for (int c = 0; c < card.length; c++) {
                    if(marks[r][c] = true)
                        across++;
                    if(across == 5)
                        winnerWinnerChickenDinner = true;
                }
            }
            
            for (int c = 0; c < card[0].length; c++) {
                for (int r = 0; c < card.length; r++) {
                    if(marks[r][c] = true)
                        down++;
                    if(down == 5)
                        winnerWinnerChickenDinner = true;
                }
            }
            
            for (int rc = 0; rc < card.length; rc++) {
                if(marks[rc][rc] = true)
                    forwardDiagonal++;
                if(forwardDiagonal == 5)
                    winnerWinnerChickenDinner = true;
            }
            
            for (int rc = 0; rc < card.length; rc++) {
                if(marks[rc][4-rc] = true)
                    backwardDiagonal++;
                if(backwardDiagonal == 5) {
                    winnerWinnerChickenDinner = true;
                }
            }
            numIndex++;
        }
        return calledNum;
    }
    
}

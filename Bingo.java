
/**
 * Kai'lani Woodard
 * Bingo Class
 * Programming III - AP CS
 * 6-04-2019
 */
import java.util.*;
import java.io.*;

public class Bingo {

    private Random rand = new Random();
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
    public void write(String outputFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true)); //Create PrintWriter object

        int[][] generatedCard = new int[5][5]; //Create 2-D array for generated bingo card
        int rangeCap = 1; //Beginning of range for calculating random integer

        for (int c = 0; c < generatedCard[0].length; c++) { //Columns
            for (int r = 0; r < generatedCard.length; r++) { //Rows
                if (rangeCap <= 75) { //Stops when rangeCap exceeds 75 (a filled card)
                    if (r == 2) { 
                        if (c == 2) {
                            generatedCard[r][c] = 0; //Putting a "Free Space" in the center of the card
                        }
                    } else {
                        generatedCard[r][c] = rand.nextInt(15) + rangeCap; //Generating random numbers according to range
                    }
                }

                int duplicateCheck = 0; //Variable to check for duplicates

                while (duplicateCheck < generatedCard[0].length) { //Checks through each index of the array
                    if (r != duplicateCheck) { //r does no equal duplicateCheck
                        if (generatedCard[duplicateCheck][c] == generatedCard[r][c]) { //If duplicate found
                            generatedCard[r][c] = rand.nextInt(15) + rangeCap; //Generate new random integer
                            duplicateCheck = 0; //Reset duplicateCheck to zero to check again
                        }
                        duplicateCheck++; //Increment duplicateCheck by one
                    }
                }

                rangeCap += 15; //Increase bounds of the generated random integers

            }

        }

        for (int r = 0; r < generatedCard.length; r++) { //Rows
            for (int c = 0; c < generatedCard[0].length; c++) { //Columns
                writer.print(generatedCard[r][c] + "\t"); //Print given number to outputFile and add text
            }
            writer.print("\n"); //New line
        }
        
        ArrayList<Integer> generatedStream = new ArrayList<>(75); //Create array list to generate a stream
        
        for (int i = 0; i < generatedStream.size(); i++) { //Loop 75 times
            generatedStream.add(i+1); //Add item to array list
        }
        
        shuffle(generatedStream); //Shuffle stream
        
        for (int j = 0; j < generatedStream.size(); j++) { //Loop 75 times again
            writer.print(generatedStream.get(j) + " "); //Write each item to the file
        }
        
        writer.close(); //Close writer

    }

    /**
     * Shuffles the list of numbers
     */
    public void shuffle(ArrayList<Integer> list) {
        int index; //Index to shuffle
        int prevIndex; //Place holder for changed index

        for (int k = 0; k < list.size(); k++) { //Check each index of the array list
            index = rand.nextInt(75) + 1; //Generate random integer between 1 and 75
            prevIndex = list.get(k); //Save the current index
            list.set(k, list.get(index)); //Set the current index to another number from the list
            list.set(index, prevIndex); //Overwrite the current index with the previous index
        }

    }

    /**
     * This method reads a given inputFile that contains a Bingo card
     * configuration and a stream of numbers between 1 and 75. . A Bingo card
     * configuration is stored in the card array. A list of 75 integers is
     * stored in the stream array.
     */
    public void read(String inputFile) throws IOException {
        Scanner reader = new Scanner(new File(inputFile)); //Create scanner object to read file

        for (int r = 0; r < card.length; r++) { //Rows
            for (int c = 0; c < card[0].length; c++) { //Columns
                card[r][c] = reader.nextInt(); //Set given index at card to the next integer
            }
        }

        for (int i = 0; i < stream.length; i++) { //Length of stream
            stream[i] = reader.nextInt(); //Print stream
        }

        reader.close(); //Close reader
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
        int calledNum = 0; //Current number called
        int numIndex = 0; //Index of number called

        boolean winnerWinnerChickenDinner = false; //Boolean of whether or not game is won

        for (int r = 0; r < card.length; r++) { //Rows
            for (int c = 0; c < card[0].length; c++) { //Columns
                if (r == 2 && c == 2) { //Center space
                    marks[r][c] = true; //Mark center space automatically
                } else {
                    marks[r][c] = false; //Leave the rest of the card blank
                }
            }
        }

        while (!winnerWinnerChickenDinner) { //While game is not won
            calledNum = stream[numIndex]; //Called number is called from the stream at index given

            for (int r = 0; r < card.length; r++) { //Rows
                for (int c = 0; c < card[0].length; c++) { //Columns
                    if (card[r][c] == calledNum) { //If stream equals spot on card
                        marks[r][c] = true; //Mark spot
                    }
                }
            }

            /**
             * Possible ways to win are: Four Corners, Across, Down, Diagonal
             * (both ways)
             */
            
                    //Create four variables to indicate each part toward possible win
                int across = 0,
                    down = 0,
                    forwardDiagonal = 0,
                    backwardDiagonal = 0;

            if (marks[0][0] && marks[4][4] && marks[4][0] && marks[0][4]) { //Four corners
                winnerWinnerChickenDinner = true;
            }

            //Across/Rows
            for (int r = 0; r < card.length; r++) {
                for (int c = 0; c < card.length; c++) { 
                    if (marks[r][c] = true) { 
                        across++;
                    }
                    if (across == 5) {
                        winnerWinnerChickenDinner = true;
                    }
                }
            }
                
            //Down/Columns
            for (int c = 0; c < card[0].length; c++) {
                for (int r = 0; c < card.length; r++) {
                    if (marks[r][c] = true) {
                        down++;
                    }
                    if (down == 5) {
                        winnerWinnerChickenDinner = true;
                    }
                }
            }

            //Forward Diagonal
            for (int rc = 0; rc < card.length; rc++) {
                if (marks[rc][rc] = true) {
                    forwardDiagonal++;
                }
                if (forwardDiagonal == 5) {
                    winnerWinnerChickenDinner = true;
                }
            }

            //Backward Diagonal
            for (int rc = 0; rc < card.length; rc++) {
                if (marks[rc][4 - rc] = true) {
                    backwardDiagonal++;
                }
                if (backwardDiagonal == 5) {
                    winnerWinnerChickenDinner = true;
                }
            }
            numIndex++;
        }
        
        return calledNum; //Return the winning number
        
    }

}

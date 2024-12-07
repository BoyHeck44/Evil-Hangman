import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Write a description of class EvilHangman here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EvilHangman {
    /**
     * The main function for user to get data and start the game
     */
    public static void main(String[] args) {
        //Creates and opens a scanner
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input file: ");
        File file = new File(scan.nextLine());
    
        //Checks that the file inputed exists
        while (!file.exists()) {
            System.out.println("File not found\nPlease input file: ");
            file = new File(scan.nextLine());
        }

        //Chooses word length
        System.out.println("Choose word length: ");
        int len = scan.nextInt();
        //Checks to see if it is an allowed word length
        while (len < 1 || len > 29 || len == 26 || len == 27) {
            System.out.println("No word of that length exists\nChoose word length: ");
            len = scan.nextInt();
        }

        //Asks how many guesses the player wants
        System.out.println("How many guesses would you like? ");
        int guesses = scan.nextInt();
        //Makes sure it isn't negative
        while (guesses < 1) {
            System.out.println("No negative guesses!\nHow many guesses would you like: ");
            guesses = scan.nextInt();
        }

        //Checks to see if the player wants to see total possible words
        System.out.println("Would you like to see the possible amount of words? (y/n) ");
        boolean showWords = false;
        if (scan.next().equalsIgnoreCase("y")) {
            showWords = true;
        }

        
        try {
            ArrayList<String> words = new ArrayList<>();

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String word = s.next().toUpperCase();
                if (word.length() == len) {
                    words.add(word);
                }
            }

            //Starts the game
            Game theGame = new Game(len, guesses, showWords, words);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
    }
}
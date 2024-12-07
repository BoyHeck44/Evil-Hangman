import java.util.*;

/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game {
    private boolean showWords;
    private int guesses;
    private int len;
    private ArrayList<String> words;
    private ArrayList<Character> letters;
    private String theWord;

    /**
     * Starts the game and sets all variables
     * 
     * @param len the length of the word
     * @param guesses the total number of guesses
     * @param showWords if the player wants to see total words
     * @param words all words in the txt file
     */
    public Game(int len, int guesses, boolean showWords, ArrayList<String> words) {
        this.len = len;
        this.guesses = guesses;
        this.showWords = showWords;
        this.words = words;
        this.letters = new ArrayList<>();
        this.theWord = "";
        for (int i = 0; i < this.len; i++) {
            this.theWord += "_";
        }
        //Plays game
        playGame();
    }

    /**
     * The main function that plays the game
     */
    public void playGame() {
        boolean checker = true;
        boolean switc = false;
        Scanner scan = new Scanner(System.in);

        //While loop as long as there are still guesses left
        while (this.guesses > 0) {
            //Prompts player with status quo of game
            System.out.println("Currently: " + getCurrentWord());
            System.out.println("Used letters: " + this.letters);
            System.out.println("You have " + this.guesses + " guesses remaining.");

            if (this.showWords) {
                System.out.println("Possible words remaining: " + this.words.size());
            }

            //Prompts player to guess
            System.out.println("Input your guess: ");
            char c = scan.next().toUpperCase().charAt(0);

            //If player guesses a word twice, this catches it
            while (this.letters.contains(c)) {
                System.out.println("You've already guessed that letter. Input your guess: ");
                c = scan.next().toUpperCase().charAt(0);
            }

            guess(c);
            this.letters.add(c);
            this.guesses--;

            //Checks if word is guessed
            checker = true;
            for (int i = 0; i < theWord.length(); i++) {
                if (theWord.substring(i, i + 1).equals("_")) {
                    checker = false;
                }
            }
            if (checker == true) {
                switc = true;
                break;
            }
        }

        //Decides if player wins or loses
        if (this.guesses == 0 && switc == false) {
            System.out.println("Out of guesses! The word was: " + getRandomWord());
        } else {
            System.out.println("Congratulations! You guessed the word: " + getCurrentWord());
        }
    }

    /**
     * Returns the current word
     * 
     * @return the current state of the guessed word
     */
    private String getCurrentWord() {
        return this.theWord;
    }

    /**
     * Guesses the letter chosen by the player
     * 
     * @param theGuess the guess by the player
     */
    private void guess(char theGuess) {
        Map<String, ArrayList<String>> wordFamilies = generateWordFamilies(theGuess);

        String largestFamilyKey = findLargestFamily(wordFamilies);
        this.words = wordFamilies.get(largestFamilyKey);

        updateWord(largestFamilyKey, theGuess);
    }

    private Map<String, ArrayList<String>> generateWordFamilies(char theGuess) {
        Map<String, ArrayList<String>> wordFamilies = new HashMap<>();

        for (String word : this.words) {
            StringBuilder familyBuilder = new StringBuilder();

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == theGuess) {
                    familyBuilder.append(theGuess);
                } else {
                    familyBuilder.append("_");
                }
            }

            String familyKey = familyBuilder.toString();
            if (!wordFamilies.containsKey(familyKey)) {
                wordFamilies.put(familyKey, new ArrayList<>());
            }
            wordFamilies.get(familyKey).add(word);
        }

        return wordFamilies;
    }

    private String findLargestFamily(Map<String, ArrayList<String>> wordFamilies) {
        String largestFamilyKey = "";
        int largestFamilySize = 0;

        Set<Map.Entry<String, ArrayList<String>>> entrySet = wordFamilies.entrySet();
        for (Map.Entry<String, ArrayList<String>> entry : entrySet) {
            int familySize = entry.getValue().size();
            if (familySize > largestFamilySize) {
                largestFamilySize = familySize;
                largestFamilyKey = entry.getKey();
            }
        }

        return largestFamilyKey;
    }

    private void updateWord(String familyKey, char theGuess) {
        for (int i = 0; i < familyKey.length(); i++) {
            if (familyKey.charAt(i) == theGuess) {
                this.theWord = this.theWord.substring(0, i) + theGuess + this.theWord.substring(i + 1);
            }
        }
    }

    private String getRandomWord() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(this.words.size());
        return this.words.get(randomIndex);
    }
}
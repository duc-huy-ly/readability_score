package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    private String fileName;
    private String text;
    private String[] sentences;
    private String[] words;
    private int numberOfCharacters;
    private int numberOfSyllables;
    private int numberOfPolysyllables;
    private float ARI;
    private float FK;
    private float SMOG;
    private float CL;


    public float getFK() {
        return FK;
    }

    public float getSMOG() {
        return SMOG;
    }

    public float getCL() {
        return CL;
    }

    public float getARI() {
        return ARI;
    }

    private void calculateAri() {
        ARI = (float) ((4.71 * numberOfCharacters / words.length )+ (0.5 * words.length / sentences.length) - 21.43);
    }

    private void calculateFK() {
        FK = (float )(0.39 * words.length/ sentences.length + 11.8 * numberOfSyllables / words.length - 15.59);
    }

    private void calculateSMOG() {
        SMOG = (float) (1.043 * Math.sqrt(numberOfPolysyllables * 30 / sentences.length) + 3.1291);
    }

    private void calculateCL() {
        CL = (float) ((0.0588 * numberOfCharacters / words.length * 100
                - 0.296 * sentences.length / words.length * 100) - 15.8);
    }
    public void displayInfo() {
        System.out.printf("The text is :\n");
        System.out.println(text);
        System.out.println("Words: " + words.length);
        System.out.println("Sentences: " + sentences.length);
        System.out.println("Characters: " + numberOfCharacters);
        System.out.println("Syllables: " + numberOfSyllables);
        System.out.println("Polysyllables: " + numberOfPolysyllables);
    }

    public FileReader(String fileName) {
        this.fileName = fileName;
        numberOfCharacters = 0 ;
        numberOfSyllables = 0;
        numberOfPolysyllables = 0;
    }
    public void start() {
        getTextFromFile();
        extractWordsFromText();
        extractSentencesFromText();
        searchSyllables();
        extractNumberOfCharactersFromText();
        extractNumberOfPolysyllables();
        calculateAri();
        calculateCL();
        calculateFK();
        calculateSMOG();
    }
    private void getTextFromFile() {
        try {
            text = readFileAsString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractWordsFromText() {
        words = text.split("[ ,?.!\t\n\"\']+");
    }

    public void extractNumberOfCharactersFromText() {
        numberOfCharacters = text.replaceAll("\\s","").length();
    }
    public void searchSyllables() {
        String textCopy = text;
        String textCopyNoPunctuation = text.replaceAll("[,.!]","");
        String textCopyNoEAtTheEnd = textCopyNoPunctuation.replaceAll("e\\b","");
        String textNoDoubleVowels = textCopyNoEAtTheEnd.replaceAll("[aeiouy]{2}", "a");
        String noYou = textNoDoubleVowels.replaceAll("you", "a");
        String noThe = noYou.replaceAll(" th "," a ");
        String wToWe = noThe.replaceAll(" w "," a ");
        String numbersAreCounted = wToWe.replaceAll("[0-9]+", "a");
        String onlyVowels = numbersAreCounted.replaceAll("[^aeiouy]", "");
        numberOfSyllables = onlyVowels.length();
    }
    public void extractSentencesFromText() {
        sentences = text.split("[?.!]");
    }
     public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private void extractNumberOfPolysyllables() {
        int counter = 0;
        for (String word :
                words) {
            String noE = word.replaceAll("e\\b","");
            String noYou = noE.replaceAll("([aeuioy]{2,}| [0-9])", "a");
            String noThe = noYou.replaceAll(" th "," a ");
            String wToWe = noThe.replaceAll(" w "," a ");
            String numbersAreCounted = wToWe.replaceAll("[0-9]+", "a");
            String onlyVowels = numbersAreCounted.replaceAll("[^aeiouyAEUIOY]", "");
            if (onlyVowels.length() > 2) {
                counter++;
            }
        }
        numberOfPolysyllables = counter;
    }
}

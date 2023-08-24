package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader(args[0]);
        fileReader.start();
        fileReader.displayInfo();
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String input = new Scanner(System.in).next();
        switch (input) {
            case "ARI" -> {
                printARI(fileReader);
            }
            case "FK" -> {
                printFK(fileReader);
            }
            case "SMOG" -> {
                printSMOG(fileReader);
            }
            case "CL" -> {
                printCL(fileReader);
            }
            case "all" -> {
                printARI(fileReader);
                printFK(fileReader);
                printSMOG(fileReader);
                printCL(fileReader);
                float average = (float) (AgesTable.getAgeByScore(fileReader.getARI()) + AgesTable.getAgeByScore(fileReader.getCL()) +
                        AgesTable.getAgeByScore(fileReader.getSMOG()) + AgesTable.getAgeByScore(fileReader.getFK())) / 4;
                System.out.printf("This text should be understood by average by %.2f-year-olds.\n", average);
            }
            default -> System.out.println("Error, input not ok.");
        }

    }

    private static void printCL(FileReader fileReader) {
        System.out.printf("Coleman–Liau index: %.2f ", fileReader.getCL());
        getAgeGroup(fileReader.getCL());
    }

    private static void printSMOG(FileReader fileReader) {
        System.out.printf("Simple Measure of Gobbledygook: %.2f ", fileReader.getSMOG());
        getAgeGroup(fileReader.getSMOG());
    }

    private static void printFK(FileReader fileReader) {
        System.out.printf("Flesch–Kincaid readability tests: %.2f ", fileReader.getFK());
        getAgeGroup(fileReader.getFK());
    }

    private static void printARI(FileReader file) {
        System.out.printf("Automated Readability Index: %.2f ", file.getARI());
        getAgeGroup(file.getARI());
    }

    private static void getAgeGroup(float score) {
        System.out.printf("(about %d-year-olds).\n", AgesTable.getAgeByScore(score));
    }
}
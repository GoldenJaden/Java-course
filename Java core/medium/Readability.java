package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        File file = new File(".\\\\" + fileName);
        Scanner scannerFile = new Scanner(file);
        Scanner scanner = new Scanner(System.in);
        double sentences = 0;
        double words = 0;
        double characters = 0;
        double syllables = 0;
        int polysyllables = 0;
        System.out.println("The text is:");
        while (scannerFile.hasNextLine()) {
            String line = scannerFile.nextLine();
            System.out.println(line);
            sentences += line.split("[.!?]").length;
            words += line.split(" ").length;
            characters += line.replace(" ", "").replace("\n", "").replace("\t", "").length();
            String[] wordList = line.replace("!", "").replace(".", "").replace("?", "").replace(",", "").split(" ");
            for (String wordString: wordList) {
                StringBuilder word = new StringBuilder(wordString.toLowerCase());
                int vowels = 0;
                for (int i = 0; i < word.length(); i++) {
                    char currentLetter = word.charAt(i);
                    if ("aeiouy".contains(String.valueOf(currentLetter))) {
                        if (currentLetter == 'e' && i == word.length() - 1) {
                            continue;
                        } else {
                            vowels++;
                            if (i < word.length() - 1 && "aeiouy".contains(String.valueOf(word.charAt(i + 1)))) {
                                word.setCharAt(i + 1, 'n');
                            }
                        }
                    }
                }
                vowels = vowels == 0 ? 1: vowels;
                syllables += vowels;
                if (vowels > 2) {
                    polysyllables++;
                }
            }
        }
        System.out.println();
        System.out.println("Words: " + Math.round(words));
        System.out.println("Sentences: " + Math.round(sentences));
        System.out.println("Characters: " + Math.round(characters));
        System.out.println("Syllables: " + Math.round(syllables));
        System.out.println("Polysyllables: " + polysyllables);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): all");
        String method = scanner.next();
        switch (method) {
            case "ARI":
                ARI(words, characters, sentences);
                break;
            case "FK":
                FK(words, sentences, syllables);
                break;
            case "SMOG":
                SMOG(polysyllables, sentences);
                break;
            case "CL":
                CL(characters, words, sentences);
                break;
            case "all":
                ARI(words, characters, sentences);
                FK(words, sentences, syllables);
                SMOG(polysyllables, sentences);
                CL(characters, words, sentences);
                break;
        }
        scannerFile.close();
    }

    public static void ARI(double words, double characters, double sentences) {
        double score = (double) Math.round((4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43) * 100) / 100;
        int age = score >= 14 ? 22 : (int) Math.round(Math.floor(score + 6));
        System.out.println(String.format("Automated Readability Index: %.2f (about %d-year-olds)", score, age));
    }

    public static void FK(double words, double sentences, double syllables) {
        double score = 0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
        int age = score >= 14 ? 22 : (int) Math.round(Math.floor(score + 6));
        System.out.println(String.format("Flesch–Kincaid readability tests: %.2f (about %d-year-olds)", score, age));
    }

    public static void SMOG(int polysyllables, double sentences) {
        double score = 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
        int age = score >= 14 ? 22 : (int) Math.round(Math.floor(score + 6));
        System.out.println(String.format("Simple Measure of Gobbledygook: %.2f (about %d-year-olds)", score, age));
    }

    public static void CL(double characters, double words, double sentences) {
        double L = characters / words * 100;
        double S = sentences / words * 100;
        double score = 0.0588 * L - 0.296 * S - 15.8;
        int age = score >= 14 ? 22 : (int) Math.round(Math.floor(score + 6));
        System.out.println(String.format("Coleman–Liau index: %.2f (about %d-year-olds)", score, age));
    }
}

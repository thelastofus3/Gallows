import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Gallows {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static boolean currentGuess = false;
    private static int attempts = 5;

    public static void main(String[] args) {
        startMenuLoop();
    }

    public static ArrayList<String> readWordFromFile(String filePath) {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String wordsFromFile = fileScanner.nextLine();
                words.add(wordsFromFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static String getRandomWord(ArrayList<String> words) {
        int indexWord = random.nextInt(words.size());
        return words.get(indexWord);
    }

    public static String findSecretWord() {
        String filePath = "src/russian_nouns4.txt";
        ArrayList<String> wordsFromFile = readWordFromFile(filePath);
        if (!wordsFromFile.isEmpty()) {
            return getRandomWord(wordsFromFile);
        }
        System.out.println("Error in method findSecretWord");
        return null;
    }

    public static void startMenuLoop() {
        while (true) {
            attempts = 5;
            System.out.println("[Н]овая игра или [В]ыход");

            char answer = scanner.next().charAt(0);
            if (answer == 'Н' || answer == 'н') {
                startMainLoop();
                break; // Выход из цикла после начала новой игры
            } else if (answer == 'В' || answer == 'в') {
                System.out.println("До новых встреч");
                System.exit(0);
            } else {
                System.out.println("Вы вариант которого нет.");
                System.out.println("Попробуйте снова.");
            }
        }
    }

    public static void startMainLoop() {
        String secretWord = findSecretWord();
        System.out.println(secretWord);
        ArrayList<Character> sign = inicialHiddenWord(secretWord);
        while (attempts > 0) {
            currentGuess = false; // Сброс флага перед каждой попыткой
            findingHiddenLetter(secretWord, sign,attempts);
            if (!currentGuess) {
                printGallows(attempts);
                attempts--;
            }
            if (attempts == 0) {
                System.out.println("Вы проиграли!");
                startMenuLoop();
            }
            if (!sign.contains('*')) {
                System.out.println("Поздравляем, вы отгадали слово!");
                startMenuLoop();
            }
        }
    }


    public static char getUserLetter() {
        System.out.println("Введите букву: ");
        return Character.toLowerCase(scanner.next().charAt(0));
    }

    public static ArrayList<Character> inicialHiddenWord(String secretWord) {
        ArrayList<Character> findingWordChar = new ArrayList<>();
        char characterToSecretWord;
        for (int i = 0; i < secretWord.length(); i++) {
            characterToSecretWord = secretWord.charAt(i);
            findingWordChar.add('*');
            System.out.print("* ");
        }
        System.out.println(); // Новая строка после вывода звездочек
        return findingWordChar;
    }

    public static String showFindLetters(ArrayList<Character> foundLetters) {
        Set<Character> uniqueLetters = new HashSet<>();
        for (char letter : foundLetters) {
            if (letter != '*') {
                uniqueLetters.add(letter);
            }
        }

        StringBuilder printLetters = new StringBuilder();
        for (char letter : uniqueLetters) {
            printLetters.append(letter);
        }

        return printLetters.toString();
    }



    public static void findingHiddenLetter(String secretWord, ArrayList<Character> findingWordChar, int number) {
        currentGuess = false; // Сбрасываем флаг перед каждой попыткой
        char setFindLetter = getUserLetter();

        for (int i = 0; i < secretWord.length(); i++) {
            if (setFindLetter == secretWord.charAt(i)) {
                findingWordChar.set(i, setFindLetter);
                currentGuess = true;
            }
        }

        String showLetter = showFindLetters(findingWordChar);
        if (currentGuess) {
            for (char sign : findingWordChar) {
                System.out.print(sign + " ");
            }
            System.out.println("\nВы использовали буквы: " + showLetter +" ");
        } else {
            System.out.println("Упс, такой буквы нет!");
            System.out.println("Вы использовали буквы:" + showLetter);
        }


    }

    public static void printGallows(int number) {
        switch (number) {
            case 5:
                System.out.println("У вас есть " + (number - 1) + " ошибок");
                System.out.println("|\n|\n|\n|\n|\n|\n");
                break;
            case 4:
                System.out.println("У вас есть " + (number - 1) + " ошибоки");
                System.out.println(" ___\n|/ |\n|\n|\n|\n|\n|\n");
                break;
            case 3:
                System.out.println("У вас есть " + (number - 1) + " ошибок");
                System.out.println(" ---\n|/ |\n|  *\n|\n|\n|\n|\n");
                break;
            case 2:
                System.out.println("У вас есть " + (number - 1) + " ошибок");
                System.out.println(" ---\n|/ |\n|  *\n| /||\n|  |\n|\n|\n");
                break;
            case 1:
                System.out.println("У вас есть " + (0) + " ошибка");
                System.out.println(" ---\n|/ |\n|  *\n| /||\n|  |\n| /|\n|\n");
                break;
        }
    }
}

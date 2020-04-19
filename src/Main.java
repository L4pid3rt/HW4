import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int SIZE = 5;
    public static int DOTS_TO_WIN = 4;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }

    public static boolean checkWin(char symb) {
        int symbCount;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Проверка диагонали вправо/вверх
                if (isDotExists((i - (DOTS_TO_WIN - 1)), (j + (DOTS_TO_WIN - 1)))) {
                    symbCount = 0;
                    for (int k = 0; k < DOTS_TO_WIN; k++) {
                        if (map[i - k][j + k] == symb) {
                            symbCount++;
                        }
                    }
                    if (symbCount == DOTS_TO_WIN) {
                        return true;
                    }
                }
                // Проверка линии вправо
                if (isDotExists(i, (j + (DOTS_TO_WIN - 1)))) {
                    symbCount = 0;
                    for (int k = 0; k < DOTS_TO_WIN; k++) {
                        if (map[i][j + k] == symb) {
                            symbCount++;
                        }
                    }
                    if (symbCount == DOTS_TO_WIN) {
                        return true;
                    }
                }
                //Проверка диагонали вправо/вниз
                if (isDotExists((i + (DOTS_TO_WIN - 1)), (j + (DOTS_TO_WIN - 1)))) {
                    symbCount = 0;
                    for (int k = 0; k < DOTS_TO_WIN; k++) {
                        if (map[i + k][j + k] == symb) {
                            symbCount++;
                        }
                    }
                    if (symbCount == DOTS_TO_WIN) {
                        return true;
                    }
                }
                //Проверка линии вниз
                if (isDotExists((i + (DOTS_TO_WIN - 1)), j)) {
                    symbCount = 0;
                    for (int k = 0; k < DOTS_TO_WIN; k++) {
                        if (map[i + k][j] == symb) {
                            symbCount++;
                        }
                    }
                    if (symbCount == DOTS_TO_WIN) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    public static void aiTurn() {
        int x,y;
        if (aiTryToWin()) {
            return;
        }
        if (aiTryToDestroyHuman()) {
            return;
        }
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));

        map[y][x] = DOT_O;
        System.out.println("Компьютер походил в точку " + (y + 1) + " " + (x + 1));
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y)); // while(isCellValid(x, y) == false)
        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int x, int y) {
        if (isDotExists(x, y)) {
            if (map[y][x] == DOT_EMPTY) return true;
        }
        return false;
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //Проверяет, помещается ли точка в игровое поле
    public static boolean isDotExists(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        return true;
    }

    public static boolean aiTryToWin () {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(j, i)) {
                    map[i][j] = DOT_O;
                    if (checkWin(DOT_O)) {
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        return true;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    public static boolean aiTryToDestroyHuman () {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(j, i)) {
                    map[i][j] = DOT_X;
                    if (checkWin(DOT_X)){
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        map[i][j] = DOT_O;
                        return true;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }
}

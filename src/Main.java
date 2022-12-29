import java.util.Scanner;

public class Main {

    private static final byte tableSize = 3;
    private static String[] array = new String[tableSize * tableSize];

    private static int playerNum = 0;

    public static void main(String[] args) throws Exception {
        int temp = 0;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < tableSize * tableSize; i++)
            array[i] = Integer.toString(++temp);

        while (!isGameEnd()) {
            nextPlayer();
            while (true) {
                System.out.println("\nХод игрока " + playerNum);
                showPole(); // Рисуем поле
                System.out.print("Наберите число, куда вы хотите вставить " + (1 == playerNum ? "крестик" : "нолик") + ": ");
                if (sc.hasNextInt()) { // проверяем, есть ли в потоке целое число
                    temp = sc.nextInt() - 1; // считывает целое число с потока ввода и сохраняем в переменную
                    if (isValidInput(temp))
                        break;
                }
                System.out.println("Вы ввели неправильное число. Повторите ввод");
                sc.next();
            }
            try {
                putX(temp);
            } catch (Exception e) {
                System.out.println("Что-то пошло не так ;(");
            }
        }
        showPole();
    }

    private static boolean isValidInput(int iIn) {
        if (iIn >= tableSize * tableSize) return false;
        if (iIn < 0) return false;
        switch (getX(iIn)) {
            case 'O':
            case 'X':
                return false;
        }

        return true;
    }

    private static void nextPlayer() {
        playerNum = (int) (1 == playerNum ? 2 : 1);
    }

    private static boolean isGameEnd() {
        int i, j;
        boolean bRowWin = false, bColWin = false;


        for (i = 0; i < tableSize; i++) {
            bRowWin = true;
            bColWin = true;
            for (j = 0; j < tableSize -1; j++) {
                bRowWin &= (getXY(i,j).charAt(0) == getXY(i,j+1).charAt(0));
                bColWin &= (getXY(j,i).charAt(0) == getXY(j+1,i).charAt(0));
            }
            if (bColWin || bRowWin) {
                System.out.println("Победил игрок " + playerNum);
                return true;
            }
        }

        bRowWin = true;
        bColWin = true;
        for (i=0; i< tableSize -1; i++) {
            bRowWin &= (getXY(i,i).charAt(0) == getXY(i+1,i+1).charAt(0));
            bColWin &= (getXY(i, tableSize -i-1).charAt(0) == getXY(i+1, tableSize -i-2).charAt(0));
        }
        if (bColWin || bRowWin) {
            System.out.println("Победил игрок " + playerNum);
            return true;
        }

        for (i = 0; i < tableSize * tableSize; i++) {
            switch (getX(i)) {
                case 'O':
                case 'X':
                    break;
                default:
                    return false;
            }
        }
        if (tableSize * tableSize <= i) {
            System.out.println("Ничья. Кончились ходы.");
            return true;
        }

        return false;
    }

    private static String getXY(int x, int y) {
        return array[x * tableSize + y];
    }

    private static char getX(int x) {
        return array[x].charAt(0);
    }

    private static void putX(int x) {
        array[x] = 1 == playerNum ? "X" : "O";
    }

    private static void showPole() {
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                System.out.printf("%4s", getXY(i, j));
            }
            System.out.print("\n");
        }
    }
}
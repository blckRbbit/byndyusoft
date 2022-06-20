package service;

import parser.BSCalculatorParser;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BSCalculatorService {

    private static final Logger LOGGER = Logger.getLogger(BSCalculatorService.class.getName());
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void run() {
        greet();
        while (true) {
            toInvite();
            String in = SCANNER.nextLine();
            if (isNotQuit(in)) {
                String expression = getExpression(in);
                int result = BSCalculatorParser.calculate(expression);
                LOGGER.info(String.valueOf(result));
            } else {
                sayGoodbye();
                break;
            }
        }
    }

    private static boolean isNotQuit(String in) {
        return !in.equalsIgnoreCase("q");
    }

    private static void greet() {
        LOGGER.log(Level.INFO, "Это приложение - консольный калькулятор.\n\n" +
                "Передайте арифметическое выражение, чтобы получить результат его вычислений.\n" +
                "Для выхода нажмите q\n");
    }

    private static void toInvite() {
        LOGGER.log(Level.INFO, "Введите выражение для расчета:\n");
    }

    private static void sayGoodbye() {
        LOGGER.log(Level.INFO, "Спасибо за использование приложения!\n");
    }

    private static String getExpression(String in) {
        return in;
    }

    /**
     * Геттеры для модульного тестирования.
     */

    public static boolean isNotQuitForTest(String in) {
        return isNotQuit(in);
    }

    public static void greetForTest() {
        greet();
    }

    public static void toInviteForTest() {
        toInvite();
    }

    public static void sayGoodbyeForTest() {
        sayGoodbye();
    }

    public static String getExpressionForTest(String in) {
        return getExpression(in);
    }

}

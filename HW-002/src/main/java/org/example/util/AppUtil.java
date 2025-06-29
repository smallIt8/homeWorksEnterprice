package org.example.util;

import java.util.function.IntConsumer;

import static org.example.util.constant.ColorsConstant.*;
import static org.example.util.constant.ExceptionMessage.ERROR_ENTER_MAX_ATTEMPTS_MESSAGE;
import static org.example.util.constant.MenuPersonConstant.FINISHING_MESSAGE;

public final class AppUtil {
    public static final int ITERATION_LOOP = 5;
    public static final int ITERATION_LOOP_TO_MESSAGE = 4;

    public static void exit() {
        System.out.println(FINISHING_MESSAGE);
        System.exit(0);
    }

    public static void exitByFromAttempt() {
        System.out.println(ERROR_ENTER_MAX_ATTEMPTS_MESSAGE);
        exit();
    }

    public static void loopIterationAndExit(IntConsumer method, int maxCounts) {
        try {
            for (int i = 0; i < maxCounts; i++) {
                method.accept(i);
                if (i == maxCounts - 1) {
                    exitByFromAttempt();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String colorizeGreenText(String text) {
        try {
            return GREEN + text + RESET;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String colorizeRedText(String text) {
        try {
            return RED + text + RESET;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String colorizeOrangeText(String text) {
        try {
            return ORANGE + text + RESET;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String colorizeNumber(String text, int number) {
        try {
            return GREEN + number + ". " + RESET + text;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public AppUtil() {
    }
}
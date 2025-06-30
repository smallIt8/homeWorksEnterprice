package org.example.util;

import lombok.experimental.UtilityClass;

import java.util.function.IntConsumer;

import static org.example.util.constant.ColorsConstant.*;
import static org.example.util.constant.ExceptionMessageConstant.ERROR_ENTER_MAX_ATTEMPTS_MESSAGE;
import static org.example.util.constant.MenuPersonConstant.FINISHING_MESSAGE;

@UtilityClass

public class AppUtil {
    public final int ITERATION_LOOP = 5;
    public final int ITERATION_LOOP_TO_MESSAGE = 4;

    public void exit() {
        System.out.println(FINISHING_MESSAGE);
        System.exit(0);
    }

    public void exitByFromAttempt() {
        System.out.println(ERROR_ENTER_MAX_ATTEMPTS_MESSAGE);
        exit();
    }

    public void loopIterationAndExit(IntConsumer method, int maxCounts) {
        try {
            for (int i = 0; i < maxCounts; i++) {
                method.accept(i);
                if (i == maxCounts - 1) {
                    exitByFromAttempt();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String colorizeGreenText(String text) {
        return GREEN + text + RESET;
    }

    public String colorizeRedText(String text) {
        return RED + text + RESET;
    }

    public String colorizeOrangeText(String text) {
        return ORANGE + text + RESET;
    }

    public String colorizeNumber(String text, int number) {
        return GREEN + number + ". " + RESET + text;
    }
}
package org.example.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.example.util.constant.ColorsConstant.*;
import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MAX_ATTEMPTS_MESSAGE;
import static org.example.util.constant.MenuConstant.FINISHING_MESSAGE;

@UtilityClass

public class AppUtil {
	public final int MAX_ITERATION_LOOP = 5;
	public final int MAX_ITERATION_LOOP_TO_MESSAGE = 4;

	public void exit() {
		System.out.println(FINISHING_MESSAGE);
		System.exit(0);
	}

	public void exitByFromAttempt() {
		System.out.println(ERROR_ENTER_MAX_ATTEMPTS_MESSAGE);
		exit();
	}

	public void loopIterationAndExit(Function<Integer, Boolean> method, int maxIteration) {
		try {
			for (int i = 0; i < maxIteration; i++) {
				boolean stop = method.apply(i);
				if (stop) {
					break;
				} else if (i == maxIteration - 1) {
					exitByFromAttempt();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public <T> Optional<T> loopIterationWithReturnAndExit(Function<Integer, Optional<T>> function, int maxIteration) {
		try {
			for (int i = 0; i < maxIteration; i++) {
				Optional<T> result = function.apply(i);
				if (result.isPresent()) {
					return result;
				}
				if (i == maxIteration - 1) {
					exitByFromAttempt();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	public <T> void printNumberedList(List<T> items) {
		for (int i = 0; i < items.size(); i++) {
			String numberOfList = colorizeNumber(items.get(i).toString(), i + 1);
			System.out.println(numberOfList);
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
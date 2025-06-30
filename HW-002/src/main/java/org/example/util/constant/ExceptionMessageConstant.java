package org.example.util.constant;

import lombok.experimental.UtilityClass;
import org.example.util.AppUtil;

@UtilityClass

public class ExceptionMessageConstant {
    // ========== Сообщения об ошибках создания ==========
    public final String ERROR_CREATION_EMAIL_MESSAGE = AppUtil.colorizeRedText("Пользователь с таким email уже существует.");

    // ========== Сообщения об ошибках ввода ==========
    public final String ERROR_ENTER_MESSAGE = AppUtil.colorizeRedText("Неверный ввод.\nповторите ввод: ");
    public final String ERROR_ENTER_MAX_ATTEMPTS_MESSAGE = AppUtil.colorizeRedText("Превышено допустимое количество попыток ввода.");
    public final String ERROR_ENTER_FIRST_NAME_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            Имя должно содержать от 2 до 50 символов
            включая буквы.
            """
    );
    public final String ERROR_ENTER_LAST_NAME_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            Фамилия должна содержать от 2 до 50 символов
            включая буквы.
            """
    );
    public final String ERROR_ENTER_EMAIL_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            Email должен быть в формате '*****@****.***',
            содержать от 4 до 25 символов перед '@', включая буквы, цифры и специальные символы '_-',
            содержать от 2 до 25 символов между '@' и '.', включая буквы, цифры и специальный символ '-',
            содержать от 2 до 10 символов после '.', включая только буквы.
            """
    );
    public final String ERROR_ENTER_SALARY_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            Зарплата должна быть в формате '*****.**',
            должна содержать от 1 до 10 символов перед '.', включая только цифры,
            может содержать до 2 символов после '.', включая только цифры.
            """
    );
    public final String ERROR_ENTER_DEPARTMENT_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            Название департамента должно содержать от 2 до 50 символов,
            должен начинаться с буквы или цифры
            включая буквы, цифры, пробелы и
            специальные символы '.,-_:'
            """
    );
    public final String ERROR_ENTER_UUID_MESSAGE = AppUtil.colorizeRedText("""
            Неверный ввод.
            ID сотрудника должен быть в формате UUID (********-****-****-****-************),
            содержать 8 символов перед первым дефисом,
            содержать 4 символа между первым и вторым дефисом,
            содержать 4 символа между вторым и третьим дефисом,
            содержать 4 символа между третьим и четвертым дефисом,
            содержать 12 символов после четвертого дефиса
            и состоять только из латинских букв от A до F в верхнем и нижнем регистре и цифр 0–9.
            """);
}
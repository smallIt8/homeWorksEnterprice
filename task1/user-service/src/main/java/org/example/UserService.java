package org.example;
import org.example.RecordingUserDatabase;

public class UserService {

    public void createUser() {
        System.out.println("Создание пользователя.....");
        new RecordingUserDatabase().recording();
    }
}
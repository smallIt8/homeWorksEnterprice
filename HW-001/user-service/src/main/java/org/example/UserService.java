package org.example;

public class UserService {

    public void createUser() {
        System.out.println("Создание пользователя.....");
        new RecordingUserDatabase().recording();
    }
}
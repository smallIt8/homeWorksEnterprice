import org.example.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск приложения.....");
        new UserService().createUser();
    }
}
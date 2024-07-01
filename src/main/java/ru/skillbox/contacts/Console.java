package ru.skillbox.contacts;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду: ");
        String command = scanner.nextLine();
        System.out.println("Команда: " + command);
    }
}

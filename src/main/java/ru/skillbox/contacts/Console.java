package ru.skillbox.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Console implements ApplicationRunner {
    private final ContactsService contactsService;

    @Override
    public void run(ApplicationArguments args) {
        boolean running = true;

        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите действие:\n" +
                    "1 - Просмотреть все контакты\n" +
                    "2 - Добавить контакт\n" +
                    "3 - Удалить контакт\n" +
                    "4 - Сохранить контакты в файл\n" +
                    "0 - Выход"
            );
            String command = scanner.nextLine();

            switch (command) {
                case "1" -> {
                    contactsService.print();
                }
                case "2" -> {
                    System.out.println("Введите данные");
                    String contactData = scanner.nextLine();
                    if (contactsService.add(contactData)) {
                        System.out.println("Контакт добавлен");
                    }
                }
                case "3" -> {
                    System.out.println("Введите email");
                    String email = scanner.nextLine();
                    if (contactsService.delete(email)) {
                        System.out.println("Контакт удалён");
                    }
                }
                case "4" -> {
                    try {
                        contactsService.write();
                        System.out.println("Данные сохранены");
                    } catch (FileNotFoundException e) {
                        System.err.println("Файл не найден");
                    }
                }
                case "0" -> {
                    running = false;
                    System.out.println("До встречи!");
                }
            }
        }
    }
}

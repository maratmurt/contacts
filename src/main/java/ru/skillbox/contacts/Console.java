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
    private boolean running;
    private Scanner scanner;

    @Override
    public void run(ApplicationArguments args) {
        running = true;

        while (running) {
            scanner = new Scanner(System.in);
            System.out.println("\nВыберите действие:\n" +
                    "1 - Просмотреть все контакты\n" +
                    "2 - Добавить контакт\n" +
                    "3 - Удалить контакт\n" +
                    "4 - Сохранить контакты в файл\n" +
                    "0 - Выход"
            );
            String command = scanner.nextLine();

            handleInput(command);
        }
    }

    private void handleInput(String command) {
        switch (command) {
            case "1" -> {
                if (contactsService.contactsCount() == 0) {
                    System.out.println("Список контактов пуст");
                    break;
                }
                contactsService.print();
            }
            case "2" -> {
                System.out.println("Введите данные в следующем формате:\n" +
                        "Фамилия Имя Отчество;+890999999;user@domain.name");
                String contactData = scanner.nextLine();
                try {
                    contactsService.add(contactData);
                    System.out.println("Контакт добавлен");
                } catch (AddContactException e) {
                    System.err.println(e.getMessage());
                }
            }
            case "3" -> {
                System.out.println("Введите email");
                String email = scanner.nextLine();
                if (contactsService.delete(email)) {
                    System.out.println("Контакт удалён");
                } else {
                    System.err.println("Контакт не найден!");
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
            default -> {
                System.err.println("Не корректный ввод!\n");
            }
        }
    }
}

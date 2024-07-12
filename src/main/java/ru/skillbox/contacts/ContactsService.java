package ru.skillbox.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ContactsService {
    private final Set<Contact> contacts;

    @Value("${app.write_path}")
    private String writePath;

    public void add(String contactData) throws AddContactException {
        String[] fields = contactData.split(";");
        if (fields.length != 3) {
            throw new AddContactException("Неверный формат ввода!");
        }

        Contact newContact = new Contact();

        String fullName = fields[0];
        if (isValidName(fullName)) {
            newContact.setFullName(fullName);
        }

        String phoneNumber = fields[1];
        if (isValidAndNotPresentPhoneNumber(phoneNumber)) {
            newContact.setPhoneNumber(phoneNumber);
        }

        String email = fields[2].toLowerCase();
        if (isValidAndNotPresentEmail(email)) {
            newContact.setEmail(email);
        }

        contacts.add(newContact);
    }

    private boolean isValidName(String fullName) throws AddContactException {
        if (!fullName.matches("([А-Яа-яЁё]+\\s){2}[А-Яа-яЁё]+")) {
            throw new AddContactException("Введено не корректное Ф.И.О.");
        }
        return true;
    }

    private boolean isValidAndNotPresentEmail(String email) throws AddContactException {
        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            throw new AddContactException("Введён не корректный адрес электронной почты");
        }
        Optional<Contact> sameEmailContact = contacts.stream()
                .filter(contact -> contact.getEmail().equals(email))
                .findAny();
        if (sameEmailContact.isPresent()) {
            throw new AddContactException("Электронная почта совпадает с: " + sameEmailContact.get());
        }
        return true;
    }

    private boolean isValidAndNotPresentPhoneNumber(String phoneNumber) throws AddContactException {
        if (!phoneNumber.matches("\\+\\d+")) {
            throw new AddContactException("Введён не корректный номер телефона");
        }
        Optional<Contact> samePhoneNumberContact = contacts.stream()
                .filter(contact -> contact.getPhoneNumber().equals(phoneNumber))
                .findAny();
        if (samePhoneNumberContact.isPresent()) {
            throw new AddContactException("Номер телефона совпадает с: " + samePhoneNumberContact.get());
        }
        return true;
    }

    public boolean delete(String email) {
        return contacts.removeIf(c -> email.equals(c.getEmail()));
    }

    public void print() {
        contacts.forEach(System.out::println);
    }

    public void write() throws FileNotFoundException {
        StringBuilder contactsString = new StringBuilder();
        contacts.forEach(c -> {
            if (!contactsString.isEmpty()) {
                contactsString.append("\n");
            }
            contactsString.append(c.getFullName())
                    .append(";")
                    .append(c.getPhoneNumber())
                    .append(";")
                    .append(c.getEmail());
        });
        try (PrintWriter writer = new PrintWriter(writePath)) {
            writer.write(contactsString.toString());
        }
    }

    public int contactsCount() {
        return contacts.size();
    }
}

package ru.skillbox.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactsService {
    private final List<Contact> contacts;

    @Value("${app.write_path}")
    private String writePath;

    public boolean add(String contactData) {
        String[] fields = contactData.split(";");
        Contact contact = new Contact();
        contact.setFullName(fields[0]);
        contact.setPhoneNumber(fields[1]);
        contact.setEmail(fields[2]);
        return contacts.add(contact);
    }

    public boolean delete(String email) {
        return contacts.removeIf(c -> email.equals(c.getEmail()));
    }

    public void print() {
        StringBuilder builder = new StringBuilder();
        contacts.forEach(c -> {
            if (!builder.isEmpty()) {
                builder.append("\n");
            }
            builder.append(c.getFullName())
                    .append(" | ")
                    .append(c.getPhoneNumber())
                    .append(" | ")
                    .append(c.getEmail());
        });
        System.out.println(builder);
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
}

package ru.skillbox.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactsService {
    private final List<Contact> contacts;

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

    public void printAll() {
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
}

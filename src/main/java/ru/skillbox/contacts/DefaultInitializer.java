package ru.skillbox.contacts;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultInitializer {
    @Value("${app.default_data_path}")
    private String filePath;

    private final ContactsService service;

    @PostConstruct
    public void initialize() throws IOException {
        List<String> contacts = Files.readAllLines(Path.of(filePath));
        contacts.forEach(service::add);
    }
}

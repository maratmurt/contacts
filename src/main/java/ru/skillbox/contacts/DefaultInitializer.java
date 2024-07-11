package ru.skillbox.contacts;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@Profile("init")
@RequiredArgsConstructor
public class DefaultInitializer {
    @Value("${app.default_data_path}")
    private String filePath;

    private final ContactsService service;

    @PostConstruct
    public void initialize() throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        File file = resource.getFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines().forEach(service::add);
    }
}

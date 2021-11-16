package com.example.filesystem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.example.filesystem.exception.FileProcessingException;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractMapper {
    @Value("${upload.path}")
    private static String uploadPath;
    public List<String> getFilesByCriteria(String  criteria) {
        try {
            Path root = Paths.get(uploadPath);
            if (Files.exists(root)) {
                return Files.walk(root, 1)
                        .filter(path -> !path.equals(root))
                        .map(Path::getFileName)
                        .filter(f -> f.getFileName().toString().contains(criteria))
                        .map(Path::toString)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new FileProcessingException("Can't get files with customer: " + criteria, e);
        }
        return Collections.emptyList();
    }
}

package com.example.filesystem.validator;

import org.springframework.stereotype.Component;

@Component
public class FileValidator {
    private static final String FILE_NAME_REGEX =
            "^[A-Za-z]+\\_[A-Za-z]+\\_([0-9]{2}).([0-9]{2}).([0-9]{4}).(xml)$";

    public boolean isValidName(String fileName) {
        return fileName != null && fileName.matches(FILE_NAME_REGEX);
    }
}

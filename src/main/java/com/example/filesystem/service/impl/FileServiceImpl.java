package com.example.filesystem.service.impl;

import com.example.filesystem.service.AbstractMapper;
import com.example.filesystem.validator.FileValidator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.example.filesystem.exception.FileProcessingException;
import com.example.filesystem.service.FileService;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl extends AbstractMapper implements FileService {
    private final FileValidator fileValidator;
    @Value("${upload.path}")
    private String uploadPath;

    public FileServiceImpl(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new FileProcessingException("Can't create folder: " + uploadPath, e);
        }
    }

    @Override
    public void save(MultipartFile file, String filename) throws FileProcessingException {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            if (!fileValidator.isValidName(filename)) {
                throw new FileProcessingException("Invalid file name.");
            }
            Files.copy(file.getInputStream(), root.getFileName());
        } catch (Exception e) {
            throw new FileProcessingException("Can't save file with name: " + filename, e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.delete(Path.of(fileName));
        } catch (IOException e) {
            throw new FileProcessingException("Can't delete the file with name: " + fileName, e);
        }
    }

    @Override
    public Document getDataByFileName(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(fileName));
            return document;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new FileProcessingException("Can't get data from file: " + fileName, e);
        }
    }
}

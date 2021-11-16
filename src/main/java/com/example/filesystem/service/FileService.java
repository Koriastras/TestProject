package com.example.filesystem.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

public interface FileService {
    void save(MultipartFile file, String filename);

    void delete(String fileName);

    Document getDataByFileName(String fileName);

    List<String> getFilesByCriteria(String criteria);

}

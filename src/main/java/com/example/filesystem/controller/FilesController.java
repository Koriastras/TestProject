package com.example.filesystem.controller;

import java.util.List;
import com.example.filesystem.service.FileService;
import com.example.filesystem.service.mapper.FileDataMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FileService fileService;
    private final FileDataMapper fileDataMapper;

    public FilesController(FileService fileService, FileDataMapper fileDataMapper) {
        this.fileService = fileService;
        this.fileDataMapper = fileDataMapper;
    }

    @PostMapping
    public void save(@RequestParam(value = "file") MultipartFile file, String filename) {
        fileService.save(file, filename);
    }

    @GetMapping("/byName/{filename:.+}")
    @ResponseBody
    public String getDataFromFileByFilename(@PathVariable String filename) {
        return fileDataMapper.toJson(fileDataMapper.toModel(fileService.getDataByFileName(filename)));
    }

    @DeleteMapping("{fileName:.+}")
    public void deleteFileByName(@PathVariable String fileName) {
        fileService.delete(fileName);
    }

    @GetMapping("/byCustomer/{customer:.+}")
    public List<String> getFilesByCustomer(@PathVariable String customer) {
        return fileService.getFilesByCriteria(customer);
    }

    @GetMapping("/byType/{type:.+}")
    public List<String> getFilesByType(@PathVariable String type) {
        return fileService.getFilesByCriteria(type);
    }

    @GetMapping("/byDate/{date:.+}")
    public List<String> getFilesByDate(@PathVariable String date) {
        return fileService.getFilesByCriteria(date);
    }
}

package com.example.filesystem.service.mapper;

import com.example.filesystem.exception.FileProcessingException;
import com.example.filesystem.model.FileDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class FileDataMapper {
    private final ObjectMapper objectMapper;

    public FileDataMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public FileDataDto toModel(Document document) {
        FileDataDto fileDataDto = new FileDataDto();
        NodeList list = document.getElementsByTagName("FileData");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String customer = element.getElementsByTagName("customer").item(0).getTextContent();
                String type = element.getElementsByTagName("type").item(0).getTextContent();
                String date = element.getElementsByTagName("date").item(0).getTextContent();
                fileDataDto.setCustomer(customer);
                fileDataDto.setType(type);
                fileDataDto.setDate(date);
            }
        }
        return fileDataDto;
    }

    public String toJson(FileDataDto fileDataDto) {
        try {
            return objectMapper.writeValueAsString(fileDataDto);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Can't convert data to JSON format: " + fileDataDto, e);
        }
    }
}

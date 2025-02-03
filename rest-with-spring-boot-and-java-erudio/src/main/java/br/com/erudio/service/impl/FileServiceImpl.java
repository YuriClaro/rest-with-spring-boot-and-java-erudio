package br.com.erudio.service.impl;

import br.com.erudio.service.FileService;
import br.com.erudio.service.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Value("${filePath}")
    private String filePath;

    @Override
    public String uploadFile(MultipartFile file) {
        logger.info("Starting upload...");
        try {
            if (!file.getOriginalFilename().endsWith(".jpeg") && !file.getOriginalFilename().endsWith(".jpg")) {
                throw new IllegalArgumentException("Invalid file type. Only JPEG files are allowed.");
            }
            if (file.getSize() > 2_000_000) {
                throw new IllegalArgumentException("File is too large. The size limit is 1MB.");
            }

            file.transferTo(new File(filePath + file.getOriginalFilename()));
            logger.info("File uploaded successfully.");
            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (Exception e) {
            logger.info("Could not upload the file.");
            throw new RuntimeException("Could not upload the file: " + e.getMessage());
        }
    }

    @Override
    public Resource downloadFile(String fileName) {
        logger.info("Starting download...");

        try {
            Path path = Paths.get(filePath).resolve(fileName).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            logger.info("File downloaded successfully.");
            return resource;
        } catch (Exception e) {
            logger.info("Error downloading the file.");
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }
}

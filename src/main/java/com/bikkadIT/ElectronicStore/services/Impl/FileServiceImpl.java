package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.exceptions.BadApiException;
import com.bikkadIT.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        logger.info("Upload File Service is being initiated");

        String originalFilename = file.getOriginalFilename();

        logger.info("File name : {} ", originalFilename);

        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        logger.info("Full image path : {} ", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            //file save
            logger.info("File extension is  : {} ", extension);
            File folder = new File(path);

            if (!folder.exists()) {
                // if path not present create the folder
                folder.mkdirs();        //mkdir()=create single folder & mkdirs()=create folder wid sub folder
            }

            //upload file on this path
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;        //return file name only or we can return path also

        } else {
            throw new BadApiException("File with this " + extension + " not allowed !!!");
        }

    }


    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        logger.info("Get File Resource method is initiated");

        String fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }

}

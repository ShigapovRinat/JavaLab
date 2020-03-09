package ru.javalab.downloadingFiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.javalab.downloadingFiles.models.FileInfo;
import ru.javalab.downloadingFiles.repositories.FilesInfoRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FilesInfoServiceImpl implements FilesInfoService {

    @Autowired
    private FilesInfoRepository filesInfoRepository;

    @Autowired
    private Environment environment;


    public FileInfo getFileInfoByStorageFilename(String filename) {
        return filesInfoRepository.find(filename)
                .orElseThrow(() -> new IllegalArgumentException("FileInfo not found"));
    }


    @Override
    public void save(FileInfo fileInfo, MultipartFile multipartFile) {
        String storageFileName = UUID.randomUUID().toString();;
        String originalFilename = multipartFile.getOriginalFilename();
        Long size = multipartFile.getSize();
        String type = multipartFile.getContentType();
        String path = environment.getProperty("storage.path") + "/" + storageFileName;


        fileInfo.setStorageFileName(storageFileName);
        fileInfo.setOriginalFileName(originalFilename);
        fileInfo.setSize(size);
        fileInfo.setType(type);
        fileInfo.setPath(path);

        File storageFile = new File(path);
        try {
            multipartFile.transferTo(storageFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        filesInfoRepository.save(fileInfo);
    }
}

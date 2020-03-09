package ru.javalab.downloadingFiles.services;

import org.springframework.web.multipart.MultipartFile;
import ru.javalab.downloadingFiles.models.FileInfo;

public interface FilesInfoService {
    void save(FileInfo fileInfo, MultipartFile multipartFile);
    FileInfo getFileInfoByStorageFilename(String filename);
}

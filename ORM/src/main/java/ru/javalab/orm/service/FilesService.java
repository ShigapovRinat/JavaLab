package ru.javalab.orm.service;

import org.springframework.stereotype.Component;
import ru.javalab.orm.dto.InformationDto;

@Component
public interface FilesService {
    void init();

    void convert();

    void convertPngByUser(Long userId);

    InformationDto getInformation(Long userId);
}

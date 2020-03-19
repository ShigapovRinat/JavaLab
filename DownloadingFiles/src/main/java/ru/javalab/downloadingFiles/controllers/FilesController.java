package ru.javalab.downloadingFiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.javalab.downloadingFiles.models.FileInfo;
import ru.javalab.downloadingFiles.services.FilesInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class FilesController {

    @Autowired
    private FilesInfoService filesInfoService;

    @Autowired
    private Environment environment;


    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public ModelAndView uploadFileView(@RequestParam("file") MultipartFile multipartFile, @RequestParam("email") String email) {
        System.out.println(email);
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .email(email)
                .build();

        filesInfoService.save(fileInfo, multipartFile);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("file_url", fileInfo.getStorageFileName());
        modelAndView.setViewName("uploaded_success");
        return modelAndView;
    }


    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView openPage() {
        return new ModelAndView("file_upload");
    }


    // localhost:8080/files/123809183093qsdas09df8af.jpeg
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public ModelAndView getFile(HttpServletResponse response,
                                @PathVariable("file-name") String fileName) {
        try {
            FileInfo fileInfo = filesInfoService.getFileInfoByStorageFilename(fileName);
            File file = new File(environment.getProperty("storage.path"), fileName);
            ModelAndView modelAndView = new ModelAndView();
            if (file.exists()) {
                response.setContentType(fileInfo.getType());
                response.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getOriginalFileName());
                response.setContentLength((int) file.length());

                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int b;

                while ((b = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }

                fis.close();
                os.close();
                return new ModelAndView("success");
            } else {
                modelAndView.addObject("fileName", fileName);
                modelAndView.setViewName("unhappy");
                return modelAndView;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}


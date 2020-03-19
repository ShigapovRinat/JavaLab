package ru.javalab.downloadingFiles.models;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> model;
}

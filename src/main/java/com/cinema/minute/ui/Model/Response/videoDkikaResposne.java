package com.cinema.minute.ui.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class videoDkikaResposne {
    private String description;
    private String videoUrl;
    private String editor;
    private String videoName;

    private Resource fileInfo;

}

package com.cinema.minute.ui.Model.Response.CompteRendu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteRenduList {
    private String username;
    private String courname;
    private Integer fileId;
    private LocalDateTime localDateTime;
}

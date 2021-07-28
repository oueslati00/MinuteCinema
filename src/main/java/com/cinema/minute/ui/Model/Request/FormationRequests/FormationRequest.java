package com.cinema.minute.ui.Model.Request.FormationRequests;

import com.cinema.minute.Data.Entity.Chapter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
@ToString
@Data
public class FormationRequest {

    private Integer formateurId;
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate FirstDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate FinalDate;
    private String description;
    private List<ChapterRequest> Chapter;


}

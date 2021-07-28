package com.cinema.minute.ui.Model.Request.FormationRequests;


import lombok.Data;

import java.util.List;

@Data
public class ChapterRequest {

    private String name;
    private List<CoursRequest> cours;
}

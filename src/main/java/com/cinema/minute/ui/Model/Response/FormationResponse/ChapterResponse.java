package com.cinema.minute.ui.Model.Response.FormationResponse;

import com.cinema.minute.ui.Model.Request.FormationRequests.CoursRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse {
    private Long id;
    private String name;
    private List<CoursResponse> cour;
}

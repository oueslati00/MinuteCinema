package com.cinema.minute.ui.Model.Request.AddFormationrequest;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ChapterRequest {

    private String name;

    private List<CoursRequest> coursRequests;
}

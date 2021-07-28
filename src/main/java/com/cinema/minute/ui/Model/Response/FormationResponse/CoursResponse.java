package com.cinema.minute.ui.Model.Response.FormationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursResponse {
    private Long id;
    private String name;
    private String description;
}

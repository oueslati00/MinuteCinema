package com.cinema.minute.ui.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {
    private long FormationNumber;
    private long VideoNumber;
    private long CompteRenduNumber;
}

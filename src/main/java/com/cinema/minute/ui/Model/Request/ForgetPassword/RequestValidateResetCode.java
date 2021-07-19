package com.cinema.minute.ui.Model.Request.ForgetPassword;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestValidateResetCode {

    private String email;
    private String code;
}

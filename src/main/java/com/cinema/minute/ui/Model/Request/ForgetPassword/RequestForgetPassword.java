package com.cinema.minute.ui.Model.Request.ForgetPassword;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class RequestForgetPassword {
    @NotBlank
    private String email;
}

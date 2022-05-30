package com.jorge.backend.portfolio.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    @NotNull
    @Email(message = "Username must be an email")
    private String username;

    @NotNull
    @Size(min = 8, message = "Password higher than 8")
    private String password;
}

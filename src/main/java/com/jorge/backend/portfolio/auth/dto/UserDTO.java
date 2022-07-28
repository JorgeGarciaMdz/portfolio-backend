package com.jorge.backend.portfolio.auth.dto;


import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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

    @NotNull
    @Size(min = 3, max = 150)
    private String name;

    @NotNull
    @Size(min = 3, max = 150)
    private String lastname;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull
    @Size(min = 3, max = 100)
    private String nationality;

    @NotNull
    @Size(min = 3, max = 250)
    private String aboutMe;

    @NotNull
    @Size(min = 3, max = 150)
    private String occupation;

    @NotNull
    @Size(min = 3, max = 250)
    private String imageUrl;
}

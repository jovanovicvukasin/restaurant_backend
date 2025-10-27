package com.vukasin.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @NotBlank(message = "Phone number cannot be empty")
    private String phone;
    private String address;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}

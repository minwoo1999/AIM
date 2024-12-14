package com.example.aim.security.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateSecurityRequestDto {

    @NotBlank(message = "Security code cannot be blank")
    private String securityCode;

    @NotNull(message = "Price cannot be null")
    private Double price;
}
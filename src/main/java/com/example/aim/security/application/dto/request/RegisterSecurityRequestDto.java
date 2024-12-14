package com.example.aim.security.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Getter
public class RegisterSecurityRequestDto {

    @NotBlank(message = "Security code cannot be blank")
    private String securityCode;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

}
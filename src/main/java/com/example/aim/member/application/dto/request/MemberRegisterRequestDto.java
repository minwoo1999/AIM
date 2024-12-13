package com.example.aim.member.application.dto.request;


import com.example.aim.member.domain.MemberRole;
import com.example.aim.member.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters.")
    private String password;

    public UserEntity toEntity(String username, String password){
        return UserEntity.builder()
                .username(username)
                .passwordHash(password)
                .role(MemberRole.MEMBER)
                .build();
    }
}

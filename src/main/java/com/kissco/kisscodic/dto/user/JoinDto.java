package com.kissco.kisscodic.dto.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kissco.kisscodic.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(max = 100, min = 3)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(max = 100, min = 3)
    private String password2;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    public static User createUserEntity(JoinDto joinDto) {
        User user = new User();
        user.setUsername(joinDto.getUsername());
        user.setPassword(joinDto.password);
        user.setEmail(joinDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}

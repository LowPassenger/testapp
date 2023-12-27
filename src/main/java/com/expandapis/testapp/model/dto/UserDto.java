package com.expandapis.testapp.model.dto;

import com.expandapis.testapp.util.AppConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @JsonProperty("username")
    @NotNull
    @NotBlank(message = "Username is mandatory")
    @Size(max = AppConstants.MAX_USERNAME_LENGTH, message = "Username must contain no more than "
            + AppConstants.MAX_USERNAME_LENGTH + " characters")
    private String username;

    @JsonProperty("password")
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @Override
    public String toString() {
        return "UserDto{"
                + "username='" + username + '\''
                + ", password='" + "OK" + '\''
                + '}';
    }
}

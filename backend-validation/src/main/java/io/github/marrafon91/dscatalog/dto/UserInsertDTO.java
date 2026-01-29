package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @NotBlank
    private  String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

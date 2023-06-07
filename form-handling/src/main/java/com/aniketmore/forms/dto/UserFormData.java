package com.aniketmore.forms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFormData {

    @Size(min = 3, message = "name must have at least 3 letters")
    String name;

    @Email(message = "email must be valid email")
    String email;

}

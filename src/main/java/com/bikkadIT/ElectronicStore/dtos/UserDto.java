package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.CustomFields;
import com.bikkadIT.ElectronicStore.validate.imageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends CustomFieldsDto {

    private String userId;

    @Size(min = 3, max = 15, message = "Invalid Name !!")
    private String name;

    //@Email(message = "Invalid User Email !!")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid User Email ")
    @NotBlank(message = "Invalid User Email !!")
    private String email;

    @NotBlank(message = "Password is necessary")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender !!")
    private String gender;

    @NotBlank(message = "Write about yourself !!")
    private String about;

    @imageNameValid
    private String imageName;

}

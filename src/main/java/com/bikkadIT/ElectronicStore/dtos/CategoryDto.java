package com.bikkadIT.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 4,message = "Title must be of minimum 4 characters")
    private String title;

    @NotBlank(message = "Description is Required !!")
    private String description;

    private String coverImage;
}

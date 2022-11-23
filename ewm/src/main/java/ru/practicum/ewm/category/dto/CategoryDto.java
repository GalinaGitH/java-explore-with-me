package ru.practicum.ewm.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class CategoryDto {
    private Long id;

    @NotBlank
    private String name;

}

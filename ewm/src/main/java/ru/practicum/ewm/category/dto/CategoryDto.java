package ru.practicum.ewm.category.dto;

import lombok.*;
import ru.practicum.ewm.user.Create;
import ru.practicum.ewm.user.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class CategoryDto {
    @NotBlank(groups = {Update.class})
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    @Size(min = 1, max = 255)
    private String name;

}

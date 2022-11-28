package ru.practicum.ewm.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}

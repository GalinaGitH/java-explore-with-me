package ru.practicum.ewm.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class UserShortDto {
    private Long id;
    private String name;
}

package ru.practicum.ewm.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class NewCompilationDto {
    @NotBlank
    private String title;
    private boolean pinned;
    private Set<Long> events;
}

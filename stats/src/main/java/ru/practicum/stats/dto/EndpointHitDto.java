package ru.practicum.stats.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class EndpointHitDto {
    @NotBlank
    private String app; //Название сервиса
    @NotBlank
    private String uri;//URI сервиса
    @NotBlank
    private String ip;//IP-адрес пользователя, осуществившего запрос
    @NotBlank
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

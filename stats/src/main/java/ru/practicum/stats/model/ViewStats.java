package ru.practicum.stats.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStats {

    private String app; //Название сервиса
    private String uri;//URI сервиса
    private Long hits; //Количество просмотров

}

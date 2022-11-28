package ru.practicum.stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "hits")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app; //Название сервиса
    private String uri;//URI сервиса
    private String ip;//IP-адрес пользователя, осуществившего запрос
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

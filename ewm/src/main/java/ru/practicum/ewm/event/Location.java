package ru.practicum.ewm.event;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class Location {

    private double lat;
    private double lon;
}

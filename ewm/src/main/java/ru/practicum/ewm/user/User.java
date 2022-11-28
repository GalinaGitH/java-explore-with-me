package ru.practicum.ewm.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",
            nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;
}



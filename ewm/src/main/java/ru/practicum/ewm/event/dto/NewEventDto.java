package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.event.Location;
import ru.practicum.ewm.user.Create;
import ru.practicum.ewm.user.Update;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "eventId")
public class NewEventDto {

    @Positive(groups = {Update.class})
    private Long eventId;

    @NotNull(groups = {Update.class, Create.class})
    @Size(min = 3, max = 120)
    private String title;

    @NotNull(groups = {Update.class, Create.class})
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull(groups = {Update.class, Create.class})
    @Size(min = 20, max = 2000)
    private String annotation;

    @Positive(groups = {Update.class, Create.class})
    private Long category;

    @Future(groups = {Update.class, Create.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(groups = {Create.class})
    private Location location;

    @NotNull(groups = {Update.class, Create.class})
    private Boolean paid;

    @PositiveOrZero(groups = {Update.class, Create.class})
    private Integer participantLimit;

    @NotNull(groups = {Create.class})
    private Boolean requestModeration;

}

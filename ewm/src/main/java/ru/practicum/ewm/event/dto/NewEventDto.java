package ru.practicum.ewm.event.dto;

import ru.practicum.ewm.category.EventCategory;
import ru.practicum.ewm.event.Location;
import ru.practicum.ewm.user.Update;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class NewEventDto {

    @NotBlank(groups = {Update.class})
    private Long id;

    @NotBlank(groups = {Update.class})
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank(groups = {Update.class})
    @Size(min = 20, max = 7000)
    private String description;

    @NotBlank(groups = {Update.class})
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotBlank(groups = {Update.class})
    @Positive
    private EventCategory category;

    @NotBlank(groups = {Update.class})
    @Future
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @NotBlank(groups = {Update.class})
    @NotNull
    private Boolean paid;

    @NotBlank(groups = {Update.class})
    @PositiveOrZero
    private Integer participantLimit;

    @NotNull
    private boolean requestModeration;

}

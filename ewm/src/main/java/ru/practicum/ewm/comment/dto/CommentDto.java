package ru.practicum.ewm.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.comment.CommentState;
import ru.practicum.ewm.user.Create;
import ru.practicum.ewm.user.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class CommentDto {

    @Positive(groups = {Update.class})
    private long id;

    @Positive(groups = {Update.class})
    private Long authorId;

    @Positive(groups = {Update.class})
    private Long eventId;

    @NotNull(groups = {Update.class, Create.class})
    @Size(min = 5, max = 3000)
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private CommentState state;
}

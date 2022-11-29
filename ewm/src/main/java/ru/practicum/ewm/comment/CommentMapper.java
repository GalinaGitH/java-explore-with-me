package ru.practicum.ewm.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class CommentMapper {

    public Comment toModel(CommentDto commentDto, User author, Event event) {
        return new Comment(
                null,
                author,
                event,
                commentDto.getContent(),
                LocalDateTime.now(),
                CommentState.PENDING
        );
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getContent(),
                comment.getCreatedOn(),
                comment.getState()
        );
    }
}

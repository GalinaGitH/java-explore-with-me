package ru.practicum.ewm.comment;

import ru.practicum.ewm.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long userId, long eventId, CommentDto commentDto);

    CommentDto updateComment(long userId, CommentDto commentDto);

    CommentDto getComment(long userId, long commentId);

    void deleteCommentById(long userId, long commentId);

    List<CommentDto> getAllByEventId(Long eventId, int from, int size);

    CommentDto publishComment(long commentId);

    CommentDto rejectComment(long commentId);

    void deleteCommentByAdmin(long commentId);

    List<CommentDto> getAllComments(Long userId, Long eventId, String state, int from, int size);
}

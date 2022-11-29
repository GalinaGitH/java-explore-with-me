package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.dto.CommentDto;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/comments")
@Slf4j
public class AdminCommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> findAllComments(@RequestParam(name = "user", required = false) Long userId,
                                            @RequestParam(name = "event", required = false) Long eventId,
                                            @RequestParam(name = "state", required = false) String state,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Get List of comments with the possibility of filtering. " +
                "Method: GET/findAllComments in AdminCommentController");

        return commentService.getAllComments(userId, eventId, state, from, size);
    }

    @PatchMapping("/{commentId}/publish")
    public CommentDto publishComment(@PathVariable("commentId") long commentId) {
        log.debug("Comment published, commentId = {}. " +
                "Method: PATCH/publishComment in AdminCommentController", commentId);

        return commentService.publishComment(commentId);
    }

    @PatchMapping("/{commentId}/reject")
    public CommentDto rejectComment(@PathVariable("commentId") long commentId) {
        log.debug("Comment rejected, commentId = {}. " +
                "Method: PATCH/publishComment in AdminCommentController", commentId);

        return commentService.rejectComment(commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteById(@PathVariable("commentId") long commentId) {
        commentService.deleteCommentByAdmin(commentId);
        log.debug("Comment with Id = {}  deleted by admin." +
                "Method: DELETE/deleteById in CommentController", commentId);
    }

}

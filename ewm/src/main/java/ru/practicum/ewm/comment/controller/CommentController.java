package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.user.Create;
import ru.practicum.ewm.user.Update;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users/{id}")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    @Validated({Create.class})
    public CommentDto createComment(@PathVariable("id") long userId,
                                    @RequestParam("eventId") long eventId,
                                    @RequestBody @Valid CommentDto commentDto) {
        log.info("Add user's(userId={}) comment to an event(eventId={}). " +
                "Method: POST/createComment in CommentController ", userId, eventId);

        return commentService.createComment(userId, eventId, commentDto);
    }

    @PatchMapping("/comments")
    @Validated({Update.class})
    public CommentDto redactComment(@PathVariable("id") long userId,
                                    @RequestBody @Valid CommentDto commentDto) {
        log.debug("Comment updated, CommentDto = {}. Method: PATCH/redactComment in CommentController", commentDto);

        return commentService.updateComment(userId, commentDto);
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable("id") long userId,
                                     @PathVariable("commentId") long commentId) {
        log.debug("Get comment by commentId = {} ." +
                "Method: GET/getCommentById in CommentController", commentId);

        return commentService.getComment(userId, commentId);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteById(@PathVariable("id") long userId,
                           @PathVariable("commentId") long commentId) {
        commentService.deleteCommentById(userId, commentId);
        log.debug("Comment with Id = {}  deleted." +
                "Method: DELETE/deleteById in CommentController", commentId);
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllByEventId(@RequestParam Long eventId,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug("List of event comments with eventId = {}. " +
                "Method: GET/getAllByEventId in CommentController", eventId);
        return commentService.getAllByEventId(eventId, from, size);
    }


}


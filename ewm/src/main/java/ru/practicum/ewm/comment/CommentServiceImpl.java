package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.exception.NotFoundException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import java.util.stream.Collectors;

import static ru.practicum.ewm.comment.CommentState.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentCustomRepository commentCustomRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto createComment(long userId, long eventId, CommentDto commentDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));

        Comment comment = commentMapper.toModel(commentDto, user, event);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(long userId, CommentDto commentDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        if (!commentDto.getAuthorId().equals(userId)) {
            throw new ValidationException("only author can redact comment");
        }
        Comment comment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentDto.getId()));
        comment.setState(PENDING);//снова в ожидании до проверки комментария админом
        comment.setContent(commentDto.getContent());
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    public CommentDto getComment(long userId, long commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));

        return commentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(long userId, long commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllByEventId(Long eventId, int from, int size) {
        int page = from / size;
        List<Comment> comments =
                commentRepository.findAllCommentsByEventId(eventId,
                        PageRequest.of(page, size, Sort.Direction.DESC, "createdOn")).getContent();

        return comments.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto publishComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));
        comment.setState(PUBLISHED);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto rejectComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));
        comment.setState(REJECTED);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllComments(Long userId, Long eventId, String state, int from, int size) {
        int page = from / size;
        List<Comment> allComments = findByCriteria(userId, eventId, state, PageRequest.of(page, size));

        return allComments.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    private List<Comment> findByCriteria(Long userId, Long eventId, String state, Pageable pageable) {
        Page<Comment> page = commentCustomRepository.findAll(new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (userId != null) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("author"), user)));
                }
                if (eventId != null) {
                    Event event = eventRepository.findById(eventId)
                            .orElseThrow(() -> new NotFoundException("Not found event with id = " + eventId));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("event"), event)));
                }
                if (state != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("state"),
                            CommentState.valueOf(state))));
                }
                query.orderBy(criteriaBuilder.desc(root.get("createdOn")));


                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        page.getTotalElements();
        page.getTotalPages();

        return page.getContent();
    }
}


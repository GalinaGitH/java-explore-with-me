package ru.practicum.ewm.comment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentCustomRepository extends CrudRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

}

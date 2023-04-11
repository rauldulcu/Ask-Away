package com.example.demo.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> getAllCommentsByPost_Id(Long id, Pageable pageable);

    void deleteAllByPost_Id(Long id);

    void deleteAllByAuthor_Id(Long id);
}

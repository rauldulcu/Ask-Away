package com.example.demo.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findPostById(Long id);

    void deleteAllByAuthor_Id(Long id);
}

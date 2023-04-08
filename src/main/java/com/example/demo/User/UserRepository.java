package com.example.demo.User;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);

    List<User> findTop3ByOrderByTokensDesc();
}

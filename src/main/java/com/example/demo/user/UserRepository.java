package com.example.demo.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    List<User> findTop3ByOrderByTokensDesc();

    @Query(value = "SELECT DISTINCT tokens FROM User ORDER BY tokens DESC LIMIT 3;", nativeQuery = true)
    List<Integer> findTop3DistinctTokenValues();

    List<User> findUsersByTokens(Integer tokenNumber);

    Optional<Object> findUserByUserName(String username);

    List<User> findAll(Pageable pageable);
}

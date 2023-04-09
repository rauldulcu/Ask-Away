package com.example.demo.season;

import com.example.demo.season.Season;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SeasonRepository extends CrudRepository<Season, Long> {
}

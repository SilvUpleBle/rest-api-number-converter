package com.service.algorithm.database.repository;

import com.service.algorithm.database.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepo extends CrudRepository<Authority, Long> {
    Authority findByUsername(String username);
}

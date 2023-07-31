package com.service.algorithm.database.repository;

import com.service.algorithm.database.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

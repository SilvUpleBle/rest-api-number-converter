package com.service.algorithm.db.service;

import com.service.algorithm.db.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
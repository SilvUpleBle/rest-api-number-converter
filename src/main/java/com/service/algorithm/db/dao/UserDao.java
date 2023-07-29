package com.service.algorithm.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.algorithm.db.models.User;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
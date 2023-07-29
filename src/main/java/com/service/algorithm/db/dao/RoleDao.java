package com.service.algorithm.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.algorithm.db.models.Role;

public interface RoleDao extends JpaRepository<Role, Long> {

}
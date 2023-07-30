package com.service.algorithm.database.dao;

import com.service.algorithm.database.model.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends CrudRepository<Log, Long> {
}

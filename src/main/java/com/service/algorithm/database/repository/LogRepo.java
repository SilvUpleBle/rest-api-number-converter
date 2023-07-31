package com.service.algorithm.database.repository;

import com.service.algorithm.database.model.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends CrudRepository<Log, Long> {
}

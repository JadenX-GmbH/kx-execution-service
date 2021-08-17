package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Long> {
}

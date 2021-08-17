package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProgramRepository extends JpaRepository<Program, Long> {
}

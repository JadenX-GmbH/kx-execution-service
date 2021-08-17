package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DatasetRepository extends JpaRepository<Dataset, Long> {
}

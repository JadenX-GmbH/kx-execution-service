package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface ExecutionJobRepository extends JpaRepository<ExecutionJob, Long> {
    @Query("select ej from ExecutionJob ej, Gig g where g.dataOwner = ?1 or g.specialist = ?1")
    List<ExecutionJob> findAllExecutionJobsByGig_DataOwner(final UUID uid);

}

package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface ExplorationJobRepository extends JpaRepository<ExplorationJob, Long> {
    @Query("select ej from ExplorationJob ej, Gig g where g.dataOwner = ?1 or g.specialist = ?1")
    List<ExplorationJob> findAllExplorationJobsByGig_DataOwnerOrGig_Specialist(final UUID uid);
}

package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Gig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface GigRepository extends JpaRepository<Gig, Long> {
    List<Gig> findByDataOwnerOrSpecialist(UUID ownerId, UUID specialistId);
}

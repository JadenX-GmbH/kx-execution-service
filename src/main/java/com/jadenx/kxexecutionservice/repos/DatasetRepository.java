package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Dataset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface DatasetRepository extends JpaRepository<Dataset, Long> {


    Page<Dataset> findDistinctByGigDatasetGigs_DataOwner_OrGigDatasetGigs_Specialist(UUID doUid,
                                                                                     UUID dsUid, Pageable pageable);

    Page<Dataset> findAllByGigDatasetGigs_id_OrGigDatasetGigs_Specialist_AndGigDatasetGigs_DataOwner(Long id,
                                                                                                        UUID dsUid,
                                                                                                        UUID doUid,
                                                                                                        Pageable
                                                                                                            pageable);

    Page<Dataset> findAllByDataOwner(UUID uid, Pageable pageable);

}

package com.jadenx.kxexecutionservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
public class ExplorationResult {

    @Id
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String storateType;

    @OneToOne(fetch = FetchType.LAZY,
        optional = false)
    @JoinColumn(name = "exploration_job_id", nullable = false)
    @MapsId
    private ExplorationJob explorationJob;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

}

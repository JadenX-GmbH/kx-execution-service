package com.jadenx.kxexecutionservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
public class Program {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, length = 100)
    private String storageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_job_id", nullable = false)
    private ExecutionJob executionJob;

    @Column
    private String  blockchainAddress;

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

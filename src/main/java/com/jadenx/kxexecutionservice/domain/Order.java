package com.jadenx.kxexecutionservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String blockchainIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_job_id", nullable = false)
    private ExecutionJob executionJob;

    @Column(columnDefinition = "longtext")
    private String orderDetails;

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

package com.jadenx.kxexecutionservice.domain;

import com.jadenx.kxexecutionservice.model.ExecutionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
public class ExecutionJob {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double priceToken;

    // CHECKSTYLE IGNORE check FOR NEXT 5 LINES
    @Column(
        nullable = false,
        name = "\"description\"",
        columnDefinition = "longtext"
    )
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExecutionType executionType;

    @Column
    private String workerpool;

    @Column
    private String worker;

    @Column
    private String dealId;

    @Column(columnDefinition = "varchar(100) default 'https://explorer.iex.ec/bellecour/deal/'")
    private String dealBlockchainIdentifier;

    @Column
    private String taskId;

    @Column(columnDefinition = "varchar(100) default 'https://explorer.iex.ec/bellecour/task/'")
    private String taskBlockchainIdentifier;

    @Column
    private int category;

    @Column
    private int trust;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gig_id", nullable = false)
    private Gig gig;

    @OneToMany(mappedBy = "executionJob", cascade = CascadeType.ALL)
    private Set<Program> executionJobPrograms;

    // CHECKSTYLE IGNORE check FOR NEXT 5 LINES
    @OneToOne(
        mappedBy = "executionJob",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private ExecutionResult executionResult;

    @OneToMany(mappedBy = "executionJob", cascade = CascadeType.ALL)
    private Set<Order> orders;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    @OneToMany(mappedBy = "executionJob", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExecutionInputParameter> inputParameters;

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

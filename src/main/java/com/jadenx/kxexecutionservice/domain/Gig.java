package com.jadenx.kxexecutionservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Gig {

    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID dataOwner;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID specialist;

    // CHECKSTYLE IGNORE check FOR NEXT 7 LINES
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "gig_dataset",
        joinColumns = @JoinColumn(name = "gig_id"),
        inverseJoinColumns = @JoinColumn(name = "dataset_id")
    )
    private Set<Dataset> gigDatasetDatasets;

    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL)
    private Set<ExecutionJob> gigExecutionJobs;

    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL)
    private Set<ExplorationJob> gigExplorationJobs;

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

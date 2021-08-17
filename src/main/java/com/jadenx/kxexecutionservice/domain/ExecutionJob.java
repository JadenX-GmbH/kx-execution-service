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

    @Column(nullable = false)
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

    @Column(nullable = false)
    private String workerpool;

    @Column(nullable = false)
    private String worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gig_id", nullable = false)
    private Gig gig;

    @OneToMany(mappedBy = "executionJob", cascade = CascadeType.ALL)
    private Set<Program> executionJobPrograms;

    // CHECKSTYLE IGNORE check FOR NEXT 5 LINES
    @OneToOne(
        mappedBy = "executionJob",
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private ExecutionResult executionResult;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

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

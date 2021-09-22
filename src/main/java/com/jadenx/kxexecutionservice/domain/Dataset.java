package com.jadenx.kxexecutionservice.domain;

import com.jadenx.kxexecutionservice.model.DataSetType;
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
public class Dataset {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID dataOwner;

    @Lob
    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DataSetType type;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String storageType;

    @ManyToMany(mappedBy = "gigDatasetDatasets")
    private Set<Gig> gigDatasetGigs;

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

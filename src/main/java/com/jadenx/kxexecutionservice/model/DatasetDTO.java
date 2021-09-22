package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
public class DatasetDTO {

    private Long id;

    @Size(max = 100)
    private String title;

    private String description;

    @NotNull
    @Size(max = 255)
    private String hash;

    @NotNull
    private DataSetType type;

    @NotNull
    @Size(max = 255)
    private String location;

    @NotNull
    @Size(max = 255)
    private String storageType;

    @NotNull
    private UUID dataOwner;

    private String  blockchainAddress;

}

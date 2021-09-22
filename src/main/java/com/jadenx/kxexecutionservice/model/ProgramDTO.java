package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ProgramDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String hash;

    @NotNull
    @Size(max = 255)
    private String location;

    @NotNull
    @Size(max = 100)
    private String storageType;

    @NotNull
    private Long executionJob;

    private String  blockchainAddress;

}

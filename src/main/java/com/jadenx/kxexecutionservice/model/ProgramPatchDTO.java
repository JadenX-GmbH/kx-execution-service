package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ProgramPatchDTO {

    private Long id;

    @Size(max = 255)
    private String hash;

    @Size(max = 255)
    private String location;

    @Size(max = 100)
    private String storageType;

    private Long executionJob;

    private String  blockchainAddress;

}

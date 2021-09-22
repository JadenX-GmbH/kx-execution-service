package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class ExecutionResultPatchDTO {

    private Long id;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String storageType;

    @Size(max = 255)
    private String transactionId;

    @Size(max = 255)
    private String blockchainIdentifier;

    private Long executionJob;

}

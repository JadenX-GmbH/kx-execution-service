package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ExecutionResultDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String location;

    @NotNull
    @Size(max = 255)
    private String storageType;

    @NotNull
    @Size(max = 255)
    private String transactionId;

    @NotNull
    @Size(max = 255)
    private String blockchianIdentifier;

    @NotNull
    private Long executionJob;

}

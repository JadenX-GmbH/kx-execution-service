package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ExecutionJobDTO {

    private Long id;

    @NotNull
    private Double priceToken;

    @NotNull
    private String description;

    @NotNull
    private ExecutionType executionType;

    @NotNull
    @Size(max = 255)
    private String workerpool;

    @NotNull
    @Size(max = 255)
    private String worker;

    @NotNull
    private Long gig;

    private Long order;

    private ExecutionResultDTO executionResultDTO;

}

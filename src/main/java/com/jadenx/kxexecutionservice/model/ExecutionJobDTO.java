package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ExecutionJobDTO {

    private Long id;

    private Double priceToken;

    @NotNull
    private String description;

    @NotNull
    private ExecutionType executionType;

    @Size(max = 255)
    private String workerpool;

    @Size(max = 255)
    private String worker;

    @Size(max = 66)
    private String dealId;

    @Size(max = 100)
    private String dealBlockchainIdentifier;

    @Size(max = 66)
    private String taskId;

    @Size(max = 100)
    private String taskBlockchainIdentifier;

    @Min(0)
    @Max(100)
    private int category;

    @Min(0)
    @Max(100)
    private int trust;


    @NotNull
    private Long gig;

    private ExecutionResultDTO executionResultDTO;

    private List<ProgramDTO> programDTOList = new ArrayList<>();

    private Long dataset;

    private List<String> inputParameters;

}

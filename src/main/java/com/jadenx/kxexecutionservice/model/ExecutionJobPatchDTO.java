package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ExecutionJobPatchDTO {

    private Long id;

    private Double priceToken;

    private String description;

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

    private Long gig;

    private List<ProgramPatchDTO> programPatchDTOList = new ArrayList<>();

    private Long dataset;

    private List<String> inputParameters;

}

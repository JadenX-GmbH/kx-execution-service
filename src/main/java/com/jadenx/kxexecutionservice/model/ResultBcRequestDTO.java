package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
public class ResultBcRequestDTO {

    private String multiaddr;

    private String checksum;

    private Long programId;

    private List<String> inputParameters;

    @Range(min = 0, max = 100)
    private int category;

    private Long executionJobId;

}

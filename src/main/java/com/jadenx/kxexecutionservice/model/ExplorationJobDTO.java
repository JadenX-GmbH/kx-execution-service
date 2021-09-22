package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ExplorationJobDTO {

    private Long id;

    private String description;

    @Size(max = 255)
    private String codeHash;

    @NotNull
    private Long gig;

    private ExplorationResultDTO explorationResultDTO;

    private Long dataset;

}

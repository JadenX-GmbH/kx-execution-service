package com.jadenx.kxexecutionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GigDTO {

    @NotNull
    private Long id;

    @NotNull
    private UUID dataOwner;

    private UUID specialist;

    private List<Long> gigDatasets;

}

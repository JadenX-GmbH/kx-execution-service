package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class GigDTO {

    private Long id;

    @NotNull
    private Long gigId;

    @NotNull
    private UUID dataOwner;

    private UUID specialist;

    private List<Long> gigDatasets;

}

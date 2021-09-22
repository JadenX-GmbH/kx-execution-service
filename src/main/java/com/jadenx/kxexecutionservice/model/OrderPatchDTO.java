package com.jadenx.kxexecutionservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class OrderPatchDTO {

    private Long id;

    private String name;

    @Size(max = 255)
    private String transactionId;

    @Size(max = 255)
    private String blockchainIdentifier;

    private Long executionJob;

    private String orderDetails;

}

package com.jadenx.kxexecutionservice.proxy;

import com.jadenx.kxexecutionservice.model.DealResponseDTO;
import com.jadenx.kxexecutionservice.model.ResultBcRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "kx-blockchain-middleware", url = "${feign.url}")
public interface FeignRestClientProxyJs {

    @PostMapping("iexec/executionJob")
    ResponseEntity<DealResponseDTO> createExecutionJob(@RequestBody @Valid final ResultBcRequestDTO resultBcRequestDTO);
}

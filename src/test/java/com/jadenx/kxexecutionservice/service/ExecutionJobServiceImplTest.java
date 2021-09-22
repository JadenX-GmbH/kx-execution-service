package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.*;
import com.jadenx.kxexecutionservice.mapper.ExecutionJobMapper;
import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExecutionType;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.repos.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExecutionJobServiceImplTest {

    @Mock
    private ExecutionJobRepository executionJobRepository;
    @Mock
    private ExecutionJobMapper executionJobMapper;

    @InjectMocks
    private ExecutionJobServiceImpl service;

    @Test
    public void create_success() {

        List<String> inputParameters = new ArrayList<>();
        inputParameters.add("test1");
        inputParameters.add("test2");

        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setHash("sha256:8a682cce5175358b3cd53f3a8a084365bc9ed0c3474cc3d047008db65541c0f0");
        programDTO.setLocation("iexechub/iexec-face-swap:1.0.0");
        programDTO.setStorageType("DockerHub");
        List<ProgramDTO> programDTOList = new ArrayList<>();
        programDTOList.add(programDTO);

        ExecutionJobDTO executionJobDTO = new ExecutionJobDTO();
        executionJobDTO.setDescription("test");
        executionJobDTO.setExecutionType(ExecutionType.TEE);
        executionJobDTO.setGig(100L);
        executionJobDTO.setDataset(1001L);
        executionJobDTO.setInputParameters(inputParameters);
        executionJobDTO.setProgramDTOList(programDTOList);

        Gig gig = new Gig();
        gig.setId(100L);
        gig.setDataOwner(UUID.fromString("dc267fab-612c-42b1-bfe6-261420efc457"));

        Dataset dataset = new Dataset();
        dataset.setId(1001L);

        ExecutionJob executionJob = new ExecutionJob();
        executionJob.setId(10000L);
        executionJob.setGig(gig);
        executionJob.setDataset(dataset);
        executionJob.setExecutionType(ExecutionType.TEE);
        executionJob.setDescription("test");

        when(executionJobMapper.mapToEntity(Mockito.any(), Mockito.any())).thenReturn(executionJob);
        when(executionJobRepository.save(Mockito.any())).thenReturn(executionJob);

        Long response = service.create(executionJobDTO);

        assertNotNull(response);
        assertEquals(10000L, response);

    }

    @Test
    public void update_success() {
        List<String> inputParameters = new ArrayList<>();
        inputParameters.add("test1");
        inputParameters.add("test2");

        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setId(100L);
        programDTO.setHash("sha256:8a682cce5175358b3cd53f3a8a084365bc9ed0c3474cc3d047008db65541c0f0");
        programDTO.setLocation("iexechub/iexec-face-swap:1.0.0");
        programDTO.setStorageType("DockerHub");
        List<ProgramDTO> programDTOList = new ArrayList<>();
        programDTOList.add(programDTO);

        ExecutionJobDTO executionJobDTO = new ExecutionJobDTO();
        executionJobDTO.setDescription("test");
        executionJobDTO.setExecutionType(ExecutionType.TEE);
        executionJobDTO.setGig(100L);
        executionJobDTO.setDataset(1001L);
        executionJobDTO.setInputParameters(inputParameters);
        executionJobDTO.setProgramDTOList(programDTOList);

        Gig gig = new Gig();
        gig.setId(100L);
        gig.setDataOwner(UUID.fromString("dc267fab-612c-42b1-bfe6-261420efc457"));

        Dataset dataset = new Dataset();
        dataset.setId(1001L);

        ExecutionJob executionJob = new ExecutionJob();
        executionJob.setId(10000L);
        executionJob.setExecutionType(ExecutionType.TEE);
        executionJob.setDescription("test");

        Program program = new Program();
        program.setId(100L);
        program.setHash("sha256:8a682cce5175358b3cd53f3a8a084365bc9ed0c3474cc3d047008db65541c0f0");
        program.setLocation("iexechub/iexec-face-swap:1.0.0");
        program.setStorageType("DockerHub");
        program.setExecutionJob(executionJob);
        List<Program> programs = new ArrayList<>();
        programs.add(program);

        executionJob.setExecutionJobPrograms(new HashSet<>(programs));

        when(executionJobRepository.findById(Mockito.any())).thenReturn(Optional.of(executionJob));
        when(executionJobMapper.mapToEntity(Mockito.any(), Mockito.any())).thenReturn(executionJob);
        when(executionJobRepository.save(Mockito.any())).thenReturn(executionJob);

        service.update(10000L, executionJobDTO);
    }
}

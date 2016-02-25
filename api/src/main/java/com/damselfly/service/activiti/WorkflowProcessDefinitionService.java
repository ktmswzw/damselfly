package com.damselfly.service.activiti;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * Created by vincent on 2015/8/2.
 */
public interface WorkflowProcessDefinitionService {
    ProcessDefinition findProcessDefinitionByPid(String processInstanceId);
    ProcessDefinition findProcessDefinition(String processDefinitionId);
    void deployFromClasspath(String exportDir, String... processKey) throws Exception;
    void deploySingleProcess(ResourceLoader resourceLoader, String processKey, String exportDir) throws IOException;
    void redeploy(String exportDir, String... processKey) throws Exception;
    void deployAllFromClasspath(String exportDir) throws Exception;
}

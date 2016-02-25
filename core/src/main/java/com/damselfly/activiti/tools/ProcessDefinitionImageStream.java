package com.damselfly.activiti.tools;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.explorer.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;

/**
 * Created by v on 2014/7/31.
 */
public class ProcessDefinitionImageStream  implements Serializable {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionImageStream.class);

    public InputStream buildStream(ProcessEngine processEngine,ProcessDefinition processDefinition, RepositoryService repositoryService) {
        InputStream definitionImageStream = null;
        if(processDefinition.getDiagramResourceName() != null) {
            try {
                InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
                XMLInputFactory xif = XMLInputFactory.newInstance();
                InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(in);
                BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
                processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                        .generateDiagram(bpmnModel, "png",
                                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                                processEngine.getProcessEngineConfiguration().getClassLoader(), 1.0);

                definitionImageStream = repositoryService.getResourceAsStream(
                        processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
            return  definitionImageStream;
    }

    public InputStream buildStream(ProcessEngine processEngine,ProcessInstance processInstance, RepositoryService repositoryService, RuntimeService runtimeService) {


        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance
                .getProcessDefinitionId());
        InputStream definitionImageStream = null;
        if (processDefinition != null && processDefinition.isGraphicalNotationDefined()) {
            try {

                BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
                ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
                definitionImageStream = processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
                        runtimeService.getActiveActivityIds(processInstance.getId()), Collections.<String>emptyList(),
                        processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),
                        processEngineConfiguration.getClassLoader(), 1.0);

            } catch(Throwable t) {
                // Image can't be generated, ignore this
                LOGGER.warn("Process image cannot be generated due to exception: {} - {}", t.getClass().getName(), t.getMessage());
            }
        }
        return definitionImageStream;
    }

    protected String extractImageExtension(String diagramResourceName) {
        String[] parts = diagramResourceName.split(".");
        if(parts.length > 1) {
            return parts[parts.length - 1];
        }
        return Constants.DEFAULT_DIAGRAM_IMAGE_EXTENSION;
    }
}

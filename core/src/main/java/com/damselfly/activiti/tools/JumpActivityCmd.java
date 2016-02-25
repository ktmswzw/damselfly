package com.damselfly.activiti.tools;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * Created by v on 2014/7/11.
 */
public class JumpActivityCmd implements Command<Object> {
    private String activityId;
    private String processInstanceId;
    private String jumpOrigin;

    public JumpActivityCmd(String processInstanceId, String activityId) {
        this(processInstanceId, activityId, "jump");
    }

    public JumpActivityCmd(String processInstanceId, String activityId, String jumpOrigin) {
        this.activityId = activityId;
        this.processInstanceId = processInstanceId;
        this.jumpOrigin = jumpOrigin;
    }

    public Object execute(CommandContext commandContext) {

        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findExecutionById(processInstanceId);

        executionEntity.destroyScope(jumpOrigin);

        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
        ActivityImpl activity = processDefinition.findActivity(activityId);

        executionEntity.executeActivity(activity);

        return executionEntity;
    }
}

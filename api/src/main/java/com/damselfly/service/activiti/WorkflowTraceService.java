package com.damselfly.service.activiti;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vincent on 2015/8/2.
 */
public interface WorkflowTraceService {
    List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception;
    Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
                                                  boolean currentActiviti) throws Exception;
    void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions);
    void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask);
    Task getCurrentTaskInfo(ProcessInstance processInstance);
    void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo);
    void setPosition(ActivityImpl activity, Map<String, Object> activityInfo);
}

package com.damselfly.controller.activiti;

import com.damselfly.activiti.tools.TaskSelfBean;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v on 2014/8/19.
 */
@Component
public class CoreTool {


    /**
     * 根据业务主键查找流程业务部署实例ID
     * @author vincent
     * @param key
     * @param repositoryService
     * @version 1.0
     * @see
     * @return 流程业务部署实例ID
     * @exception null
     */
    public String getKey(String key,RepositoryService repositoryService) {
        String processDefinitionId = "";
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();

        if(list.size()>0)
            processDefinitionId = list.get(0).getId();//取最新的流程

        return processDefinitionId;
    }

    /**
     * 获取最新流程实例
     * @param processDefinitionId
     * @param repositoryService
     * @return
     */
    public ProcessDefinition getDefinition(String processDefinitionId,RepositoryService repositoryService) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).active();
        List<ProcessDefinition> list = query.list();
        if(list.size()>0)
            return list.get(0);//取最新的流程
        return null;
    }

    /**
     * 查找所有任务
     * @param taskService
     * @param repositoryService
     * @return
     */
    public List<TaskSelfBean> getAllTaskLists(TaskService taskService,RepositoryService repositoryService,String name)
    {
        List<TaskSelfBean> resultList = new ArrayList();

        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(name).active().orderByExecutionId().desc().list();

        for (Task t : tasks) {
            TaskSelfBean s = new TaskSelfBean();
            try {
                ProcessDefinition processDefinition = getDefinition(t.getProcessDefinitionId(),repositoryService) ;
                if(processDefinition!=null) {
                    s.setBusinessName(processDefinition.getName());
                    s.setTaskUrl(processDefinition.getDescription());
                }
                BeanUtils.copyProperties(s, t);
                resultList.add(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}

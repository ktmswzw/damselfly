package com.damselfly.controller.activiti;

import com.damselfly.activiti.tools.*;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.Result;
import com.damselfly.viewModel.GridModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 外置表单Controller
 * 了解不同表单请访问：http://www.kafeitu.me/activiti/2012/08/05/diff-activiti
 * -workflow-forms.html
 *
 * @author HenryYan
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/leave/form/formkey")
public class FormKeyController  extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;


    private static final String PROCESS_LIST = "form/leave/formkey/formkey-process-list";

    @RequestMapping(value="/formkeyprocesslist", method= RequestMethod.GET)
    public String formkeyprocesslist(HttpServletRequest request) {
        return PROCESS_LIST;
    }

    /**
     * 动态form流程列表
     *
     * @return
     */
    @RequestMapping("/processFromKeyLists")
    @ResponseBody
    public GridModel processFromKeyLists() {
        GridModel m = new GridModel();
        List<ProcessDefinitionBean> resultList = new ArrayList();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        //ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave-formkey").active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();
        for (ProcessDefinition processDefinition : list) {
            ProcessDefinitionEntity entity = (ProcessDefinitionEntity)processDefinition;
            ProcessDefinitionBean bean = new ProcessDefinitionBean();
            try {
                BeanUtils.copyProperties(bean, entity);
                bean.setId(processDefinition.getId());
                bean.setName(processDefinition.getName());
                bean.setSuspended(processDefinition.isSuspended());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            /*
            bean.setCategory(processDefinition.getCategory());
            bean.setDeploymentId(processDefinition.getDeploymentId());
            bean.setDiagramResourceName(processDefinition.getDiagramResourceName());
            bean.setKey(processDefinition.getKey());
            bean.setTenantId(processDefinition.getTenantId());
            bean.setResourceName(processDefinition.getResourceName());
            bean.setVersion(processDefinition.getVersion());*/

            resultList.add(bean);
        }
        m.setTotal(resultList.size());
        m.setRows(resultList);

        return m;
    }

    /**
     * 初始化启动流程，读取启动流程的表单内容来渲染start form
     */
    @RequestMapping(value = "/get-form/start/{processDefinitionId}")
    @ResponseBody
    public Object findStartForm(@PathVariable("processDefinitionId") String processDefinitionId) throws Exception {

        // 根据流程定义ID读取外置表单
        Object startForm = formService.getRenderedStartForm(processDefinitionId);

        return startForm;
    }

    /**
     * 读取Task的表单
     */
    @RequestMapping(value = "/get-form/task/{taskId}")
    @ResponseBody
    public Object findTaskForm(@PathVariable("taskId") String taskId) throws Exception {
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        return renderedTaskForm;
    }

    /**
     * 办理任务，提交task的并保存form
     */
    @RequestMapping(value = "/task/complete/{taskId}/{name}")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result completeTask(@PathVariable("taskId") String taskId,@PathVariable("name") String name, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Map<String, String> formProperties = new HashMap<String, String>();

        Result result = new Result();
        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();

      /*
       * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
       */
            if (StringUtils.defaultString(key).startsWith("fp_")) {
                String[] paramSplit = key.split("_");
                formProperties.put(paramSplit[1], entry.getValue()[0]);
            }
        }

        logger.debug("start form parameters: {}", formProperties);


        try {
            identityService.setAuthenticatedUserId(name);

            formService.submitTaskFormData(taskId, formProperties);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        result.setMsg("任务完成：taskId=" + taskId);
        result.setSuccessful(true);
        return result;


    }


    private static final String TASK_LIST = "form/leave/formkey/formkey-task-list";

    @RequestMapping(value="/formkeytasklist", method= RequestMethod.GET)
    public String formkeytasklist(HttpServletRequest request) {
        return TASK_LIST;
    }

    /**
     * task列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/task/list/{name}")
    @ResponseBody
    public GridModel taskList(@PathVariable("name") String name,Model model, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        GridModel m = new GridModel();
        List<TaskSelfBean> resultList = new ArrayList();

        Page page = page();
        /**
         * 这里为了演示区分开自定义表单的请假流程，值读取leave-formkey
         */
    //and D.KEY_ = #{processDefinitionKey}
        // 已经签收的或者直接分配到当前人的任务
        String asigneeSql = "select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = #{userId}"
                + " and RES.SUSPENSION_STATE_ = #{suspensionState}";
//D1.KEY_ = #{processDefinitionKey}
        // 当前人在候选人或者候选组范围之内
        String needClaimSql = "select distinct RES1.* from ACT_RU_TASK RES1 inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES1.ID_ inner join ACT_RE_PROCDEF D1 on RES1.PROC_DEF_ID_ = D1.ID_ WHERE"
                + " RES1.ASSIGNEE_ is null and I.TYPE_ = 'candidate'"
                + " and ( I.USER_ID_ = #{userId} or I.GROUP_ID_ IN (select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userId} ) )"
                + " and RES1.SUSPENSION_STATE_ = #{suspensionState}";
        String sql = asigneeSql + " union all " + needClaimSql;
        //parameter("processDefinitionKey", "leave-formkey").
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql(sql)
                .parameter("suspensionState", SuspensionState.ACTIVE.getStateCode())
                .parameter("userId", name);

        List<Task> tasks = query.listPage(page.getOffset()<0?0:page.getOffset(),page.getLimit());

        //所有
        //List<Task> tasks  = taskService.createTaskQuery().taskCandidateOrAssigned(user.getUsername()).active().orderByTaskId().desc().list();
        for (Task t  : tasks) {
            TaskSelfBean s = new TaskSelfBean();
            try {
                BeanUtils.copyProperties(s, t);
                resultList.add(s);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        m.setRows(resultList);
        m.setTotal(resultList.size());

        return m;
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "/task/claim/{id}/{name}")
    @ResponseBody
    public Result claim(@PathVariable("id") String taskId,@PathVariable("name") String name, HttpSession session, RedirectAttributes redirectAttributes) {
        Result result = new Result();
        taskService.claim(taskId, name);
        result.setSuccessful(true);
        result.setMsg("任务已签收");
        return result;
    }

    public static final String RUNNING_LIST = "form/leave/running-list";
    @RequestMapping(value="/running-list", method= RequestMethod.GET)
    public String runningList(HttpServletRequest request) {
        return RUNNING_LIST;
    }

    /**
     * 运行中的流程实例
     * @param request
     * @return
     */
    @RequestMapping(value = "/process-instance/running/list")
    @ResponseBody
    public GridModel running(HttpServletRequest request) {
        GridModel m = new GridModel();
        Page page = page();
        //ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave-formkey").active().orderByProcessInstanceId().desc();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().desc();
        List<ProcessInstance> processInstanceList = query.listPage(page.getOffset()<0?0:page.getOffset(),page.getLimit());
        List<ExecutionBean> list = new ArrayList();
        for(ProcessInstance  processInstance : processInstanceList){
            ExecutionEntity entity = (ExecutionEntity) processInstance;
            ExecutionBean bean = new ExecutionBean();
            try {
                BeanUtils.copyProperties(bean, entity);
                bean.setId(processInstance.getId());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            list.add(bean);
        }
        m.setTotal(list.size());
        m.setRows(list);
        return  m;
    }



    public static final String FINISHED_LIST = "form/leave/finished-list";
    @RequestMapping(value="/finished-list", method= RequestMethod.GET)
    public String finishedList(HttpServletRequest request) {
        return FINISHED_LIST;
    }

    /**
     * 已结束的流程实例
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/process-instance/finished/list")
    @ResponseBody
    public GridModel finished(Model model, HttpServletRequest request) {
        GridModel m = new GridModel();
        Page page = page();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceEndTime().desc().finished();
        //HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave-formkey").orderByProcessInstanceEndTime().desc().finished();
        List<HistoricProcessInstance> list = query.listPage(page.getOffset()<0?0:page.getOffset(),page.getLimit());
 
        List<HistoricProcessInstanceBean> resultList = new ArrayList<>();
        for(HistoricProcessInstance  historicProcessInstance : list){
            HistoricProcessInstanceEntity entity = (HistoricProcessInstanceEntity) historicProcessInstance;
            HistoricProcessInstanceBean bean = new HistoricProcessInstanceBean();
            try {
                BeanUtils.copyProperties(bean, entity);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            resultList.add(bean);
        }
        m.setRows(resultList);
        m.setTotal(resultList.size());
        return m;
    }

    /**
     * 历史
     *
     * @param processInstanceId
     * @return
     */

    @RequestMapping(value = "/taskHistoryList/{processInstanceId}")
    @ResponseBody
    public GridModel taskHistoryList(@PathVariable("processInstanceId") String processInstanceId) {
        List<HistoricTaskInstance> historicTasks = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).list();
        GridModel m = new GridModel();
        List<HistoricTaskInstanceBean> list = new ArrayList();
        for(HistoricTaskInstance  historicTaskInstance : historicTasks){
            HistoricTaskInstanceEntity entity = (HistoricTaskInstanceEntity) historicTaskInstance;
            HistoricTaskInstanceBean bean = new HistoricTaskInstanceBean();
            try {
                BeanUtils.copyProperties(bean, entity);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            list.add(bean);
           }
        m.setTotal(list.size());
        m.setRows(list);
        return  m;
    }

}

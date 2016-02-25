package com.damselfly.controller.activiti;

import com.damselfly.activiti.tools.HistoryProcessInstanceDiagramCmd;
import com.damselfly.activiti.tools.JumpActivityCmd;
import com.damselfly.activiti.tools.ProcessDefinitionBean;
import com.damselfly.activiti.tools.WorkflowUtils;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.service.activiti.WorkflowProcessDefinitionService;
import com.damselfly.service.activiti.WorkflowTraceService;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by v on 2014/7/11.
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/activiti/workflow")
public class ActivitiController  extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final String PROCESS_LIST = "management/workflow/processlist";

    @Autowired
    protected WorkflowProcessDefinitionService workflowProcessDefinitionService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected WorkflowTraceService traceService;

    @Autowired
    ManagementService managementService;


    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;

    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();


    @RequestMapping(value="/processlist", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return PROCESS_LIST;
    }


    /**
     * 流程定义列表
     *
     * @return
     */
    @RequestMapping(value = "/getprocesslist")
    @ResponseBody
    public GridModel processList(HttpServletRequest request) {
    /*
     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
     */
        GridModel m = new GridModel();
        Page page = page();
        List<ProcessDefinitionBean> resultList = new ArrayList();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getOffset()<0?0:page.getOffset(),page.getLimit());
        for (ProcessDefinition processDefinition : processDefinitionList) {
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
            resultList.add(bean);
        }

        m.setTotal(resultList.size());

        m.setRows(resultList);
        return m;
    }

    /**
     * 部署全部流程
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/redeploy/all")
    @ResponseBody
    public String redeployAll(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws Exception {
        workflowProcessDefinitionService.deployAllFromClasspath(exportDir);
        return "redirect:/workflow/process-list";
    }

    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read")
    @ResponseBody
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        InputStream resourceAsStream = null;
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
            resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
    } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
            resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);

        }
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    @ResponseBody
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            ProcessDefinitionImageStream p = new ProcessDefinitionImageStream();
            resourceAsStream = p.buildStream(processEngine,processInstance, repositoryService, runtimeService);
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
            resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        }
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }


    /**
     * 自定义读取带跟踪流程路径图片
     * @throws Exception
     */
    @RequestMapping(value = "/traceImage/{processInstanceId}")
    public void traceImage(@PathVariable("processInstanceId") String processInstanceId,HttpServletResponse response) throws Exception {

        //Context.setProcessEngineConfiguration((org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl) processEngineConfiguration);

        Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
        InputStream is = managementService.executeCommand(cmd);

        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/process/delete")
    public String delete(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return "redirect:/activiti/workflow/processlist";
    }

    /**
     * 输出跟踪流程信息
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process/trace")
    public List<Map<String, Object>> traceProcess(@RequestParam("pid") String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = traceService.traceProcess(processInstanceId);
        return activityInfos;
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/process/trace/auto/{executionId}")
    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)
            throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        // 不使用spring请使用下面的两行代码
//    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
//    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

        // 使用spring注入引擎请使用下面的这行代码
       Context.setProcessEngineConfiguration((org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl) processEngineConfiguration);

        //InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream imageStream = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    @RequestMapping(value = "/deploy")
    public String deploy(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir, @RequestParam(value = "file", required = false) MultipartFile file) {

        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;

            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            for (ProcessDefinition processDefinition : list) {
                WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
            }

        } catch (Exception e) {
            logger.error("error on deploy process, because of file input stream", e);
        }

        return "redirect:/activiti/workflow/processlist";
    }

    /**
     * 转为模型
     * @param processDefinitionId
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @RequestMapping(value = "/process/convert-to-model/{processDefinitionId}")
    public String convertToModel(@PathVariable("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelNode2 = converter.convertToJson(bpmnModel);
        modelNode2.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelNode2.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelNode2.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelNode2.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return "redirect:/activiti/workflow/processlist";
    }

    /**
     * 转为模型
     * @param processDefinitionId
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @RequestMapping(value = "/process/convert-to-modelNew/{processDefinitionId}")
    public String convertToModelNew(@PathVariable("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());


        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return "redirect:/activiti/workflow/processlist";
    }

    /**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/task/todo/list")
    @ResponseBody
    public List<Map<String, Object>> todoList(HttpSession session,String name) throws Exception {;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        // 已经签收的任务
        List<Task> todoList = taskService.createTaskQuery().taskAssignee(name).active().list();
        for (Task task : todoList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "todo");
            result.add(singleTask);
        }

        // 等待签收的任务
        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(name).active().list();
        for (Task task : toClaimList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "claim");
            result.add(singleTask);
        }

        return result;
    }

    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "/updateState/{state}/{id}")
    public String updateState(@PathVariable("state") String state, @PathVariable("id") String processDefinitionId,
                              RedirectAttributes redirectAttributes) {
        if (state.equals("active")) {
            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processDefinitionId + "]的流程定义。");
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processDefinitionId + "]的流程定义。");
        }
        return "redirect:/activiti/workflow/processlist";
    }

    /**
     * 导出图片文件到硬盘
     *
     * @return
     */
    @RequestMapping(value = "export/diagrams")
    @ResponseBody
    public List<String> exportDiagrams(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws IOException {
        List<String> files = new ArrayList<String>();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        for (ProcessDefinition processDefinition : list) {
            files.add(WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir));
        }
        return files;
    }

    @RequestMapping(value = "activity/jump")
    @ResponseBody
    public boolean jump(@RequestParam("executionId") String executionId,
                        @RequestParam("activityId") String activityId) {
        Command<Object> cmd = new JumpActivityCmd(executionId, activityId);
        managementService.executeCommand(cmd);
        return true;
    }

    @RequestMapping(value = "bpmn/model/{processDefinitionId}")
    @ResponseBody
    public BpmnModel queryBpmnModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        return bpmnModel;
    }

}

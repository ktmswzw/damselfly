package com.damselfly.controller.activiti;

/**
 *
 * Created by v on 2014/6/3.
 */

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.viewModel.GridModel;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 流程模型控制器
 *
 * @author henryyan
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/workflow/model")
public class ModelController  extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RepositoryService repositoryService;


    @Autowired
    private ProcessEngine processEngine;

    public static final String LIST = "/management/workflow/modelList";
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list() {
        return LIST;
    }


    /**
     * 模型列表
     */
    @RequestMapping(value="/dataList")
    @ResponseBody
    public GridModel dataList(){
        GridModel m = new GridModel();
        Page page = page();
        List<Model> list = repositoryService.createModelQuery().listPage(page.getOffset()<0?0:page.getOffset(),page.getLimit());
        m.setRows(list);
        m.setTotal(list.size());
        return m;
    }



    /**
     * 创建模型
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + "/service/editor?id=" + modelData.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/workflow/model/list";
    }


    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "deploy/{modelId}")
    public String deploy(@PathVariable("modelId") String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));


            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);

            processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                    .generateDiagram(model, "png",
                            processEngine.getProcessEngineConfiguration().getActivityFontName(),
                            processEngine.getProcessEngineConfiguration().getLabelFontName(),
                            processEngine.getProcessEngineConfiguration().getClassLoader(),1.0);

            byte[] bpmnBytes   = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            repositoryService.createDeployment().name(modelData.getName()).addString(processName,  new String(bpmnBytes, "UTF-8")).deploy();

        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return "redirect:/workflow/model/list";
    }



    /**
     * 根据Model部署流程 NEW
     *
     */
    @RequestMapping(value = "deployNew/{modelId}")
    public String deployNew(@PathVariable("modelId") String modelId) {

            Model model = repositoryService.getModel(modelId);
        ObjectNode modelNode = null;
        try {
            modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        deployModelerModel(modelNode,model);

        return "redirect:/workflow/model/list";
    }


    protected void deployModelerModel(final ObjectNode modelNode,Model modelData) {

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                .generateDiagram(model, "png",
                        processEngine.getProcessEngineConfiguration().getActivityFontName(),
                        processEngine.getProcessEngineConfiguration().getLabelFontName(),
                        processEngine.getProcessEngineConfiguration().getClassLoader(),1.0);

        String processName = modelData.getName() + ".bpmn20.xml";
        try {
            repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }

    @RequestMapping(value = "delete/{modelId}")
    public String delete(@PathVariable("modelId") String modelId) {
        repositoryService.deleteModel(modelId);
        return "redirect:/workflow/model/list";
    }

}

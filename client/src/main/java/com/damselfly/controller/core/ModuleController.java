package com.damselfly.controller.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.DataAttributes;
import com.damselfly.common.util.FuelueTree;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.entity.Module;
import com.damselfly.entity.Permission;
import com.damselfly.exception.ExistedException;
import com.damselfly.exception.ServiceException;
import com.damselfly.service.core.ModuleService;
import com.damselfly.service.core.PermissionService;
import com.damselfly.shiro.SecurityUtils;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by v on 2014/7/18.
 */

@Controller
@RequestMapping("/console/security/module")
public class ModuleController extends BaseAction {

    private static final String MODULE_TREE = "console/security/module/list";
    private static final String MODULE_EDIT = "console/security/module/edit";

    @Autowired
    private ModuleService moduleService;
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String tree_list() {
        return MODULE_TREE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {

        ModelAndView mav = new ModelAndView(MODULE_EDIT);
        Module module = new Module();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(module);
            mav.addObject("message", "success");
            mav.addObject("module",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(MODULE_EDIT);
        try {
        Module module = moduleService.get(Long.parseLong(id + ""));
        ObjectMapper mapper = JacksonMapper.getInstance();
        String json =mapper.writeValueAsString(module);
        mav.addObject("message", "success");
        mav.addObject("module",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/getModuleList", method = RequestMethod.GET)
    @ResponseBody
    public GridModel getModuleListAll() {
        Module module = form(Module.class);
        Page info = moduleService.findByPage(page(), module);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }


    @RequestMapping(value = "/getModuleByP/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Module> getModuleByParentId(@PathVariable Integer pid) {
        List<Module> modules = moduleService.getModuleByParentId(Long.valueOf(pid));
        Subject subject = SecurityUtils.getSubject();
        List<Module> modules_me = check(modules, subject);
        return modules_me;
    }

    /**
     * 检查权限是否拥有
     * @param modules
     * @param subject
     * @return
     */
    private List<Module> check(List<Module> modules, Subject subject) {
        List<Module> list1 = Lists.newArrayList();
        for (Module m1 :modules) {
            // 只加入拥有view权限的Module
            if (subject.isPermitted(m1.getSn() + ":"+ Permission.PERMISSION_SHOW))
            {
                list1.add(m1);
            }
        }
        return list1;
    }

    @RequestMapping(value = "/findJsonById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findJsonById(@PathVariable Long id) {
        System.out.println("id = " + id);
        List<Module> modules = moduleService.getModuleByParentId(Long.valueOf(id));
        String json = "";
        JSONObject jsonobject = new JSONObject();
        JSONArray jarray = new JSONArray();
        for (Module o : modules) {
            FuelueTree fuelueTree = new FuelueTree();
            fuelueTree.setText(o.getName());
            fuelueTree.setType(o.getNodes() > 0 ? "folder" : "item");
            DataAttributes dataAttributes =  new DataAttributes();
            dataAttributes.setId(o.getId().toString());
            fuelueTree.setAttr(dataAttributes);
            jarray.add(fuelueTree);
        }
        jsonobject.put("data", jarray);
        return jsonobject;
    }


    @RequestMapping(value = "/deleteInfo/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteModule(@PathVariable Integer id) {
        Result result = new Result();
        if (id != null && id > 1) {
            Permission permission = new Permission();
            permission.setModuleId(Long.valueOf(id));
            if (permissionService.get(permission).size() > 0) {
                result.setMsg("该模块配置了权限，不可以删除");
                result.setSuccessful(false);
                return result;
            }

            Module module1 = new Module();
            module1.setParentId(Long.valueOf(id));
            if (moduleService.find(module1).size() > 0) {
                result.setMsg("该模块有子模块，不可以删除");
                result.setSuccessful(false);
                return result;
            } else {
                try {
                    moduleService.delete(Long.valueOf(id));
                    result.setMsg("操作成功");
                    result.setSuccessful(true);
                } catch (ServiceException s) {
                    result.setMsg(s.getMessage());
                    result.setSuccessful(false);
                    return result;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteModule(@ModelAttribute Module module) {
        Result result = new Result();

        if (module.getId() != null && module.getId() > 1) {

            Module modules = new Module();
            if (module.getName() != null && !(module.getId() == null)) {
                modules.setName(module.getName());
                modules.setSn(module.getSn());
                modules.setUrl(module.getUrl());
                if (moduleService.find(modules).size() > 1) {
                    result.setMsg("已存在多个，不可修改");
                    result.setSuccessful(false);
                    return result;
                } else {
                    try {
                        moduleService.update(module);
                        result.setMsg("操作成功");
                        result.setSuccessful(true);
                    } catch (ExistedException e) {
                        result.setMsg(e.getMessage());
                        result.setSuccessful(false);
                        return result;
                    }
                }
            }
        }
        else {
            Module modules = new Module();
            modules.setName(module.getName());
            modules.setSn(module.getSn());
            modules.setUrl(module.getUrl());
            if (moduleService.find(modules).size() > 0) {
                result.setMsg("已存在，不可添加");
                result.setSuccessful(false);
                return result;
            } else {
                try {
                    if (module.getName() != null && module.getSn() != null && module.getUrl() != null) {
                        moduleService.save(module);
                    }
                    result.setMsg("操作成功");
                    result.setSuccessful(true);
                } catch (ExistedException e) {
                    result.setMsg(e.getMessage());
                    result.setSuccessful(false);
                    return result;
                }
            }
        }
        return result;
    }
}

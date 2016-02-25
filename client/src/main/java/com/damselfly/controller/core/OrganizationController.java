package com.damselfly.controller.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.DataAttributes;
import com.damselfly.common.util.FuelueTree;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.entity.Organization;
import com.damselfly.entity.User;
import com.damselfly.exception.ExistedException;
import com.damselfly.exception.ServiceException;
import com.damselfly.service.core.OrganizationRoleService;
import com.damselfly.service.core.OrganizationService;
import com.damselfly.service.core.UserService;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by v on 2014/5/12.
 */

@Controller
@RequestMapping("/console/security/organization")
public class OrganizationController extends BaseAction {

    private static final String EDIT = "console/security/organization/edit";
    private static final String LIST = "console/security/organization/list";
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationRoleService organizationRoleService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return LIST;
    }


    @RequestMapping(value="add", method= RequestMethod.GET)
    public ModelAndView add() {

        ModelAndView mav = new ModelAndView(EDIT);
        Organization organization = new Organization();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(organization);
            mav.addObject("message", "success");
            mav.addObject("organization",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id) {

        ModelAndView mav = new ModelAndView(EDIT);
        Organization organization = organizationService.get(Long.valueOf(id));
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(organization);
            mav.addObject("message", "success");
            mav.addObject("organization",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value = "/getListAll", method = RequestMethod.GET)
    @ResponseBody
    public GridModel getListAll() {
        Organization organization = form(Organization.class);
        Page info = organizationService.findByPage(page(),organization);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }


    @RequestMapping(value="/findAll")
    @ResponseBody
    public List<Organization>  findAll() {
        List<Organization> list = organizationService.findAll();
        return list;
    }


    @RequestMapping(value="/getTree")
    @ResponseBody
    public Organization  getTree() {
        return organizationService.getTree();
    }

    @RequestMapping(value="/findById/{id}", method= RequestMethod.POST)
    @ResponseBody
    public Organization  findById(@PathVariable Long id) {
        System.out.println("id = " + id);
        Organization organization1 = organizationService.get(id);
        return organization1;
    }


    @RequestMapping(value = "/findJsonById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findJsonById(@PathVariable Long id) {
        System.out.println("id = " + id);
        List<Organization> organization = organizationService.findByParentId(id);
        String json = "";
        JSONObject jsonobject = new JSONObject();
        JSONArray jarray = new JSONArray();
        for (Organization o : organization) {
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

            User user = new User();
            user.setOrgId(Long.valueOf(id));
            if(userService.find(user).size()>0) {
                result.setMsg("该模块配置了用户，不可以删除");
                result.setSuccessful(false);
                return result;
            }

            // : 配置了角色
            if(organizationRoleService.find(Long.valueOf(id)).size()>0)
            {
                result.setMsg("该模块配置了角色，不可以删除");
                result.setSuccessful(false);
                return result;
            }

            if(organizationService.findByParentId(Long.valueOf(id)).size()>0) {
                result.setMsg("该模块有子部门，不可以删除");
                result.setSuccessful(false);
                return result;
            }

            else {
                try {
                    organizationService.delete(Long.valueOf(id));
                    result.setMsg("操作成功");
                    result.setSuccessful(true);
                }
                catch (ServiceException s)
                {
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
    public Result deleteModule(@ModelAttribute Organization organization) {
        Result result = new Result();

        if(organization.getName() != null&& !(organization.getId()==null)){

            if(organizationService.find(null,organization.getName(),null).size()>1) {
                result.setMsg("已存在多个，不可添加");
                result.setSuccessful(false);
                return result;
            }
            else {
                try {
                    organizationService.update(organization);
                    result.setMsg("操作成功");
                    result.setSuccessful(true);
                }
                catch (ExistedException e)
                {
                    result.setMsg(e.getMessage());
                    result.setSuccessful(false);
                    return result;
                }
            }
        }
        if(organization.getName() != null&& organization.getId()==null){//treegrid 添加后直接修改变为了修改行，无添加行
            if(organization.getName() != null){
                if(organizationService.getByName(organization.getName())!=null) {
                    result.setMsg("已存在，不可添加");
                    result.setSuccessful(false);
                    return result;
                }
                else {
                    try {
                        if(organization.getName() != null)
                            organizationService.save(organization);
                        result.setMsg("操作成功");
                        result.setSuccessful(true);
                    }
                    catch (ExistedException e)
                    {
                        result.setMsg(e.getMessage());
                        result.setSuccessful(false);
                        return result;
                    }
                }
            }
        }
        return result;
    }

}

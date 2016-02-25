package com.damselfly.controller.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.entity.*;
import com.damselfly.service.core.*;
import com.damselfly.viewModel.GridModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v on 2014/7/22.
 */

@Controller
@RequestMapping("/console/security/role")
public class RoleController extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private static final String LIST = "console/security/role/list";
    private static final String EDIT = "console/security/role/edit";
    private static final String PERMISSION = "console/security/role/permission";

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @RequiresPermissions("Role:show")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list() {
        return LIST;
    }

    /**
     * 获取列表
     * @return
     */
    @RequiresPermissions("Role:show")
    @RequestMapping(value = "/getListAll", method = RequestMethod.GET)
    @ResponseBody
    public GridModel getListAll() {
        Role role = form(Role.class);
        Page info = roleService.findByPage(page(),role);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/findAllRole/{id}")
    @ResponseBody
    public List<Role> findAllRole(@PathVariable Long id) {
        logger.debug("id = " + id);
        List<Role> list = roleService.findAll();
        return list;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequiresPermissions("Role:delete")
    @RequestMapping(value = "/deleteInfo/{id}")
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        Result result = new Result();
        UserRole userRole = new UserRole();
        if (id != null) {
            userRole.setRoleId(id);
            if (userRoleService.find(userRole).size() > 0) {
                result.setMsg("该角色有用户，不可以删除");
                result.setSuccessful(false);
                return result;
            } else {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(id);
                rolePermissionService.delete(rolePermission);//删除角色全部权限
                roleService.delete(id);
            }
        }
        result.setMsg("删除成功");
        result.setSuccessful(true);
        return result;
    }

    /**
     * 修改角色
     *
     * @return
     */
    @RequiresPermissions("Role:update")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result modify() {
        Result result = new Result();
        Role role = form(Role.class);
        if (role.getId() != null) {
            role.setName(role.getName());
            if (roleService.find(null, role).size() > 1) {
                result.setMsg("已存在角色多个，不可修改");
                result.setSuccessful(false);
                return result;
            } else {
                roleService.update(role);
            }
        }
        result.setMsg("修改成功");
        result.setSuccessful(true);
        return result;
    }

    /**
     * 添加页面
     * @return
     */
    @RequiresPermissions("Role:add")
    @RequestMapping(value="/add")
    @ResponseBody
    public ModelAndView add (){
        ModelAndView mav = new ModelAndView(EDIT);
        Role role = new Role();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(role);
            mav.addObject("message", "success");
            mav.addObject("role",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }


    /**
     * 修改页面
     * @return
     */
    @RequiresPermissions("Role:edit")
    @RequestMapping(value="/edit/{id}")
    @ResponseBody
    public ModelAndView edit (@PathVariable Long id){
        ModelAndView mav = new ModelAndView(EDIT);
        Role role = roleService.get(id);
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(role);
            mav.addObject("message", "success");
            mav.addObject("role",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }



    /**
     * 添加角色
     *
     * @return
     */
    @RequiresPermissions("Role:add")
    @RequestMapping(value = "/saveInfo")
    @ResponseBody
    public Result save(@ModelAttribute Role role) {
        Result result = new Result();
        if (role.getId() != null) {
            role.setName(role.getName());
            if (roleService.find(null, role).size() > 1) {
                result.setMsg("已存在角色，不可添加");
                result.setSuccessful(false);
                return result;
            } else {
                roleService.update(role);
            }
        }else {
            roleService.save(role);
        }
        result.setMsg("添加成功");
        result.setSuccessful(true);
        return result;
    }


    /**
     * 角色权限设置
     * @param id
     * @return
     */
    @RequiresPermissions("Role:edit")
    @RequestMapping(value="/editRolePermission/{id}")
    @ResponseBody
    public ModelAndView rolePermission (@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView(PERMISSION);
        List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(null,id);
        List<Permission> list = new ArrayList<>();
        for(RolePermission permission: rolePermissions)
        {
            list.add(permissionService.get(permission.getPermissionId()));
        }
        ObjectMapper mapper = JacksonMapper.getInstance();
        try {
            String json =mapper.writeValueAsString(list);
            modelAndView.addObject("PermissionList",json);//一个角色拥有的权限
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("roleid",id);//角色
        return modelAndView;
    }

    /**
     * 角色权限保存
     * @param id
     * @param rolePermissionStr
     * @return
     */
    @RequestMapping(value="/saveRolePermission/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result saveRolePermission(@PathVariable Long id,@RequestParam String rolePermissionStr){
        //设置请求编码
        Result result = new Result();
        try {
            //获取编辑数据 这里获取到的是json字符串

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);
            rolePermissionService.delete(rolePermission);//删除角色全部权限

            if(rolePermissionStr!=null){
            String[] rPermission = rolePermissionStr.split(",");
            if(rolePermissionStr.indexOf(",")>0)
            for (String p : rPermission) {
                String[] per = p.split("_");
                Permission permission = new Permission();
                permission.setShortName(per[0]);
                permission.setModuleId(Long.valueOf(per[1]));
                List<Permission> permissions = permissionService.get(permission);//查找选择的模块权限ID
                if(permissions.size()==0)
                {
                    permission.setName(per[0]);
                    permissionService.save(permission);
                    permission.setName(null);
                    List<Permission> permissions_add_then = permissionService.get(permission);//查找选择的模块权限ID
                    for (Permission p2 : permissions_add_then) {
                        RolePermission rolePermission_in = new RolePermission();
                        rolePermission_in.setPermissionId(p2.getId());
                        rolePermission_in.setRoleId(id);
                        rolePermissionService.save(rolePermission_in);//添加角色选择的模块权限
                    }
                }
                else {
                    for (Permission p2 : permissions) {
                        RolePermission rolePermission_in = new RolePermission();
                        rolePermission_in.setPermissionId(p2.getId());
                        rolePermission_in.setRoleId(id);
                        rolePermissionService.save(rolePermission_in);//添加角色选择的模块权限
                    }
                }
            }
            result.setMsg("操作成功");
            result.setSuccessful(true);
            }
        }
        catch (Exception e)
        {
            result.setMsg("操作失败");
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

}

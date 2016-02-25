package com.damselfly.controller.core;

import com.alibaba.fastjson.JSON;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.entity.Role;
import com.damselfly.entity.RoleSelect;
import com.damselfly.entity.User;
import com.damselfly.entity.UserRole;
import com.damselfly.service.core.RoleService;
import com.damselfly.service.core.UserRoleService;
import com.damselfly.service.core.UserService;
import com.damselfly.shiro.ShiroUser;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/console/security/user")
public class UserController extends BaseAction{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private static final String LIST = "console/security/user/list";
    private static final String SHOW = "console/security/user/show";
    private static final String ADDEDIT = "console/security/user/edit";
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    private EhCacheCacheManager ehCacheCacheManager;

    @RequiresPermissions("User:show")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/show", method= RequestMethod.GET)
    public String show(HttpServletRequest request) {
        return SHOW;
    }

    @RequestMapping(value="/userList")
    @ResponseBody
    public GridModel userList(){/*
        Dictionary dictionary = new Dictionary();
        dictionary.setDicName("USER-STATUS");
        List<Dictionary> list_sql  = dictionaryService.findAll(null,dictionary);

        List<Dictionary> list_eache  = dictionaryService.findAll(null,dictionary);

        dictionaryService.delete(79);

        List<Dictionary> list_eache_after_delete  = dictionaryService.findAll(null,dictionary);

*/
        User user = form(User.class);
        Page info = userService.findByPage(page(), user);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/userEdit")
    @ResponseBody
    public ModelAndView userEdit() {
        ModelAndView mav = new ModelAndView(ADDEDIT);
        User user = new User();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(user);
            mav.addObject("message", "success");
            mav.addObject("user",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/saveUser")
    @ResponseBody
    public Result saveUser(@ModelAttribute User user) {
        Result result = new Result();
        try {
            if(user.getId()==null) {
                if (userService.getByUsername(user.getUsername()) != null) {
                    result.setSuccessful(false);
                    result.setMsg("用户添加失败，登录名：" + user.getUsername() + "已存在。");
                    return result;
                }

                if (userService.getByEmail(user.getEmail()) != null) {
                    result.setSuccessful(false);
                    result.setMsg("用户添加失败，登录邮箱：" + user.getEmail() + "已存在。");
                    return result;
                }
                userService.save(user);
            }
            else
            {
                userService.update(user);
            }
            result.setSuccessful(true);
            result.setMsg("ok");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("error");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/editUser/{id}")
    @ResponseBody
    public ModelAndView editUser(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(ADDEDIT);
        try {
            User user = userService.get(Long.parseLong(id + ""));
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(user);
            mav.addObject("message", "success");
            mav.addObject("user",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/saveUserList")
    @ResponseBody
    @Transactional
    public Result saveUserList(HttpServletRequest req){
        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String deleted = req.getParameter("deleted");
        String inserted = req.getParameter("inserted");
        String updated = req.getParameter("updated");

        Result result = new Result();
        User user_temp = new User();
        Page info;

        //删除
        if(deleted != null){
            //把json字符串转换成对象
            List<User> listDeleted = JSON.parseArray(deleted, User.class);
            for(User ur : listDeleted){
                if(ur.getId() != null){
                    logger.debug("ur.getId()=============="+ur.getId());
                    if(ur.getId()==1) {
                        logger.debug("ADMIN=====DELETE=====ERROR====");
                        result.setMsg("ADMIN=====DELETE=====ERROR====");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        List<UserRole> list = userRoleService.find(ur.getId());
                        for(UserRole role : list)
                        {
                            userRoleService.delete(role.getId());
                        }
                        userService.delete(ur.getId());
                        result.setMsg("删除成功");
                        result.setSuccessful(true);
                    }
                }
            }
        }

        //添加

        if(inserted != null){
            List<User> listInserted = JSON.parseArray(inserted, User.class);
            for(User ur : listInserted){
                if(ur.getId() != null){
                    logger.debug("ur.getId()=============="+ur.getId());
                    user_temp.setUsername(ur.getUsername());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=NAME=repeat======ERROR====");

                        result.setMsg("USER=NAME=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setUsername(null);
                    user_temp.setEmail(ur.getEmail());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=EMAIL=repeat======ERROR====");
                        result.setMsg("USER=EMAIL=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setEmail(null);
                    user_temp.setPhone(ur.getPhone());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=Phone=repeat======ERROR====");
                        result.setMsg("USER=Phone=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    if(ur.getUserRoles().size()>0) {

                    }
                    result.setMsg("OK");
                    result.setSuccessful(true);
                    userService.save(ur);
                }
            }
        }

        //修改
        if(updated != null){
            //把json字符串转换成对象
            List<User> listUpdated = JSON.parseArray(updated, User.class);
            for(User ur : listUpdated){
                if(ur.getId() != null&&ur.getId()!=1){
                    logger.debug("ur.getId()=============="+ur.getId());
                    user_temp.setUsername(ur.getUsername());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=NAME=repeat======ERROR====");

                        result.setMsg("USER=NAME=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }
                    user_temp.setUsername(null);
                    user_temp.setEmail(ur.getEmail());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=EMAIL=repeat======ERROR====");
                        result.setMsg("USER=EMAIL=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setEmail(null);
                    user_temp.setPhone(ur.getPhone());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=Phone=repeat======ERROR====");
                        result.setMsg("USER=Phone=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }
                    result.setMsg("OK");
                    result.setSuccessful(true);
                    userService.update(ur);
                }
            }
        }

        return result;

    }

    @RequestMapping(value="/editinfo/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result editingUser(@ModelAttribute User user, @PathVariable Integer id) {
        logger.debug("id = " + id);
        Result result = new Result();
        if(!Strings.isNullOrEmpty(id+""))
        userService.update(user);
        else
        userService.save(user);
        result.setMsg("操作成功");
        result.setSuccessful(true);
        return result;
    }


    @RequestMapping(value="/deleteInfo/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result deleteInfo(@ModelAttribute User user, @PathVariable Integer id) {
        logger.debug("id = " + id);
        Result result = new Result();
        user.setStatus("disabled");
        userService.delete(user);
        result.setMsg("操作成功");
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/userinfo")
    @ResponseBody
    public User userInfo(HttpServletRequest request) throws AuthenticationException {
        ShiroUser shiroUser = com.damselfly.shiro.SecurityUtils.getShiroUser();
        User user = userService.get(shiroUser.getUser().getId());
        return user;
    }
    @RequestMapping(value="/findAllRole/{id}")
    @ResponseBody
    public List<Role> findAllRole(@PathVariable Long id) {
        logger.debug("id = " + id);
        List<Role> list = roleService.findAll();
        return list;
    }


    @RequestMapping(value="/findRole")
    @ResponseBody
    public List<Role> findAllRole(@RequestParam String description) {
        Role role = new Role();
        role.setDescription(description);
        List<Role> list = roleService.find(null,role);
        return list;
    }

    @RequestMapping(value="/findAllRoleSelect/{id}/{roles}")
    @ResponseBody
    public List<RoleSelect> findAllRoleSelect(@PathVariable Long id,@PathVariable String roles) {
        logger.debug("id = " + id);
        List<RoleSelect> roleSelects = new ArrayList<>();
        List<Role> list = roleService.findAll();

        for(Role role: list)
        {
            RoleSelect roleSelect = new RoleSelect();
                roleSelect.setId(role.getId());
                roleSelect.setDescription(role.getDescription());
                for (String roleId : StringUtils.split(roles, ",")) {
                    if(StringUtils.equals(role.getId().toString(),roleId)) {
                        roleSelect.setSelected(true);
                        break;
                    }
                }
                roleSelects.add(roleSelect);
        }
        return roleSelects;
    }
}

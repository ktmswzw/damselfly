package com.damselfly.controller.core;

import com.damselfly.common.SecurityConstants;
import com.damselfly.common.util.Result;
import com.damselfly.entity.Login;
import com.damselfly.shiro.CaptchaUsernamePasswordToken;
import com.damselfly.shiro.IncorrectCaptchaException;
import com.damselfly.shiro.RepeatLoginException;
import com.damselfly.shiro.ShiroDbRealm;
import com.damselfly.viewModel.Json;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class); 
	
	private static final String LOGIN_PAGE = "login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";
	
	/*
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return LOGIN_PAGE;
	}
*/


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(LOGIN_PAGE);
        String salt = UUID.randomUUID().toString();
        modelAndView.addObject("salt",salt);
        return modelAndView;
    }

    @RequestMapping(value="/autoLoad")
	@ResponseBody
	public Result load(String username,String password) throws Exception {

        Result result = new Result();
		Subject subject=SecurityUtils.getSubject();
		CaptchaUsernamePasswordToken token=new CaptchaUsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        try{
            result.setSuccessful(false);
            subject.login(token);

            Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
            cookie.setHttpOnly(true); //more secure, protects against XSS attacks

            result.setMsg(SecurityConstants.LOGIN_IS_SUCCESS);
            result.setSuccessful(true);
        }
        catch (UnknownSessionException use) {
            subject = new Subject.Builder().buildSubject();
            subject.login(token);
            LOG.error(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
            result.setMsg(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
        }
        catch(UnknownAccountException ex){
			LOG.error(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
			result.setMsg(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
		}
        catch (IncorrectCredentialsException ice) {
            result.setMsg(SecurityConstants.INCORRECT_CREDENTIALS_EXCEPTION);
        } 
        catch (LockedAccountException lae) {
            result.setMsg(SecurityConstants.LOCKED_ACCOUNT_EXCEPTION);
        }
        catch (DisabledAccountException dae) {
            result.setMsg(SecurityConstants.DISABLED_ACCOUNT_EXCEPTION);
        }
        catch (IncorrectCaptchaException e) {
        	 result.setMsg(SecurityConstants.INCORRECT_CAPTCHA_EXCEPTION);
		}catch (RepeatLoginException e) {
        	result.setMsg(SecurityConstants.REPEAT_LOGIN_EXCEPTION);
		}
        catch (AuthenticationException ae) {
            result.setMsg(SecurityConstants.AUTHENTICATION_EXCEPTION);
        } 
        catch(Exception e){
            result.setMsg(SecurityConstants.UNKNOWN_EXCEPTION);
        }
		return result;
	}


    //验证码校验
    public boolean doCaptchaValidate(CaptchaUsernamePasswordToken token){
        String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (captcha != null &&!captcha.equalsIgnoreCase(token.getCaptcha())){
            return false;
        }
        return true;
    }

    @RequestMapping(value="/do",method= RequestMethod.POST)
    @ResponseBody
    public Result loadB(@ModelAttribute Login login) throws Exception {

        Result result = new Result();
        Subject subject=SecurityUtils.getSubject();
        CaptchaUsernamePasswordToken token=new CaptchaUsernamePasswordToken();
        token.setUsername(login.getUsername());
        token.setPassword(login.getPassword().toCharArray());
        token.setCaptcha(login.getCaptcha());
        token.setRememberMe(login.getRememberMe()!=null&&login.getRememberMe().equals("on")?true:false);
        try{
            result.setSuccessful(false);
            ShiroDbRealm shiroDbRealm = new ShiroDbRealm();

            try {
                if (!doCaptchaValidate(token)) {//忽略大小写。
                    throw new IncorrectCaptchaException("验证码错误");
                }
            } catch (Exception e) {
                // session如果没有刷新，validateResponseForID会抛出com.octo.captcha.service.CaptchaServiceException的异常
                throw new IncorrectCaptchaException("验证码错误");
            }
            subject.login(token);
            LOG.debug("sessionTimeout===>"+subject.getSession().getTimeout());
            result.setMsg(SecurityConstants.LOGIN_IS_SUCCESS);
            result.setSuccessful(true);
        }
        catch (UnknownSessionException use) {
            subject = new Subject.Builder().buildSubject();
            subject.login(token);
            LOG.error(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
            result.setMsg(SecurityConstants.UNKNOWN_SESSION_EXCEPTION);
        }
        catch(UnknownAccountException ex){
            LOG.error(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
            result.setMsg(SecurityConstants.UNKNOWN_ACCOUNT_EXCEPTION);
        }
        catch (IncorrectCredentialsException ice) {
            result.setMsg(SecurityConstants.INCORRECT_CREDENTIALS_EXCEPTION);
        }
        catch (LockedAccountException lae) {
            result.setMsg(SecurityConstants.LOCKED_ACCOUNT_EXCEPTION);
        }
        catch (DisabledAccountException dae) {
            result.setMsg(SecurityConstants.DISABLED_ACCOUNT_EXCEPTION);
        }
        catch (IncorrectCaptchaException e) {
            result.setMsg(SecurityConstants.INCORRECT_CAPTCHA_EXCEPTION);
        }catch (RepeatLoginException e) {
            result.setMsg(SecurityConstants.REPEAT_LOGIN_EXCEPTION);
        }
        catch (AuthenticationException ae) {
            result.setMsg(SecurityConstants.AUTHENTICATION_EXCEPTION);
        }
        catch(Exception e){
            result.setMsg(SecurityConstants.UNKNOWN_EXCEPTION);
        }
        finally {

        }
        return result;
    }
	
}

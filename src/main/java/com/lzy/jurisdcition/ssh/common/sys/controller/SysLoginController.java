package com.lzy.jurisdcition.ssh.common.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzy.jurisdcition.ssh.common.base.controller.BaseController;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.util.EasyUITreeEntity;
import com.lzy.jurisdcition.ssh.common.util.MyJson;
import com.lzy.jurisdcition.ssh.common.util.StatusCode;

@Controller
@RequestMapping("/login")
public class SysLoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(SysLoginController.class);

	/**
	 * 转到登录页面
	 * @return
	 */
	@RequestMapping(value = "/toLogin")
	public String toPageLogin(Model model,HttpServletRequest request){
		
		if(request.getAttribute("loginErrorMsg") != null){
			model.addAttribute("MSG", request.getAttribute("loginErrorMsg"));
		}

        request.getSession();

		return "login";
	}
	
	/**
	 * 校验验证码
	 * @param verify  验证码
	 * @param request Request对象
	 * @return MyJosn
	 */
	@ResponseBody
	@RequestMapping(value = "/loginVerify" , produces="application/json" , method = RequestMethod.GET)
	public MyJson toVerifyLogin(
			@RequestParam(value = "verify")  String verify ,
			HttpServletRequest request){
		MyJson json = new MyJson();
		HttpSession session = request.getSession();
		if(!(session.getAttribute("rand")!= null && 
				session.getAttribute("rand").toString().equalsIgnoreCase(verify))){
			json.setMsg("验证码不正确!");
			json.setStatusCode(StatusCode.ERROR);
		}
		return json;
	}

	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public String toActionLogin(
			@RequestParam(value = "username" ,required = true) String username,
			@RequestParam(value = "password" , required = true) String password,
			HttpServletRequest request,
			Model model){
		HttpSession session = request.getSession();
		SysUser user = new SysUser();
		user.setUserName(username);
		user.setUserPassword(password);
		SysUser user1 = sysUserService.login(user);
		if(user1 != null){
			session.setAttribute("user", user1);
			session.setMaxInactiveInterval(30 * 60);//设置会话时间为30分钟
			return "main";
		}
	model.addAttribute("MSG","输入信息有误，请重新登录");
	return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/menuTree" , produces = "application/json" , method = RequestMethod.POST)
	public MyJson getMenuTree(HttpServletRequest request){
		MyJson json = new MyJson();
		List<EasyUITreeEntity> data = super.setJurisdiction(request);
		json.setData(data);
		logger.info("调用getMenuTree==>>"+json);
		return json;
	}
	

	
	@RequestMapping("/logout")
	public String toActionLogout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:toLogin";
	}

    @RequestMapping(value = "/updatePwd" ,method = RequestMethod.GET)
    public String updatePwd(HttpServletRequest request , String newPwd){

        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        sysUserService.changePwd(Integer.valueOf(sysUser.getUserId()) ,newPwd);
        return "redirect:toLogin";
    }
	
}

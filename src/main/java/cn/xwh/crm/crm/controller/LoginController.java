package cn.xwh.crm.crm.controller;

import cn.xwh.crm.crm.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;


/**
 * @Auther: xwh
 * @Date: 2019/10/6 11:33
 * @Description:
 */
@Controller
public class LoginController {

    @Resource
    private SysUserService sysUserService;

    //跳转到登录页面
    @RequestMapping(value="/login.html", method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }

    //跳转到index.html页面，首页
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String toIndex(){
        return "index";
    }

    //处理登录请求
    @RequestMapping(value="/login.html", method = RequestMethod.POST)
    public String doLogin(String usrName, String usrPassword, Model model){
        //1、获取当前的Subject(用户)
        Subject currentUser = SecurityUtils.getSubject();
        //2、判断当前的用户是否已经被认证。即是否已经登录
        if (!currentUser.isAuthenticated()) {
            //3、若没有被认证，则把用户名和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(usrName, usrPassword);
            try {
                //4、执行登录
                currentUser.login(token);
                System.out.println("===测试git");

            } catch ( AuthenticationException ae ) {
                model.addAttribute("msg", ae.getMessage());// 登录失败移除用户
                SecurityUtils.getSubject().getSession().removeAttribute("sessionUser");
                return "login";
            }
        }
        return "redirect:/index.html";  //重定向到首页
    }
}

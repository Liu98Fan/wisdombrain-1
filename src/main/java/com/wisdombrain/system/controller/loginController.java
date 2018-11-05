package com.wisdombrain.system.controller;

import com.wisdombrain.system.entities.Vuser;
import com.wisdombrain.system.service.baseService;
import com.wisdombrain.system.service.userService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class loginController {

    @Autowired
    userService userService;
    @Autowired
    baseService baseService;

    @RequestMapping(value = "sign")
    public String sign(){
        return "gateway/login";
    }
    @RequestMapping(value="/login")
    public String login(String username, String password, boolean rememberMe, Model model, HttpSession session, HttpServletRequest request){
        String serverName = request.getServerName();//localhost
        int serverPort = request.getServerPort();//8080
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
            String vusername = token.getUsername();
            Vuser vuser = baseService.getCurrentVuser();
            session.setAttribute("username",vuser.getUsername());
            session.setAttribute("id",vuser.getId());
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest==null){
                return "gateway/main";
            }else{
                return "redirect://"+serverName+":"+serverPort+savedRequest.getRequestUrl();
            }
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户不存在或邮箱尚未验证");
            return "gateway/login";
        }catch (IncorrectCredentialsException e1){
            model.addAttribute("msg","用户名或密码错误");
            return "gateway/login";
        }catch (Exception e2){
            model.addAttribute("msg","未知错误");
            return "gateway/login";
        }
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            SecurityUtils.getSubject().logout();
        }
        return "gateway/login";
    }
    @RequiresPermissions("info:view")
    @RequestMapping(value = "/main")
    public String mainEntrance(){
        return "gateway/main";
    }
    @RequestMapping(value = "/usercheck")
    @ResponseBody
    public String usercheck(HttpServletRequest request){
        String username = request.getParameter("username");
        Vuser vuser = userService.getVuserToCheckRepeat(username);
        if(vuser!=null){
            return "yes";
        }else{
            return "no";
        }
    }
    @RequestMapping(value = "/testtime")
    public String authorization(){
        return baseService.getCurrentTime();
    }
}

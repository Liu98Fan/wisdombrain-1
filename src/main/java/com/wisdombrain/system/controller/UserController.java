package com.wisdombrain.system.controller;

import com.wisdombrain.system.entities.User;
import com.wisdombrain.system.service.BaseService;
import com.wisdombrain.system.service.UserService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BaseService baseService;


    @RequestMapping("/login")
    public String login(String username, String password, @RequestParam(value = "isEncrypt", defaultValue = "0") String isEncrypt, Model model, HttpSession session, HttpServletRequest request) {
        String servername = request.getServerName();
        int port = request.getServerPort();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            try {
                if (isEncrypt.equals("0")) {
                    User user = userService.getUserByUserName(username);
                    password = baseService.Encrypt("MD5", password, (ByteSource) baseService.ConvertSaltByte(user.getSalt())).toString();
                }
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
                //下面进入realm
                //得到被拦截的url
                User user = (User) subject.getPrincipal();
                session.setAttribute("currentUser", user);
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                if (savedRequest == null) {
                    return "redirect:/main/entrance";
                } else {
                    return "redirect://" + servername + ":" + port + savedRequest.getRequestUrl();
                }
            } catch (UnknownAccountException e) {
                model.addAttribute("msg", e.getMessage());
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("msg", "用户名或密码错误");
            } catch (Exception e) {
                model.addAttribute("msg", "未知错误:" + e.getMessage());
            }
            return "gateway/login";
        }

        return "redirect:/main/entrance";
    }

    @RequestMapping("/registerEntrance")
    public String registerEntrance() {
        return "gateway/register";
    }

    @RequestMapping("/loginEntrance")
    public String loginEntrance() {
        return "gateway/login";
    }

    /**
     * 注册
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Model model) {
        String password = user.getPassword();
        user.setSalt(user.getUsername());
        ByteSource byteSource = (ByteSource) baseService.ConvertSaltByte(user.getUsername());
        user.setSaltByte(byteSource);
        Object result = baseService.Encrypt("MD5", password, byteSource);
        user.setPassword(result.toString());
        user.setNewdate(user.getDate());
        if (userService.register(user)) {
            return "redirect:/user/login?username=" + user.getUsername() + "&password=" + user.getPassword() + "&isEncrypt=1";
        } else {
            model.addAttribute("msg", "注册失败请稍后重试");
            return "register";
        }

    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/sign";
    }

    /**
     * 检测用户名是否合法
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public Object checkUsername(String username) {
        JSONObject json = new JSONObject();
        String success = "success";
        String message = null;
        int code = 200;
        if (!userService.checkUsername(username)) {
            success = "fail";
            //3001表示用户名已经存在
            code = 3001;
            message = "用户名已经存在";
        }
        json.put("success", success);
        json.put("message", message);
        json.put("code", code);
        return json;
    }
}

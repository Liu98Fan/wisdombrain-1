package com.wisdombrain.system.controller;

import com.mysql.cj.xdevapi.JsonArray;
import com.wisdombrain.system.entities.Student;
import com.wisdombrain.system.entities.Teacher;
import com.wisdombrain.system.entities.User;
import com.wisdombrain.system.service.BaseService;
import com.wisdombrain.system.service.UserService;
import com.wisdombrain.system.util.Info;
import com.wisdombrain.system.util.MD5;
import com.wisdombrain.system.util.Msg;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    BaseService baseService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "/sign")
    public String sign(Model model){
        return "gateway/login";
    }
    /**
     * 判断用户是否已经注册
     */
    @RequestMapping(value = "hasRegister")
    @ResponseBody
    public JSONObject hasRegister(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String date = baseService.getCurrentTime();
        String number = request.getParameter("username");
        String role = request.getParameter("role");
        User user = userService.getStudentInfoToCheck(role, number);
        if(user != null){
             jsonObject = Msg.msg(Info.SUCCESS,date,0);
        }else{
             jsonObject = Msg.msg(Info.UNREGISTER,date,1);
        }
        return jsonObject;

    }
    /**
     * 判断当前用户是否已经注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/usercheck")
    @ResponseBody
    public JSONObject usercheck(HttpServletRequest request){
        JSONObject jsonValues = new JSONObject();
        String number = request.getParameter("username");
        String role = request.getParameter("roleName");
        User user = userService.getStudentInfoToCheck(role, number);
        String currentTime = baseService.getCurrentTime();
        if(user == null){
            jsonValues = Msg.msg(Info.SUCCESS,currentTime,0);
        }else{
            jsonValues = Msg.msg(Info.EXISTFAIL,currentTime,1);
        }
        return jsonValues;
    }
    /**
     * 判断当前用户的信息是否存在
     * @param request
     * @return
     */
    @RequestMapping(value = "/usernumbercheck")
    @ResponseBody
    public JSONObject usernumbercheck(HttpServletRequest request){
        JSONObject jsonValues = new JSONObject();
        String number = request.getParameter("username");
        String role = request.getParameter("role");
        User stu = userService.getStudentNumberToCheck(number,role);
        String currentTime = baseService.getCurrentTime();
        if(stu != null){
            jsonValues = Msg.msg(Info.SUCCESS,currentTime,0);
        }else{
            jsonValues = Msg.msg(Info.UNEXISTFAIL,currentTime,1);
        }
        return jsonValues;
    }

    /**
     * 信息系统的登录
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public JSONObject login(HttpServletRequest request){
       JSONObject jsonObject = new JSONObject();
        String number = request.getParameter("number");
        String role = request.getParameter("role");
        String date = baseService.getCurrentTime();
        User user = userService.getUser(number, role);

        String salt = user.getSalt();
        String password = user.getPassword();

        String passwd = request.getParameter("passwd");

        String PIN = MD5.MD5(passwd + salt);

        if(PIN.equals(password)){
            jsonObject = Msg.msg(Info.SUCCESS,date,0);
        }else{
            jsonObject = Msg.msg(Info.WPASSWORD,date,1);
        }

        return jsonObject;

    }

    /**
     * 注册页面入口
     * @return
     */
    @RequestMapping(value = "/signup")
    public String signup(){
        return "gateway/register";
    }




    /**
     * 这里为用户注册
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(HttpServletRequest request, User user, String roleName, Model model) {
        JSONObject jsonArray = new JSONObject();
        String date = baseService.getCurrentTime();
        //为用户设置salt
        String salt = baseService.getUUID();
        user.setSalt(salt);
        ByteSource byteSource = (ByteSource) baseService.ConvertSaltByte(salt);
        user.setSaltByte(byteSource);
        //用MD5进行加密
        Object result = baseService.Encrypt("MD5", user.getPassword(), byteSource);
        user.setPassword(result.toString());
        user.setNewdate(user.getDate());
        //获取注册传来的角色名字，老师/学生
        String mark = userService.insertUser(user.getId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getDate(), user.getNewdate(), 0, roleName);
        if (mark.equals("succ")) {
            return "redirect:/user/login?username=" + user.getUsername() + "&password=" + user.getPassword() + "&isEncrypt=1";
        } else {
            model.addAttribute("msg", "注册失败请稍后重试");
            return "gateway/register";
        }
    }

}


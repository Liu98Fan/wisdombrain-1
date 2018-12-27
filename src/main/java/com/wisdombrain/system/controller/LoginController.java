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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    BaseService baseService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "sign")
    public String sign(){
        return "gateway/login";
    }
    /**
     * 判断用户是否已经注册
     */
    @RequestMapping(value = "hasRegister")
    @ResponseBody
    public JSONArray hasRegister(HttpServletRequest request){
        JSONArray jsonArray = new JSONArray();
        String date = baseService.getCurrentTime();
        String number = request.getParameter("number");
        String role = request.getParameter("role");
        User user = userService.getStudentInfoToCheck(role, number);
        if(user != null){
            jsonArray = Msg.msg(Info.SUCCESS,date,0);
        }else{
            jsonArray = Msg.msg(Info.UNREGISTER,date,1);
        }
        return jsonArray;

    }

    /**
     * 信息系统的登录
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public JSONArray login(HttpServletRequest request){
        JSONArray jsonArray = new JSONArray();
        String number = request.getParameter("number");
        String role = request.getParameter("role");
        String date = baseService.getCurrentTime();
        User user = userService.getUser(number, role);

        String salt = user.getSalt();
        String password = user.getPassword();

        String passwd = request.getParameter("passwd");

        String PIN = MD5.MD5(passwd + salt);

        if(PIN.equals(password)){
            jsonArray = Msg.msg(Info.SUCCESS,date,0);
        }else{
            jsonArray = Msg.msg(Info.WPASSWORD,date,1);
        }

        return jsonArray;

    }
    /*@RequestMapping(value="/login")
    public String login(String username, String password,boolean rememberMe,Model model,HttpSession session,HttpServletRequest request){
        String serverName = request.getRemoteHost();//localhost
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
                return "main";
            }else{
                return "redirect:" + savedRequest.getRequestUrl();
            }
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户不存在或邮箱尚未验证");
            return "login";
        }catch (IncorrectCredentialsException e1){
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }catch (Exception e2){
            model.addAttribute("msg","未知错误");
            return "login";
        }
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            SecurityUtils.getSubject().logout();
        }
        return "login";
    }*/

    /**
     * 注册页面入口
     * @return
     */
    @RequestMapping(value = "/signup")
    public String signup(){
        return "gateway/register";
    }

    /**
     * 判断当前用户的信息是否存在
     * @param request
     * @return
     */
    @RequestMapping(value = "/usernumbercheck")
    @ResponseBody
    public JSONArray usernumbercheck(HttpServletRequest request){
        JSONArray jsonValues = new JSONArray();
        String number = request.getParameter("number");
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
     * 判断当前用户是否已经注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/usercheck")
    @ResponseBody
    public JSONArray usercheck(HttpServletRequest request){
        JSONArray jsonValues = new JSONArray();
        String number = request.getParameter("number");
        String role = request.getParameter("role");
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
     * 这里为用户注册
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public JSONArray register(HttpServletRequest request){
        JSONArray jsonArray = new JSONArray();
        String date = baseService.getCurrentTime();
        try {
            String number = request.getParameter("number");
            String passwd = request.getParameter("passwd");
            String salt = baseService.getUUID();
            passwd = MD5.MD5((passwd + salt));
            String role = request.getParameter("role");
            userService.insertUser(number,passwd,salt,date,0,role);
            jsonArray = Msg.msg(Info.INSERTSUCCESS,date,0);
        }catch (Exception e){
            e.printStackTrace();
            jsonArray = Msg.msg(Info.INSERTFAIL,date,1);
        }
        return jsonArray;
    }
    /**
     * 邮件验证
     *//*
    @RequestMapping(value = "/email")
    public String email(HttpServletRequest request,Model model){
        String uuid = request.getParameter("uuid");
        Vuser vuser = userService.getVuserByUUID(uuid);
        int emailconfirm = 0;
        vuser.setEmailconfirm(emailconfirm);
        try{
            userService.updateVuserByUUID(vuser);
            model.addAttribute("msg","验证成功！");
        }catch (Exception e){
            model.addAttribute("msg","验证失败！");
            e.printStackTrace();
        }
        return "mail";
    }
    @RequestMapping(value = "/StartCaptchaServlet")
    public void StartCaptchaServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);
    }*/
}


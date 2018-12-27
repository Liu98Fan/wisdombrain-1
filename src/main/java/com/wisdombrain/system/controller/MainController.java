package com.wisdombrain.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:LIUFAN
 * @date:2018/12/27
 */
@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping("/entrance")
    public String entrance(){
        return "admin/admin";
    }
}

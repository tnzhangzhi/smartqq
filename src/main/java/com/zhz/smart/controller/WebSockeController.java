package com.zhz.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zz1987 on 17/11/2.
 */
@Controller
public class WebSockeController {

    @RequestMapping("/hello")
    public String index(){
        return "index";
    }
}

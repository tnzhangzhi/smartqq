package com.zhz.smart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zz1987 on 17/10/31.
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "hello world";
    }
}

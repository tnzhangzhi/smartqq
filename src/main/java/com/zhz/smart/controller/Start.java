package com.zhz.smart.controller;


import com.zhz.smart.util.SmartQQApi;

import java.io.IOException;

/**
 * Created by zz1987 on 17/10/30.
 */
public class Start {

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartQQApi.login();
    }
}

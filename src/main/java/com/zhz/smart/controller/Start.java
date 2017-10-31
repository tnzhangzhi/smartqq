package com.zhz.smart.controller;

import com.zhz.robot.util.SmartQQApi;

import java.io.IOException;

/**
 * Created by zz1987 on 17/10/30.
 */
public class Start {
    private static String indexpage = "http://w.qq.com/";
    private static String qrcode = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=2&l=M&s=3&d=72&v=4&t=0.6399276740019129&daid=164&pt_3rd_aid=0";
    private static String cgi = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";
    private static String checklogin = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=http%3A%2F%2Fw.qq.com%2Fproxy.html&ptqrtoken=TOKEN&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1509420213462&js_ver=10231&js_type=1&login_sig=sn4dEzsnG8SkMsmInd2xqv0eswQE4SetxHw1FE7T7TfSw57Oc7wzfhqdSuj7Dxcd&pt_uistyle=40&aid=501004106&daid=164&mibao_css=m_webqq&";

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartQQApi.login();
    }
}

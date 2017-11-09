package com.zhz.smart.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zz1987 on 17/10/31.
 */
public class SmartQQApi {

    public static String ptwebqq;
    public static String vfwebqq;
    public static long uin;
    public static String psessionid;
    public static final long clientId=53999199L;
    public static String pturl;
    public static boolean is_login = false;


    static Logger logger = LoggerFactory.getLogger(SmartQQApi.class);

    public static boolean login(){
        if(!is_login) {
            index();
            cgiLogin();
            getQrCode(null);
            boolean result = checkLogin();
            if (result) {
                getPtwebqq();
                getVfwebqq();
                getUinAndPsessionid();
                is_login = true;
            }
            return result;
        }
        return true;
    }

    public static String getGroupList() {
        boolean flag = login();
        logger.info("开始获取群列表");
        if(flag) {
            JSONObject r = new JSONObject();
            r.put("vfwebqq", vfwebqq);
            r.put("hash", hash());

            String result = HttpUtil.post(Constant.GROUP_LIST, Constant.GROUP_LIST_REFERER, Constant.GROUP_LIST_ORGIN, r);
            logger.info(result);
            return result;
        }
        return null;
    }

    public static String getFriends(){
        boolean flag = login();
        logger.info("开始获取好友列表");
        if(flag) {
            JSONObject r = new JSONObject();
            r.put("vfwebqq", vfwebqq);
            r.put("hash", hash());

            String result = HttpUtil.post(Constant.FRIEND_LIST, Constant.FRIEND_LIST_REFERER, Constant.FRIEND_LIST_ORGIN, r);
            logger.info(result);
            return result;
        }
        return null;
    }

    public static String getDiscuss(){
        boolean flag = login();
        logger.info("开始获取讨论组列表");
        if(flag) {
            JSONObject r = new JSONObject();
            r.put("vfwebqq", vfwebqq);
            r.put("hash", hash());
            String url = Constant.DISCUSS_LIST.replace("PSESSIONID",psessionid).replace("VFWEBQQ",vfwebqq);
            String result = HttpUtil.stringMethod(url, Constant.DISCUSS_LIST_REFERER, Constant.DISCUSS_LIST_ORGIN);
            logger.info(result);
            return result;
        }
        return null;
    }

    public static void getPtwebqq(){
        logger.info("开始获取ptwebqq");
        HttpUtil.excute(pturl,Constant.GROUP_LIST_REFERER,null);
        ptwebqq = HttpUtil.map.get("ptwebqq");
    }

    public static void getVfwebqq(){
        logger.info("开始获取vfwebqq");
//        String url = Constant.VF_URL.replace("PTWEBQQ",ptwebqq);
        String result = HttpUtil.stringMethod(Constant.VF_URL,Constant.VF_REFERER,null);
        JSONObject object = JSONObject.fromObject(result);
        vfwebqq= object.getJSONObject("result").getString("vfwebqq");
    }

    public static void getUinAndPsessionid(){
        logger.info("开始获取uin and sessionid");
        JSONObject object = new JSONObject();
        object.put("status","online");
        object.put("psessionid","");
        object.put("clientid",53999199);
        object.put("ptwebqq",ptwebqq);
        String result = HttpUtil.post(Constant.PSESSION_ID,Constant.PSESSION_ID_REFERER,Constant.PSESSION_ID_ORGIN,object);
        JSONObject object2 = JSONObject.fromObject(result);
        psessionid = object2.getJSONObject("result").getString("psessionid");
        uin = object2.getJSONObject("result").getLong("uin");
    }

    public static String pullMessage(){
        boolean flag = login();
        logger.info("拉取消息。。。");
        if(flag) {
            JSONObject r = new JSONObject();
            r.put("ptwebqq", "");
            r.put("clientid", SmartQQApi.clientId);
            r.put("psessionid", SmartQQApi.psessionid);
            r.put("key", "");

            String result = HttpUtil.post(Constant.POLL_MSG, Constant.POLL_MSG_REFERER, Constant.POLL_MSG_ORIGIN, r);
            logger.info(result);
            return result;
        }
        return null;
    }

    public static void cgiLogin(){
        HttpUtil.excute(Constant.CGI,Constant.INDEX,null);
    }

    public static void index(){
        HttpUtil.excute(Constant.INDEX,null,null);
    }

    public static void getQrCode(String path){
        if(path==null || "".equals(path)){
            path = "/Users/zz1987/temp/qrcode.png";
        }
        File file = new File(path);
        try {
            file.createNewFile();
            InputStream is = HttpUtil.streamMethod(Constant.QR_CODE,Constant.CGI,null);
            if(is != null) {
                OutputStream out = new FileOutputStream(file);
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.close();
                is.close();
            }
            logger.info("二维码已生成,请扫码登录 . . . ");
        }catch (Exception e){
            logger.error("生成二维码过程中程序出错 ! {}",e.getMessage());
            e.printStackTrace();
        }

    }

    public static boolean checkLogin(){
        boolean success = false;
        int times = 0;
        while (true) {
            try {
                Thread.sleep(3 * 1000);
                String url = Constant.CHECK_LOGIN;
                String token = HttpUtil.map.get("qrsig");
                if(token==null || "".equals(token)){
                    logger.error("token 不能为空 checkLogin fail");
                    break;
                }
                url = url.replace("TOKEN", hash3(token)+"");
                String result = HttpUtil.stringMethod(url, Constant.CGI,null);
                if(result.contains("登录成功")){
                    success = true;
                    for (String content : result.split("','")) {
                        if (content.startsWith("http")) {
                            logger.info("正在登录，请稍后");
                            pturl = content;
                        }
                    }
                    break;
                }else if(result.contains("已失效")){
                    break;
                }
                logger.info("check login result:"+result);
            }catch (Exception e){
                logger.error("login fail ! {}"+e.getMessage());
                e.printStackTrace();
                break;
            }

        }
        return success;
    }

    public static int hash3(String qrsig) {
        int e=0;
        for (int i = 0,n=qrsig.length(); n > i; ++i) {
            e += (e <<5) + qrsig.charAt(i);
        }
        return 2147483647 & e;
    }

    private static String hash() {
        return hash(uin, "");
    }

    //hash加密方法
    private static String hash(long x, String K) {
        int[] N = new int[4];
        for (int T = 0; T < K.length(); T++) {
            N[T % 4] ^= K.charAt(T);
        }
        String[] U = {"EC", "OK"};
        long[] V = new long[4];
        V[0] = x >> 24 & 255 ^ U[0].charAt(0);
        V[1] = x >> 16 & 255 ^ U[0].charAt(1);
        V[2] = x >> 8 & 255 ^ U[1].charAt(0);
        V[3] = x & 255 ^ U[1].charAt(1);

        long[] U1 = new long[8];

        for (int T = 0; T < 8; T++) {
            U1[T] = T % 2 == 0 ? N[T >> 1] : V[T >> 1];
        }

        String[] N1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String V1 = "";
        for (long aU1 : U1) {
            V1 += N1[(int) ((aU1 >> 4) & 15)];
            V1 += N1[(int) (aU1 & 15)];
        }
        return V1;
    }
}

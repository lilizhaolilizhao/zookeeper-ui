package com.javazyw.zk.controller;

import com.javazyw.zk.util.ResourcesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description:
 * @date 2015年9月21日 下午1:10:47
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public ModelAndView home(String msg) throws Exception {
        if (msg != null) {
            msg = URLDecoder.decode(msg, "utf8");
        }
        return new ModelAndView("login", "msg", msg);
    }

    @RequestMapping(value = "toLogin")
    public ModelAndView toLogin(String msg) throws Exception {
        if (msg != null) {
            msg = URLDecoder.decode(msg, "utf8");
        }
        return new ModelAndView("login", "msg", msg);
    }

    @RequestMapping(value = "login")
    public ModelAndView login(String username, String password, HttpSession session) {
        String msg = "";

        username = "admin";
        password = "admin";
        if (username != null && password != null) {
            String auth = username + ":" + password;
            if (!auth.equals(ResourcesUtils.bundle.getString("auth"))) {
                msg = "用户名或密码错误！";
            } else {
                //登录
                session.setAttribute(ResourcesUtils.SESSION_KEY, username);
                session.setAttribute("zookeeperUrl", ResourcesUtils.bundle.getString("connectString").split(":")[0]);
                return new ModelAndView(new RedirectView("welcome"));
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("msg", URLEncoder.encode(msg, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ModelAndView(new RedirectView("toLogin"), map);
    }

    @RequestMapping(value = "welcome")
    public ModelAndView welcome(HttpSession session) {

        return new ModelAndView("main");
    }

    @RequestMapping(value = "logout")
    public ModelAndView logout(HttpSession session) {

        return new ModelAndView(new RedirectView("toLogin"));
    }

}

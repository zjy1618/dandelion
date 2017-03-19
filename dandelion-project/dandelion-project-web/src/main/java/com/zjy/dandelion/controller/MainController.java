package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.Member;
import com.zjy.dandelion.service.core.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Value("#{settings['dev.mode']}")
    protected boolean dev;

    @Value("#{settings['session.user.name']}")
    protected String sessionUser;

    @Resource
    private MemberService memberService;

    /**
     * web页面访问根路径
     *
     * @param session session
     * @param model   model
     * @param request request
     * @return login页面
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(HttpSession session, Model model, HttpServletRequest request) {
        if (dev) {
            Member member = memberService.login(session, model, "dandelion", "123456");
            session.setAttribute(sessionUser, member);
            return "main";
        }
        Object member = session.getAttribute(sessionUser);
        if (member != null) {
            return "main";
        }
        return "login";
    }

    /**
     * 请求登录页面
     *
     * @return 登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登录
     *
     * @param session  session
     * @param username 用户名
     * @param password 密码
     * @return 主页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, Model model, String username, String password) {
        Member member = memberService.login(session, model, username, password);
        if (member == null) {
            return "login";
        }
        session.setAttribute(sessionUser, member);
        return "main";
    }

    /**
     * 退出
     *
     * @param session session
     * @return 登录页面
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    /**
     * 请求主页
     *
     * @return 主页
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpSession session) {
        Object member = session.getAttribute(sessionUser);
        if (member == null) {
            return "login";
        }
        return "main";
    }

}

package com.zjy.dandelion.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSessionInterceptor implements HandlerInterceptor {

    protected final Logger logger = Logger
            .getLogger(UserSessionInterceptor.class);

    @Value("#{settings['session.user.name']}")
    protected String sessionUser;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();

        boolean flag = false;
        Object user = request.getSession().getAttribute(sessionUser);
        if (user != null) {
            uri = uri.substring(uri.indexOf(ctx));
        } else {
            uri = uri.substring(uri.indexOf(ctx) + ctx.length());
        }

        if (user == null && !("/".equals(uri) || "/login".equals(uri))) {
            response.sendRedirect("/");
        } else {
            flag = true;
        }

        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}

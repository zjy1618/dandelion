package com.zjy.dandelion.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;


/**
 * Created by chars on 16/2/29 19:12
 * Copyright © mizhuanglicai
 */
public class CurtainHandlerExceptionResolver implements HandlerExceptionResolver {

    protected static final Logger logger = LoggerFactory.getLogger(CurtainHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("url:" + request.getRequestURI() + "发生错误[" + ex.getMessage() + "]");
        logger.error(ex.getMessage(), ex);
        if (trimToEmpty(request.getHeader("accept")).indexOf("application/json") > -1) {

            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }

        }
        return new ModelAndView("404", new HashMap<>());
    }
}

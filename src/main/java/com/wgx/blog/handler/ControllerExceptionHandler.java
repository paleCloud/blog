package com.wgx.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Pale language
 * @Description: 自定义异常处理器
 * @Date:Create: 2020/5/13
 * @since: jdk1.8
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 统一拦截和处理异常并记录日志  最后返回到错误页面
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)      //标注这个方法可以作为异常处理
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {} , Exception : {}" ,request.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("URL",request.getRequestURL());
        mv.addObject("error",e);
        mv.setViewName("error/error");//设置返回的页面
        return mv;
    }
}

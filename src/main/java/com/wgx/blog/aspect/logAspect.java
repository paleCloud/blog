package com.wgx.blog.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: Pale language
 * @Description:  日志类---切面
 * @Date:Create: 2020/5/13
 * @since: jdk1.8
 */

@Aspect
@Component
public class logAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* *.*.*.controller.*.*(..))")
    public void log(){}


    @Before("log()")
    public void doBefore(){
        logger.info("---------doBefore-----------");
    }

    /**
     * 在方法执行之前获取到url ip 访问那个方法 请求得参数
     */
    @After("log()")
    public void doAfter(JoinPoint joinpoint){
        //获取url
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取访问方法和请求的参数
        String classMethod = joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName();
        Object[] args = joinpoint.getArgs();
        requestLog requestLog = new requestLog(url, ip, classMethod, args);
        logger.info("Request : {}",requestLog);
    }

    @AfterReturning(returning = "result",pointcut = "log()")//参数是要返回得参数
    public void doAfterRuturn(Object result){
//        logger.info("result : {}",result);
    }


    private class requestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public requestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }




        @Override
        public String toString() {
            return "requestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

package com.bjpowernode.commons;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.domain.Base;
import com.bjpowernode.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class DataScopeAspect {

    //value属性指定切入点表达式
    @Pointcut(value = "@annotation(com.bjpowernode.commons.DataScope)")
    private void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        DataScope dataScope = methodSignature.getMethod().getAnnotation(DataScope.class);

        String tableAlias = dataScope.tableAlias();
        String tableField = dataScope.tableField();

        String token = request.getHeader(Constants.TOKEN_NAME);

        List<String> userRoleList = JWTUtils.parseJWTByUserRole(token);
        Integer userId = JWTUtils.parseJWTByUserId(token);

        if (!userRoleList.contains("admin")) {
            Object params = joinPoint.getArgs()[0];
            if (Objects.nonNull(params) && params instanceof Base) {
                Base base = (Base) params;
                base.setFilterSQL(" AND " + tableAlias + "." + tableField + " = " + userId);
            }
        }
        System.out.println("调用目标方法前................" + new Date());
        //执行被拦截的方法
        Object result= joinPoint.proceed();
        System.out.println("调用目标方法后................" + new Date() + ", result = " + result);
        return result;
    }
}

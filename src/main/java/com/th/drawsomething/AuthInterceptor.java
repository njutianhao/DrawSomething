package com.th.drawsomething;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object id = request.getSession().getAttribute("userId");
        if(id == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}

package com.hoseok.firstboard.web.interceptor;

import com.hoseok.firstboard.domain.member.Member;
import com.hoseok.firstboard.web.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("interceptor preHandle()");
        HttpSession session = request.getSession(false); //세션을 먼저 가저오고

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) { //세션이 널인지 검사 후 세션에 로그인멤버가 있는지 검사
            log.info("미인증 사용자!!");
            response.sendRedirect("/member/login?redirectURL=" + request.getRequestURI());
            return false; // 아닐 경우 로그 남기고 리다이렉트 남겨주고 false
        }

        log.info("인가된 사용자");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

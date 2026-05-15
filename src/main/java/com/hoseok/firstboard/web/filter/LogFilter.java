package com.hoseok.firstboard.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override // 필터 초기화, 서블릿 컨테이너 생성시 호출
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("logFilter init()");
    }

    @Override // 요청 시마다 호출, 로직 수행
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; // 그냥 ServletRequest이라 캐스팅 해야 함
        String requestURI = httpRequest.getRequestURI(); //로그에 남길 요청 URI 받기
        UUID uuid = UUID.randomUUID(); // 요청 구분을 위한 UUID 생성

        try { // doFilter는 필터가 여러개라면 콜백함수 처럼 재귀호출 개념임 + request 및 response는 참조값을 넘겨주는 거라서 같이 변해서 옴
            log.info("REQUEST [{}][{}]", uuid, requestURI); //처음 들어오는 시기 -> 요청
            chain.doFilter(request, response); // 다음 필터(or컨트롤러) 실행
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI); // 마지막 종료 직전 로그
        }
    }

    @Override // 필터 종료시 호출
    public void destroy() {
        log.info("logFilter destroy()");
    }
}

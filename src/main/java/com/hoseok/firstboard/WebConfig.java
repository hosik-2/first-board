package com.hoseok.firstboard;

import com.hoseok.firstboard.web.filter.LogFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //설정파일 (@Component 포함)
public class WebConfig {

    @Bean // 빈 등록 필수!
    // 클래스 레벨에 컴포넌트가 붙으면 클래스 정보를... 스프링이 알게끔 컨테이너에 들어가 근데 안에 있는 메서드들은 실행이 안돼 필요할 때만
    // 컨테이너에 있는 메서드를 실행하지 컨트롤러처럼 근데 우리가 만든 설정파일중 필터는 실제 설정정보가 담긴 FilterRegistration? 객체가 필요해
    // 그래서 @Bean 해서 스프링이 실행되자마자 만들 수 있도록 빈을 등록하고 안에 있는 로직을 수행 후 정보가 담긴 FilterRegistration 객체를 반환해서
    // 그걸 스프링한테 줘 그게 아니라 클래스 단에만 컴포넌트를 달면 저 메서드는 실행을 안할테니까
    // 라고 물어봤는데 맞다고 합니다.
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter()); //사용할 필터 등록
        filterRegistrationBean.setOrder(1); // 사용 순서 (낮을 수록 먼저 실행됨)
        filterRegistrationBean.addUrlPatterns("/*"); // 사용할 URL 패턴 등록 *은 모두임

        return filterRegistrationBean;
    }
}

package com.hoseok.firstboard.web.member;

import com.hoseok.firstboard.domain.member.Member;
import com.hoseok.firstboard.domain.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) { //get 요청에도 빈 객체 전달을 위한 모델 선언 및 빈 객체 전달 잊지 말기.
        log.info("loginForm()");
        model.addAttribute("loginForm", new MemberLoginForm());
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") MemberLoginForm form, BindingResult bindingResult,
                        HttpSession session) {

        Optional<Member> optionalMember = memberService.login(form.getLoginId(), form.getPassword());

        if (bindingResult.hasErrors()) { // 검증 오류부터 잡고 그 다음에 글로벌 오류로 가는 것이 일반적인 순서 (무게차이)
            log.info("validation errors = {}", bindingResult);
            return "member/loginForm";
        }

        if (optionalMember.isEmpty()) {
            bindingResult.reject("loginError", "아이디와 비밀번호가 맞지 않습니다.");
            log.info("loginError = {}", bindingResult);
            // return "redirect:/login"; <- BindingResult랑 리다이렉트는 같이 쓸 수 없음: 브라우저가 새로운 요청 보낼 시
            // 바인딩 리절트 내용이 공중분해 된다.
            return "member/loginForm"; //아이디/비번 불일치 시 입력 데이터는 유지하면서 사용자에게 경고를 띄우기 위해 BindingResult에 글로벌 에러를 담고 포워딩함.
        }

        Member member = optionalMember.get(); // null이 아닌게 확인 됐기 때문에 get()을 써도 무방함.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout()");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // null 체크 잊지 않기 (NPE)
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        log.info("registerForm()");
        model.addAttribute("registerForm", new MemberSaveForm());
        return "member/registerForm";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerForm") MemberSaveForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) { // 검증 오류 추출
            log.info("validation error={}", bindingResult);
            return "member/registerForm";
        }

//        Member member = new Member();
//        member.setName(form.getName());
//        member.setLoginId(form.getLoginId());
//        member.setPassword(form.getPassword());
//        member.setEmail(form.getEmail());
//        member.setPhoneNumber(form.getPhoneNumber()); DTO -> Member 수동 맵핑
        Member member = form.toEntity(); // DTO 객체 안 메서드 활용

        try {
            memberService.register(member); // 예외를 발생하는 로직 실행
        } catch (IllegalStateException e) {
            bindingResult.reject("duplicate", e.getMessage()); // bindingResult에 에러 주입
            log.info("duplicate ID={}", bindingResult.getGlobalError());
            return "member/registerForm";
        }

        return "redirect:/";
    }

    @GetMapping("/500-error")
    public String error500() {
        throw new RuntimeException("500Error");
    }
}

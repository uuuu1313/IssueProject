package com.issuemarket.controllers.members;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MyPageForm;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberUpdateService;
import com.issuemarket.validators.member.JoinValidator;
import com.issuemarket.validators.member.UpdateValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberRepository repository;
    private final MemberUtil memberUtil;
    private final MemberUpdateService updateService;
    private final UpdateValidator updateValidator;

    @GetMapping("/info/{userNo}")
    public String mypage(@PathVariable Long userNo, Model model) {
        commonProcess(model, "회원정보 수정");

        if (memberUtil.isLogin()) {
            Long no = memberUtil.getMember().getUserNo();
            if (!userNo.equals(no)) {
                String script = String.format("Swal.fire('본인 계정만 접근 가능합니다.', '', 'error').then(function(){location.href='/';})");

                model.addAttribute("script", script);

                return "commons/sweet_script";
            }
        }

        try {
            MemberInfo memberInfo = memberUtil.getMember();

            MemberJoin memberJoin = new ModelMapper().map(memberInfo, MemberJoin.class);

            model.addAttribute("memberJoin", memberJoin);

            return "member/mypage/info";
        } catch (Exception e) {

            return "redirect:/member/login";
        }
    }

    @PostMapping("/info/{userNo}")
    public String mypagePs(@PathVariable Long userNo, @ModelAttribute MemberJoin memberJoin, Errors errors, Model model) {

        updateValidator.validate(memberJoin, errors);

        if (errors.hasErrors()) {
            return "member/mypage/info";
        }

        updateService.myPageUpdate(userNo, memberJoin);

        String script = String.format("Swal.fire('수정 완료!', '재 로그인시 적용됩니다 :D', 'success').then(function() {history.back();})");

        model.addAttribute("script", script);

        return "commons/sweet_script";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}

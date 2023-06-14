package com.issuemarket.controllers.members;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MyPageForm;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberUpdateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberRepository repository;
    private final MemberUtil memberUtil;
    private final MemberUpdateService updateService;

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

            MyPageForm myPageForm = new ModelMapper().map(memberInfo, MyPageForm.class);

            model.addAttribute("myPageForm", myPageForm);

            return "/member/mypage";
        } catch (Exception e) {

            return "redirect:/member/login";
        }
    }

    @PostMapping("/info/{userNo}")
    public String mypagePs(@PathVariable Long userNo, @ModelAttribute MyPageForm myPageForm, Model model) {




        return "redirect:/member/mypage";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}

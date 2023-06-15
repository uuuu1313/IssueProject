package com.issuemarket.controllers.members;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.BoardSearch;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MyPageForm;
import com.issuemarket.entities.Board;
import com.issuemarket.entities.Member;
import com.issuemarket.entities.Post;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberDeleteService;
import com.issuemarket.service.admin.member.MemberUpdateService;
import com.issuemarket.service.front.member.MemberPostListService;
import com.issuemarket.service.front.member.MemberPwCheckService;
import com.issuemarket.validators.member.JoinValidator;
import com.issuemarket.validators.member.UpdateValidator;
import groovy.lang.Script;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberUtil memberUtil;
    private final MemberUpdateService updateService;
    private final UpdateValidator updateValidator;
    private final MemberPwCheckService checkService;
    private final MemberDeleteService deleteService;
    private final MemberPostListService postListService;

    @GetMapping("/pwconfirm")
    public String pwconfirm(@ModelAttribute MemberInfo memberInfo, Model model) {
        commonProcess(model, "비밀번호 확인");

        return "member/mypage/pwconfirm";
    }

    @PostMapping("/pwconfirm")
    public String pwconfirmPs(MemberInfo memberInfo, Model model) {
        String userPw = memberInfo.getUserPw();
        Long userNo = memberUtil.getMember().getUserNo();

        try {
            checkService.check(userPw);
            return "redirect:/member/mypage/info/" + userNo;
        } catch (Exception e) {
            String script = String.format("Swal.fire('%s', '', 'error').then(function(){history.back();})", e.getMessage());
            model.addAttribute("script", script);
            return "commons/sweet_script";
        }
    }

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

    @GetMapping("/delete/{userNo}")
    public String delete(@PathVariable Long userNo) {
        deleteService.delete(userNo);

        return "redirect:/member/logout";
    }

    @GetMapping("/deletepw/{userNo}")
    public String deleteconfirm(@PathVariable Long userNo, @ModelAttribute MemberInfo memberInfo, Model model) {
        commonProcess(model, "비밀번호 확인");

        return "member/mypage/pwconfirm";
    }

    @PostMapping("/deletepw/{userNo}")
    public String deleteconfirmPs(@PathVariable Long userNo, MemberInfo memberInfo, Model model) {
        String userPw = memberInfo.getUserPw();
        String url = "/member/mypage/delete/" + userNo;
        try {
            if (checkService.check(userPw)) {
                model.addAttribute("confirmUrl", url);
                return "commons/sweet_script";
            }
        } catch (Exception e) {
            String script = String.format("Swal.fire('%s', '', 'error').then(function(){history.back();})", e.getMessage());
            model.addAttribute("script", script);
            return "commons/sweet_script";
        }
        return "redirect:/member/mypage/info/"+userNo;
    }

    @GetMapping("/mypost/{userNo}")
    public String mypost(@PathVariable Long userNo, @ModelAttribute BoardSearch boardSearch, Model model) {
        commonProcess(model, "내가 쓴 게시글");

        Page<Post> posts = postListService.gets(boardSearch, userNo);
        List<Post> postList = posts.getContent();

        int nowPage = posts.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, posts.getTotalPages());

        model.addAttribute("postList", postList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/mypage/mypost";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);

        List<String> addCss = new ArrayList<>();
        addCss.add("board/style");
        model.addAttribute("addCss", addCss);
    }
}
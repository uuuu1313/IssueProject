package com.issuemarket.controllers.admins;

import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MemberListForm;
import com.issuemarket.dto.MemberSearch;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.CommonException;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.service.admin.member.MemberDeleteService;
import com.issuemarket.service.admin.member.MemberListService;
import com.issuemarket.service.admin.member.MemberUpdateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberListService listService;
    private final MemberRepository memberRepository;
    private final MemberDeleteService deleteService;
    private final MemberUpdateService updateService;

    @GetMapping
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess(model, "회원관리");

        Page<Member> data = listService.gets(search);
        List<Member> items =data.getContent();
        model.addAttribute("items", items);
        items.stream().forEach(System.out::println);

        return "admin/member/index";
    }

    @PostMapping
    public String listPs(@ModelAttribute MemberListForm listForm, Model model) {
        commonProcess(model, "회원 관리");

        String script = null;
        try {
            updateService.listUpdate(listForm);
            script = String.format("parent.Swal.fire('수정 완료!', '', 'success').then(function() { parent.location.reload(); })");
        } catch (Exception e) {
            script = String.format("parent.Swal.fire('%s', '', 'error').then(function() {})", e.getMessage());
        }

        model.addAttribute("script", script);
        return "commons/sweet_script";
    }

    @GetMapping("/view/{userNo}")
    public String view(@PathVariable Long userNo, Model model) {
        commonProcess(model, "회원 상세 조회");

        Member member = memberRepository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        model.addAttribute("memberJoin", member);

        return "admin/member/view";
    }

    @PostMapping("/view/{userNo}")
    public String viewPs(@PathVariable Long userNo, @ModelAttribute MemberJoin memberJoin, Model model) {
        commonProcess(model, "회원 상세 조회");

        model.addAttribute("mypageuno",userNo);

        updateService.update(userNo, memberJoin);

        return "redirect:/admin/member";
    }

    @GetMapping("/delete/{userNo}")
    public String delete(@PathVariable Long userNo) {
        Member member = listService.get(userNo);

        deleteService.delete(member.getUserNo());

        return "redirect:/admin/member";
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", "member");
    }
}
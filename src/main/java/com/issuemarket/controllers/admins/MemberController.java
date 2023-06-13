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

//        String[] roles = Arrays.stream(Role.values()).map(r->r.toString()).toArray(String[]::new);
//        model.addAttribute("roles", roles);

        return "admin/member/index";
    }

    @PostMapping
    public String listPs(@ModelAttribute MemberListForm listForm, Model model) {
        commonProcess(model, "회원 관리");



        try {
            updateService.listUpdate(listForm);
            String script = String.format("Swal.fire('수정 완료!', '', 'success').then(function() {location.reload();})");
            model.addAttribute("script", script);
        } catch (Exception e) {
            e.printStackTrace();

            String script = String.format("Swal.fire('수정할 회원을 선택해주세요!', '', 'error').then(function() {history.go(-1);})");
            model.addAttribute("script", script);

            return "commons/sweet_script";
        }
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
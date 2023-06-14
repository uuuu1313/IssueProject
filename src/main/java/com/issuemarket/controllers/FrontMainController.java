package com.issuemarket.controllers;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.dto.MemberLogin;
import com.issuemarket.entities.Board;
import com.issuemarket.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class FrontMainController {


    private final MemberUtil memberUtil;

    @GetMapping
    public String main(@AuthenticationPrincipal MemberInfo memberInfo, Model model) {

        if (memberUtil.isLogin()) {
            Long userNo = memberInfo.getUserNo();
            model.addAttribute("mypageuno", userNo);
        }

        return "front/index";
    }

}

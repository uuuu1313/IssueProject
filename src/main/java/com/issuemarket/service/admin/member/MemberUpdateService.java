package com.issuemarket.service.admin.member;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MemberListForm;
import com.issuemarket.dto.MyPageForm;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.CommonException;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public void listUpdate(MemberListForm memberListForm) {

        List<Long> userNos = memberListForm.getUserNo();
        if (userNos == null || userNos.isEmpty()) {
            throw new CommonException("사용자를 선택하세요.");
        }
        for (int i = 0; i < userNos.size(); i++) {
            Long userNo = userNos.get(i);
            String roleStr = request.getParameter("roles_" + userNo);
            if (roleStr == null) continue;

            Role role = Role.valueOf(roleStr);
            Member member = repository.findById(userNo).orElse(null);
            member.setRoles(role);

            repository.saveAndFlush(member);
        }
    }

    public void update(Long userNo, MemberJoin memberJoin) {

        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);
        String nick = memberJoin.getUserNick();
        String role = memberJoin.getRoles();

        if (nick == null || nick.isBlank()) {
            member.setUserNick(member.getUserNick());
        } else {
            member.setUserNick(nick);
        }

        member.setRoles(Role.valueOf(role));

        repository.saveAndFlush(member);
    }

    public void pwUpdate(Long userNo, String pw) {
        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        member.setUserPw(passwordEncoder.encode(pw));

        repository.saveAndFlush(member);
    }

    public void myPageUpdate(Long userNo, MemberJoin memberJoin) {

        Member member = repository.findById(userNo).orElseThrow(MemberNotFoundException::new);

        String updatePw = memberJoin.getUserPw();
        String updatePwRe = memberJoin.getUserPwRe();
        String updateUserNm = memberJoin.getUserNm();
        String updateNick = memberJoin.getUserNick();
        String updateMobile = memberJoin.getMobile();

        if (updatePw != null && !updatePw.isBlank() && updatePw.equals(updatePwRe)) {
            member.setUserPw(passwordEncoder.encode(updatePw));
        }

        member.setUserNm(updateUserNm);
        member.setUserNick(updateNick);
        member.setMobile(updateMobile);

        repository.saveAndFlush(member);
    }
}
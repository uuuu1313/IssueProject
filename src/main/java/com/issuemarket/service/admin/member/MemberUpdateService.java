package com.issuemarket.service.admin.member;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.commons.constants.Role;
import com.issuemarket.dto.MemberJoin;
import com.issuemarket.dto.MemberListForm;
import com.issuemarket.entities.Member;
import com.issuemarket.exception.MemberNotFoundException;
import com.issuemarket.repositories.MemberRepository;
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


    public void listUpdate(MemberListForm memberListForm) {

        List<Long> userNos = memberListForm.getUserNo();
        List<String> roles = memberListForm.getRoles();

        for (int i = 0; i < userNos.size(); i++){
            Long userNo = userNos.get(i);
            Role role = Role.valueOf(roles.get(i));
            Member member = repository.findById(userNo).orElse(null);
            member.setRoles(role);

            repository.saveAndFlush(member);
        }

//        List<Member> items = new ArrayList<>();
//        for (int num : nums) {
//            Long userNo = userNos.get(num);
//            Role role = Role.valueOf(roles.get(num));
//            Member item = repository.findById(userNo).orElse(null);
//            if(item == null) {
//                continue;
//            }
//            item.setRoles(role);
//            items.add(item);
//        }

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

    public void pwUpdate(Long no, String pw) {
        Member member = repository.findById(no).orElseThrow(MemberNotFoundException::new);

        member.setUserPw(passwordEncoder.encode(pw));

        repository.saveAndFlush(member);
    }
}

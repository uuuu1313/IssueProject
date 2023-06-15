package com.issuemarket.service.front.member;

import com.issuemarket.commons.MemberUtil;
import com.issuemarket.dto.MemberInfo;
import com.issuemarket.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPwCheckService {

    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    public void check(String password) {
        String getPw = memberUtil.getMember().getUserPw();

        boolean matched = passwordEncoder.matches(password, getPw);

        if (!matched) {
            throw new CommonException("비밀번호가 일치하지 않습니다.");
        }
    }
}

package com.issuemarket.validators.member;

import com.issuemarket.dto.MemberJoin;
import com.issuemarket.validators.MobileValidator;
import com.issuemarket.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UpdateValidator implements Validator, MobileValidator, PasswordValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberJoin memberJoin = (MemberJoin) target;

        String updatePw = memberJoin.getUserPw();
        String updatePwRe = memberJoin.getUserPwRe();
        String mobile = memberJoin.getMobile();

        // 2. 비밀번호 복잡성 체크 : 알파벳(대문자, 소문자 포함), 숫자, 특수문자 포함 ( PasswordValidator 사용 )
        if (updatePw != null && !updatePw.isBlank()
                && (!alphaCheck(updatePw,false)
                || !numberCheck(updatePw)
                || !specialCharsCheck(updatePw))) {
            errors.rejectValue("userPw", "Validation.complexity.password");
        }

        // 3. 비밀번호와 비밀번호 확인 일치
        if (updatePw != null && !updatePw.isBlank() && updatePwRe != null && !updatePwRe.isBlank() && !updatePw.equals(updatePwRe)) {
            errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
        }

        // 4. 휴대전화번호 ( 선택 ) : 입력된 경우 형식 체크
        // 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
        if (mobile != null && !mobile.isBlank()) {
            if (!mobileNumCheck(mobile)) {
                errors.rejectValue("mobile", "Validation.mobile");
            }
            mobile = mobile.replaceAll("\\D", "");
            memberJoin.setMobile(mobile);
        }
    }
}

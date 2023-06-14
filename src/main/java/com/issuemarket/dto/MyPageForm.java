package com.issuemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MyPageForm {

    private Long userNo;

    private String userId;

    private String userPw;

    private String updatePw;

    private String updatePwRe;

    private String userNm;

    private String userNick;

    private String mobile;
}

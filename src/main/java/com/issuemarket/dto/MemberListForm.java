package com.issuemarket.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberListForm {
    private List<Long> userNo;
    private List<String> roles;
}

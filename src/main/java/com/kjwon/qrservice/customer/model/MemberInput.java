package com.kjwon.qrservice.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberInput {
    private String userId;
    private String userName;
    private String phone;
    private String password;
}

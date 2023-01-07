package com.website.stw.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
    @Size(min=3, max=20)
    @NotEmpty(message = "ID를 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String password2;

    @NotEmpty(message = "E-mail을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    private String phone;
}

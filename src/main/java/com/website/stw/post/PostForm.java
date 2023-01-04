package com.website.stw.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class PostForm {
    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(max=200)
    private String subject;

    //String -> NotEmpty로 체크
    //Integer -> NotNull로 체크
    @NotNull(message = "정원을 입력해주세요.")
    @Min(1)
    @Max(999)
    private Integer maxNum;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

}

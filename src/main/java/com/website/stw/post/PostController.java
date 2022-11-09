package com.website.stw.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor // final 붙은 속성을 포함한 생성자를 자동 생성해줌
@Controller
public class PostController {

    private final PostRepository postRepository;

    @RequestMapping("/post/list")
    public String list(Model model){
        List<Post> postList = this.postRepository.findAll();
        model.addAttribute("postList", postList);
        return "post_list";
    }
}

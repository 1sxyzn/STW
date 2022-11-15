package com.website.stw.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/post")
@RequiredArgsConstructor // final 붙은 속성을 포함한 생성자를 자동 생성해줌
@Controller
public class PostController {

    private final PostService postService;

    @RequestMapping("/list")
    public String list(Model model){
        List<Post> postList = this.postService.getList();
        model.addAttribute("postList", postList);
        return "post_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        model.addAttribute("post",post);
        return "post_detail";
    }

    @RequestMapping(value = "/create")
    public String postCreate(){
        return "post_form";
    }

    @PostMapping("/create")
    // 메소드 오버로딩
    public String postCreate(@RequestParam String subject, @RequestParam Integer max_num, @RequestParam String content){
        this.postService.create(subject, max_num, content);
        return "redirect:/post/list";
    }
}

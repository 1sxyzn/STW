package com.website.stw.post;

import com.website.stw.user.SiteUser;
import com.website.stw.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/post")
@RequiredArgsConstructor // final 붙은 속성을 포함한 생성자를 자동 생성해줌
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page){
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging", paging);
        return "post_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        model.addAttribute("post",post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/create")
    public String postCreate(PostForm postForm){
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    // 메소드 오버로딩
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) { //오류 있는 경우, 폼 다시 작성
            return "post_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.postService.create(postForm.getSubject(), postForm.getMaxNum(), postForm.getContent(), siteUser);
        return "redirect:/post/list";
    }
}

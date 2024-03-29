package com.website.stw.post;

import com.website.stw.user.SiteUser;
import com.website.stw.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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
        Set<String> masking = new HashSet<>();
        for(SiteUser s : post.participant){
            String mask = s.getUsername().substring(0,2);
            for(int ast=2; ast<s.getUsername().length(); ast++){
                mask += "*";
            }
            masking.add(mask);
        }
        model.addAttribute("post",post);
        model.addAttribute("masking",masking);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(PostForm postForm, @PathVariable("id") Integer id, Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        postForm.setMaxNum(post.getMaxNum());
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getSubject(), postForm.getContent(), String.valueOf(postForm.getMaxNum()));
        return String.format("redirect:/post/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/join/{id}")
    public String postJoin(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.postService.join(post, siteUser);
        return String.format("redirect:/post/detail/%s", id);
    }
}

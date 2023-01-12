// Controller -> Service -> Repository 구조

package com.website.stw.post;

import com.website.stw.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.website.stw.DataNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getList(){
        return this.postRepository.findAll();
    }

    public Post getPost(Integer id){
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        }
        else{
            throw new DataNotFoundException("post not found");
        }
    }

    public void create(String subject, Integer maxNum, String content, SiteUser user){
        Post p = new Post();
        p.setSubject(subject);
        p.setMaxNum(maxNum);
        p.setCurNum(1);
        p.setContent(content);
        p.setCreatedDate(LocalDateTime.now());
        p.setAuthor(user);
        this.postRepository.save(p);
    }

    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    public void modify(Post post, String subject, String content, String maxNum) {
        post.setSubject(subject);
        post.setContent(content);
        post.setMaxNum(Integer.valueOf(maxNum));
        post.setModifiedDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    public void join(Post post, SiteUser siteUser){
        // 정원 초과가 아니거나, 본인이 올린 글이 아니거나, 이미 참여하지 않은 경우
        if(post.getMaxNum()>post.getCurNum() && !post.getAuthor().getUsername().equals(siteUser.getUsername())){
            post.getParticipant().add(siteUser);
            post.setCurNum(post.getCurNum()+1);
            this.postRepository.save(post);
        }
    }
}

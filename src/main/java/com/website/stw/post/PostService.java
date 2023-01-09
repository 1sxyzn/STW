// Controller -> Service -> Repository 구조

package com.website.stw.post;

import com.website.stw.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.website.stw.DataNotFoundException;

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
        p.setCurNum(0);
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
}

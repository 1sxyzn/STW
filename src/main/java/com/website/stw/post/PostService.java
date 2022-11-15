// Controller -> Service -> Repository 구조

package com.website.stw.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void create(String subject, Integer max_num, String content){
        Post p = new Post();
        p.setSubject(subject);
        p.setMax_num(max_num);
        p.setCur_num(0);
        p.setContent(content);
        p.setCreated_date(LocalDateTime.now());
        this.postRepository.save(p);
    }
}

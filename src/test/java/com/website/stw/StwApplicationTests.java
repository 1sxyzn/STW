package com.website.stw;

import com.website.stw.post.Post;
import com.website.stw.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StwApplicationTests {

    @Autowired // DI, 객체 자동 생성 및 주입 (테스트 코드에서만 사용할 예정)
    private PostRepository postRepository;

    @Test
    void testCreatePost(){
        Post p1 = new Post();
        p1.setSubject("2023 winter internship");
        p1.setContent("Back-End intern");
        p1.setMax_num(30);
        p1.setCur_num(0);
        p1.setCreated_date(LocalDateTime.now());
        this.postRepository.save(p1);
    }

    @Test
    void testFindAllPost(){
        List<Post> all = this.postRepository.findAll();
        assertEquals(2,all.size()); //2개 데이터 있는지

        Post p = all.get(0);
        assertEquals("2023 winter internship", p.getSubject());
    }

    @Test
    void testFindByIdPost(){
        Optional<Post>op = this.postRepository.findById(1); // findbyid 리턴값이 optional임
        if(op.isPresent()){
            Post p = op.get();
            assertEquals("2023 winter internship", p.getSubject());
        }
    }
}

package com.website.stw;

import com.website.stw.post.Post;
import com.website.stw.post.PostRepository;
import com.website.stw.post.PostService;
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
    @Autowired
    private PostService postService;

    //테스트 데이터 300개 생성
    @Test
    void testCreateData() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("Search The Web : Data [%03d]", i);
            Integer maxNum = 80;
            String content = "Test Data";
            this.postService.create(subject, maxNum, content, null);
        }
    }

    @Test
    void testCreatePost(){
        Post p1 = new Post();
        p1.setSubject("2023 winter internship");
        p1.setContent("Back-End intern");
        p1.setMaxNum(30);
        p1.setCurNum(0);
        p1.setCreatedDate(LocalDateTime.now());
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

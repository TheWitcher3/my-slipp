package net.slipp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoad() {
        User user = new User();
        user.setName("이현하");
        user.setEmail("lee.hh@kt.com");
        user.setPassword("1111");
        user.setUserId("lee.hh");

        userRepository.save(user);

        System.out.println("초기 데이터 로딩");
    }
}
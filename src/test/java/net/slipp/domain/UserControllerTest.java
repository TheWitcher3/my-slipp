package net.slipp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepository userRepository;


    @Test
    void init() {
        System.out.println("게임을 시작하지");
    }

//    @Test
//    void insertTest() {
//        User user = new User("lee.hh", "이현하", "1234", "lee.hh@kt.com");
//        System.out.println(user);
//        userRepository.save(user);
//    }

}
package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = new User();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUserId(String.format("U%s", uuid));
        user.setUsername("cjf001");
        user.setNickname("杰飞");
        user.setLoginPassword("123456");
        User save = userRepository.save(user);
    }

}
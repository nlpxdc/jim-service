package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.Uxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UxyRepositoryTest {

    @Autowired
    private UxyRepository uxyRepository;

    @Test
    void save() {
        Uxy uxy = new Uxy();
        uxy.setUxyId("123v456");
        uxy.setUxId("123");
        uxy.setUyId("456");
        uxy.setBeFriend(true);
        uxy.setBeFriendTime(1601234567890L);
        Uxy save = uxyRepository.save(uxy);
    }

    @Test
    void load() {
        Optional<Uxy> optional = uxyRepository.findById("123v456");
        Uxy uxy = optional.get();
    }

}
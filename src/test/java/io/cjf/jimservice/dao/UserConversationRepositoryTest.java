package io.cjf.jimservice.dao;

import io.cjf.jimservice.po.UserConversation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserConversationRepositoryTest {

    @Autowired
    private UserConversationRepository userConversationRepository;

    @Test
    void save() {
        UserConversation userConversation = new UserConversation();
        userConversation.setUserConversationId("123V456");
        userConversation.setUserId("123");
        userConversation.setConversationId("456");
        userConversation.setJoinTime(1234L);
        userConversation.setMuteEnabled(false);
        UserConversation save = userConversationRepository.save(userConversation);
    }

}
package io.cjf.jimservice.dao;

import io.cjf.jimservice.enumeration.ConversationType;
import io.cjf.jimservice.po.Conversation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConversationRepositoryTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @Test
    void save() {
        Conversation conversation = new Conversation();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        conversation.setConversationId(uuid);
        conversation.setType(ConversationType.Single.ordinal());
        conversation.setName("asdf");
        long now = System.currentTimeMillis();
        conversation.setCreateTime(now);
        conversation.setHoldUserId("uuid");
        conversation.setLastMessageId("uuid");
        Conversation save = conversationRepository.save(conversation);
    }

    @Test
    void load() {
        Optional<Conversation> optional = conversationRepository.findById("150c2802d3a14e7cad1dcab8a2c6ca48");
        Conversation conversation = optional.get();
    }

}
package io.cjf.jimservice.service.impl;

import io.cjf.jimservice.dao.ConversationRepository;
import io.cjf.jimservice.po.Conversation;
import io.cjf.jimservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Conversation save(Conversation conversation) {
        Conversation save = conversationRepository.save(conversation);
        return save;
    }

    @Override
    public Conversation load(String conversationId) {
        Conversation conversation = null;
        Optional<Conversation> optional = conversationRepository.findById(conversationId);
        if (optional.isPresent()) {
            conversation = optional.get();
        }
        return conversation;
    }
}

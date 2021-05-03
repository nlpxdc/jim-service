package io.cjf.jimservice.service.impl;

import io.cjf.jimservice.dao.UserConversationRepository;
import io.cjf.jimservice.po.UserConversation;
import io.cjf.jimservice.service.UserConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserConversationServiceImpl implements UserConversationService {

    @Autowired
    private UserConversationRepository userConversationRepository;

    @Override
    public UserConversation load(String userConversationId) {
        UserConversation userConversation = null;
        Optional<UserConversation> optional = userConversationRepository.findById(userConversationId);
        if (optional.isPresent()) {
            userConversation = optional.get();
        }
        return userConversation;
    }

    @Override
    public UserConversation save(UserConversation userConversation) {
        UserConversation save = userConversationRepository.save(userConversation);
        return save;
    }

    @Override
    public Iterable<UserConversation> batchSave(Iterable<UserConversation> userConversations) {
        Iterable<UserConversation> saves = userConversationRepository.saveAll(userConversations);
        return saves;
    }
}

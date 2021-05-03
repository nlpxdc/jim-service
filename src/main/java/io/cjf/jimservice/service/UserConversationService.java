package io.cjf.jimservice.service;

import io.cjf.jimservice.po.UserConversation;

import java.util.List;

public interface UserConversationService {

    UserConversation load(String userConversationId);

    UserConversation save(UserConversation userConversation);

    Iterable<UserConversation> batchSave(Iterable<UserConversation> userConversations);

}

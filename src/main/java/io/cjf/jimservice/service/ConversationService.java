package io.cjf.jimservice.service;

import io.cjf.jimservice.po.Conversation;

public interface ConversationService {

    Conversation save(Conversation conversation);

    Conversation load(String conversationId);

}
